package com.lappsus.rxretrorealm.api


import com.lappsus.rxretrorealm.model.Gist
import com.lappsus.rxretrorealm.model.Subscription
import com.lappsus.rxretrorealm.model.User
import retrofit2.http.GET
import retrofit2.http.Path
import rx.Observable
import java.util.*

/**
 *
 */

interface GithubService {

    @GET("users/{username}")
    fun getUser(@Path("username") username: String): Observable<User>

    @GET("users/{username}/subscriptions")
    fun getUserSubscriptions(@Path("username") username: String): Observable<ArrayList<Subscription>>

    @GET("users/{username}/gists")
    fun getUserGists(@Path("username") username: String): Observable<ArrayList<Gist>>

    @GET("users/{username}/following")
    fun getFollowingUsers(@Path("username") username: String): Observable<ArrayList<User>>

}



