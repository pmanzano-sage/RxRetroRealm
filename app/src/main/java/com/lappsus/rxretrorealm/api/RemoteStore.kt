package com.lappsus.rxretrorealm.api

import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import com.google.gson.GsonBuilder
import com.lappsus.rxretrorealm.db.saveUser
import com.lappsus.rxretrorealm.db.setFollowingUsers
import com.lappsus.rxretrorealm.db.setGists
import com.lappsus.rxretrorealm.db.setSubscriptions
import com.lappsus.rxretrorealm.model.Gist
import com.lappsus.rxretrorealm.model.Subscription
import com.lappsus.rxretrorealm.model.User
import io.realm.RealmObject
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.*

/**
 * @author Pablo Manzano
 * @since 11/10/16
 */
val gson = GsonBuilder().setExclusionStrategies(object : ExclusionStrategy {
    override fun shouldSkipField(f: FieldAttributes): Boolean {
        return f.declaringClass == RealmObject::class.java
    }

    override fun shouldSkipClass(clazz: Class<*>): Boolean {
        return false
    }
}).create()

val retrofit: Retrofit = Retrofit.Builder()
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .baseUrl("https://api.github.com/")
        .build()

val githubService: GithubService = retrofit.create(GithubService::class.java)


fun retrieveGithubUser(username: String): Observable<User> {
    return githubService.getUser(username)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .map(::saveUser)
}


fun retrieveGithubSubscriptions(username: String): Observable<ArrayList<Subscription>> {
    return githubService.getUserSubscriptions(username)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
}


fun retrieveGithubGists(username: String): Observable<ArrayList<Gist>> {
    return githubService.getUserGists(username)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
}

/**
 * Now we try to combine various requests into one.
 * If the requests are independent they can be synchronized with a zip operator.
 */
fun retrieveGithubGuy(username: String): Observable<User> {
    val userReq = retrieveGithubUser(username)
    val subsReq = retrieveGithubSubscriptions(username)
    val gistsReq = retrieveGithubGists(username)
    return Observable.zip(userReq, subsReq, gistsReq) { user, subscriptions, gists ->
        user.setSubscriptions(subscriptions).setGists(gists)
    }
}

/**
 * Now we implement an example where various requests are dependent from each other.
 * - First obtain the following list,
 * - then, retrieve more info about each of them
 */
fun retrieveFollowingUsers(username: String): Observable<ArrayList<User>> {
    return githubService.getFollowingUsers(username)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
}

/**
 * NOTE: The easiest and cleanest way to modify an entity in the lambdas is to take it as argument.
 */
fun retrieveGithubFollowings(theUser: User): Observable<ArrayList<User>> {
    val username = theUser.login!!
    val userReq = retrieveGithubUser(username)
    return Observable.just(userReq)
            .flatMap { retrieveFollowingUsers(username) }
            .doOnNext { users -> theUser.setFollowingUsers(users) }
}


/**
 * Look how buffer can be used to rebuild the list.
 */
fun retrieveFollowingDetails(theUser: User): Observable<List<User>> {
    val username = theUser.login!!
    val numFollowing = theUser.following
    return retrieveFollowingUsers(username)
            .doOnNext { users -> theUser.setFollowingUsers(users) }
            .flatMap { Observable.from(it) }
            .flatMap { retrieveGithubUser(it.login!!) }
            .doOnNext { saveUser(it) }
            .buffer(numFollowing)
}



