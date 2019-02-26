package com.mvp.rx.sample.main

import com.mvp.rx.sample.base.BasePresenter
import com.mvp.rx.sample.data.User
import com.mvp.rx.sample.data.user.UserRepository
import com.mvp.rx.sample.extensions.addTo
import com.mvp.rx.sample.extensions.applyIoAndMainThreads

class MainPresenter(private val view: IMainContract.View): BasePresenter(), IMainContract.Presenter {

    override fun getUser(): User? = UserRepository.getInstance().getUser()

    override fun logout() {
        UserRepository.getInstance().logout(view.getViewContext())
                .applyIoAndMainThreads()
                .subscribe(
                        {view.onLogOutSuccess()},
                        {view.onLogOutFailure()}
                )
                .addTo(compositeDisposable)
    }

}