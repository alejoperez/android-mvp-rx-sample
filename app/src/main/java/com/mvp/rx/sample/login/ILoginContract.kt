package com.mvp.rx.sample.login

import com.mvp.rx.sample.base.IBaseView

interface ILoginContract {

    interface View : IBaseView{
        fun getEmail(): String
        fun getPassword(): String
        fun showProgress()
        fun hideProgress()
        fun onLoginSuccess()
        fun onLoginFailure()
    }

    interface Presenter {
        fun isValidEmail(email: String): Boolean
        fun isValidPassword(password: String): Boolean
        fun isValidForm(email: String, password: String): Boolean
        fun login(email:String, password: String)
    }
}