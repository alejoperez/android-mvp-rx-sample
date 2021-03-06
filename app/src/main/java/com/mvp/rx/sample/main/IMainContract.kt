package com.mvp.rx.sample.main

import com.mvp.rx.sample.base.IBaseView
import com.mvp.rx.sample.data.User

interface IMainContract {

    interface View: IBaseView {
        fun onLogOutSuccess()
        fun onLogOutFailure()
    }

    interface Presenter {
        fun getUser(): User?
        fun logout()
    }

}