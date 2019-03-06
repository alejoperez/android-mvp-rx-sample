package com.mvp.rx.sample.application

import androidx.multidex.MultiDexApplication
import io.realm.Realm
import io.realm.RealmConfiguration

class SampleApp: MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        initRealm()
    }

    private fun initRealm() {
        Realm.init(this)
        val config = RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build()
        Realm.setDefaultConfiguration(config)
    }

}