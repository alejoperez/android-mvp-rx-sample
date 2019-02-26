package com.mvp.rx.sample.register

import com.mvp.rx.sample.R
import com.mvp.rx.sample.base.BasePresenter
import com.mvp.rx.sample.data.user.UserRepository
import com.mvp.rx.sample.extensions.addTo
import com.mvp.rx.sample.extensions.applyIoAndMainThreads
import com.mvp.rx.sample.webservice.RegisterRequest

class RegisterPresenter(private val view: IRegisterContract.IRegisterView): BasePresenter(), IRegisterContract.IRegisterPresenter {

    override fun isValidName(name: String): Boolean = name.isNotEmpty()

    override fun isValidEmail(email: String): Boolean = email.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()

    override fun isValidPassword(password: String): Boolean = password.isNotEmpty()

    override fun isValidForm(name: String, email: String, password: String): Boolean = isValidName(name) && isValidEmail(email) && isValidPassword(password)

    override fun register(name: String, email: String, password: String) {

        UserRepository.getInstance().register(view.getViewContext(), RegisterRequest(name, email, password))
                .applyIoAndMainThreads()
                .doOnSubscribe { view.showProgress() }
                .doAfterTerminate { hideProgress() }
                .subscribe(
                        { onRegisterSuccess() },
                        { onRegisterFailure() }
                )
                .addTo(compositeDisposable)
    }

    private fun onRegisterSuccess() {
        if (view.isActive()) {
            view.hideProgress()
            view.onRegisterSuccess()
        }
    }

    private fun onRegisterFailure() {
        if (view.isActive()) {
            view.hideProgress()
            view.onRegisterFailure()
        }
    }

    private fun hideProgress() {
        if (view.isActive()) {
            view.hideProgress()
        }
    }

}