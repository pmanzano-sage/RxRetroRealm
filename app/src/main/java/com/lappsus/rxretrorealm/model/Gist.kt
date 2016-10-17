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
open class Gist : RealmObject() {

    @PrimaryKey
    @SerializedName("id")
    @Expose
    open var id: String? = null

    @SerializedName("url")
    @Expose
    open var url: String? = null

    @SerializedName("description")
    @Expose
    open var description: String? = null

    @SerializedName("public")
    @Expose
    open var public: Boolean? = null

}

