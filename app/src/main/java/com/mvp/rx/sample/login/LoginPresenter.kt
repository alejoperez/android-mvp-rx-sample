package com.mvp.rx.sample.login

import com.mvp.rx.sample.R
import com.mvp.rx.sample.base.BasePresenter
import com.mvp.rx.sample.data.user.UserRepository
import com.mvp.rx.sample.extensions.addTo
import com.mvp.rx.sample.extensions.applyIoAndMainThreads
import com.mvp.rx.sample.webservice.LoginRequest

class LoginPresenter(private val view: ILoginContract.View) : BasePresenter(), ILoginContract.Presenter {

    override fun isValidEmail(email: String): Boolean = email.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()

    override fun isValidPassword(password: String): Boolean = password.isNotEmpty()

    override fun isValidForm(email: String, password: String): Boolean = isValidEmail(email) && isValidPassword(password)

    override fun login(email: String, password: String) {
        UserRepository.getInstance()
                .login(view.getViewContext(), LoginRequest(email, password))
                .applyIoAndMainThreads()
                .doOnSubscribe { view.showProgress() }
                .doAfterTerminate { hideProgress() }
                .subscribe(
                        {onLoginSuccess()},
                        {onLoginError()}
                )
                .addTo(compositeDisposable)
    }

    private fun onLoginError() {
        if (view.isActive()) {
            view.hideProgress()
            view.onLoginFailure()
        }
    }

    private fun onLoginSuccess() {
        if (view.isActive()) {
            view.hideProgress()
            view.onLoginSuccess()
        }
    }

    private fun hideProgress() {
        if (view.isActive()) {
            view.hideProgress()
        }
    }

}