package com.mvp.rx.sample.splash

import com.mvp.rx.sample.base.IBaseView

interface ISplashContract {

    interface View: IBaseView {
        fun onLoggedInEventSuccess(isLoggedIn: Boolean)
        fun onLoggedInEventFailure()
    }

    interface Presenter {
        fun isLoggedIn()
    }

}