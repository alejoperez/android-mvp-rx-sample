package com.mvp.rx.sample.data.user

import android.content.Context
import com.mvp.rx.sample.data.User
import com.mvp.rx.sample.storage.PreferenceManager
import com.mvp.rx.sample.webservice.LoginRequest
import com.mvp.rx.sample.webservice.LoginResponse
import com.mvp.rx.sample.webservice.RegisterRequest
import com.mvp.rx.sample.webservice.RegisterResponse
import io.reactivex.Completable
import io.reactivex.Single
import io.realm.Realm

class UserLocalDataSource : IUserDataSource {

    override fun isLoggedIn(context: Context): Single<Boolean> = Single.just(PreferenceManager<String>(context).findPreference(PreferenceManager.ACCESS_TOKEN,"").isNotEmpty())

    override fun getUser(): User? = Realm.getDefaultInstance().where(User::class.java).findFirst()

    override fun saveUser(user: User) {
        Realm.getDefaultInstance().executeTransactionAsync {
            it.insertOrUpdate(user)
        }
    }

    override fun logout(context: Context): Completable {
        PreferenceManager<String>(context).putPreference(PreferenceManager.ACCESS_TOKEN,"")
        return Completable.complete()
    }

    override fun login(context: Context, request: LoginRequest): Single<LoginResponse> = throw UnsupportedOperationException()

    override fun register(context: Context, request: RegisterRequest): Single<RegisterResponse> = throw UnsupportedOperationException()

}