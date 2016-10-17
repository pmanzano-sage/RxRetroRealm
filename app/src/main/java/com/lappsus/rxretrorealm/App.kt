package com.lappsus.rxretrorealm

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class App : Application() {
  override fun onCreate() {
    super.onCreate()
    val realmConfig = RealmConfiguration.Builder(
        this).deleteRealmIfMigrationNeeded().build()
    Realm.setDefaultConfiguration(realmConfig)
  }
}