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

class UserRepository private constructor(
        private val localDataSource: IUserDataSource = UserLocalDataSource(),
        private val remoteDataSource: IUserDataSource = UserRemoteDataSource()) : IUserDataSource {

    companion object {
        @Volatile
        private var INSTANCE: UserRepository? = null

        fun getInstance(): UserRepository = INSTANCE ?: synchronized(this) {
                    INSTANCE ?: UserRepository().also { INSTANCE = it }
        }
    }

    override fun saveUser(user: User) = localDataSource.saveUser(user)

    override fun getUser(): User? = localDataSource.getUser()

    override fun login(context: Context, request: LoginRequest): Single<LoginResponse> =
            remoteDataSource.login(context, request)
                    .doOnSuccess {
                        PreferenceManager<String>(context).putPreference(PreferenceManager.ACCESS_TOKEN, it.accessToken)
                        localDataSource.saveUser(it.toUser())
                    }

    override fun register(context: Context, request: RegisterRequest): Single<RegisterResponse> =
        remoteDataSource.register(context, request)
                .doOnSuccess {
                    PreferenceManager<String>(context).putPreference(PreferenceManager.ACCESS_TOKEN, it.accessToken)
                    localDataSource.saveUser(it.toUser())
                }

    override fun isLoggedIn(context: Context): Single<Boolean> = localDataSource.isLoggedIn(context)

    override fun logout(context: Context): Completable = localDataSource.logout(context)

}
