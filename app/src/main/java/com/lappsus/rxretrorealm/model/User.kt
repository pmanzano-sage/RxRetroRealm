package com.lappsus.rxretrorealm.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import java.util.*

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
    open var publicRepos: Int = 0

    @SerializedName("followers")
    @Expose
    open var followers: Int = 0

    @SerializedName("following")
    @Expose
    open var following: Int = 0

    @SerializedName("public_gists")
    @Expose
    open var publicGists: Int = 0

    @SerializedName("created_at")
    @Expose
    open var createdAt: Date? = null

    // Only used from realm
    open var subscriptions: RealmList<Subscription> = RealmList()
    open var gists: RealmList<Gist> = RealmList()
    open var followingUsers: RealmList<User> = RealmList()
}

