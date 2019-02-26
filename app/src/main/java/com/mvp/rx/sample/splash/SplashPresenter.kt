package com.mvp.rx.sample.splash

import com.mvp.rx.sample.base.BasePresenter
import com.mvp.rx.sample.data.user.UserRepository
import com.mvp.rx.sample.extensions.addTo
import com.mvp.rx.sample.extensions.applyIoAndMainThreads

class SplashPresenter(private val view: ISplashContract.View): BasePresenter(), ISplashContract.Presenter {

    override fun isLoggedIn() {
        UserRepository.getInstance().isLoggedIn(view.getViewContext())
                .applyIoAndMainThreads()
                .subscribe(
                        { onLoggedInEventSuccess(it) },
                        { onLoggedInEventFailure() }
                )
                .addTo(compositeDisposable)
    }

    private fun onLoggedInEventSuccess(isLoggedIn: Boolean) {
        if (view.isActive()) {
            view.onLoggedInEventSuccess(isLoggedIn)
        }
    }

    private fun onLoggedInEventFailure() {
        if (view.isActive()) {
            view.onLoggedInEventFailure()
        }
    }

}