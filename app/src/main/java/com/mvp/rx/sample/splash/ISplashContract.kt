package com.mvp.rx.sample.splash

import com.mvp.rx.sample.base.IBaseView

interface ISplashContract {

    interface View: IBaseView {
        fun goToNextScreen()
    }

    interface Presenter {
        fun isLoggedIn(): Boolean
    }

}