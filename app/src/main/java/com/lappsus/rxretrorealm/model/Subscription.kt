package com.lappsus.rxretrorealm.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

/**
 *
 */
@RealmClass
open class Subscription : RealmObject() {

    @PrimaryKey
    @SerializedName("id")
    @Expose
    open var id: Int = 0

    @SerializedName("name")
    @Expose
    open var name: String? = null

    @SerializedName("description")
    @Expose
    open var description: String? = null

    @SerializedName("private")
    @Expose
    open var private: Boolean? = null

    @SerializedName("fork")
    @Expose
    open var fork: Boolean? = null
}

