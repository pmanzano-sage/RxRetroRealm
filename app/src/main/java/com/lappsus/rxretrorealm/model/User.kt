package com.lappsus.rxretrorealm.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class User : RealmObject() {

    @PrimaryKey
    @SerializedName("id")
    @Expose
    open var id: Int = 0

    @SerializedName("login")
    @Expose
    open var login: String? = null

    @SerializedName("avatar_url")
    @Expose
    open var avatarUrl: String? = null

    @SerializedName("name")
    @Expose
    open var name: String? = null

    @SerializedName("public_repos")
    @Expose
    open var publicRepos: Int? = null

    @SerializedName("following")
    @Expose
    open var following: Int = 0

    // Only used from realm
    open var subscriptions: RealmList<Subscription> = RealmList()
    open var gists: RealmList<Gist> = RealmList()
    open var followingUsers: RealmList<User> = RealmList()
}

