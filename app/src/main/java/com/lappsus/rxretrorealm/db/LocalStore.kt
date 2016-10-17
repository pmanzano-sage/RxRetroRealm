package com.lappsus.rxretrorealm.db

import android.util.Log
import com.lappsus.rxretrorealm.model.Gist
import com.lappsus.rxretrorealm.model.Subscription
import com.lappsus.rxretrorealm.model.User
import com.lappsus.rxretrorealm.utils.TAG
import io.realm.Realm
import io.realm.RealmQuery
import java.util.*

/**
 * @author Pablo Manzano
 * @since 11/10/16
 */

val realm = Realm.getDefaultInstance()

fun saveUser(user: User): User {
    realm.beginTransaction()
    realm.copyToRealmOrUpdate(user)
    realm.commitTransaction()
    return user
}

fun saveSubscription(subscription: Subscription): Subscription {
    Log.d(TAG, "Saving ${subscription.toStr()}")
    realm.beginTransaction()
    realm.copyToRealmOrUpdate(subscription)
    realm.commitTransaction()
    return subscription
}

fun saveGist(gist: Gist): Gist {
    Log.d(TAG, "Saving ${gist.toStr()}")
    realm.beginTransaction()
    realm.copyToRealmOrUpdate(gist)
    realm.commitTransaction()
    return gist
}

/**
 * Dependencies
 */

fun User.setSubscriptions(subs: ArrayList<Subscription>): User {
    subs.forEach { saveSubscription(it) }
    subscriptions.addAll(subs)
    return saveUser(this)
}

fun User.setGists(gists: ArrayList<Gist>): User {
    gists.forEach { saveGist(it) }
    this.gists.addAll(gists)
    return saveUser(this)
}

fun User.setFollowingUsers(users: ArrayList<User>): User {
    users.forEach { saveUser(it) }
    followingUsers.addAll(users)
    return saveUser(this)
}


/**
 * Auxiliary functions
 */

fun getCurrentUser(): User? {
    return RealmQuery.createQuery(realm, User::class.java).findFirst()
}

fun Subscription.toStr(): String {
    return "$name $description"
}

fun Gist.toStr(): String {
    return "$id $description"
}

