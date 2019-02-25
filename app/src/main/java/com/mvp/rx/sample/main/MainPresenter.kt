package com.mvp.rx.sample.main

import com.mvp.rx.sample.data.user.UserRepository

class MainPresenter(private val view: IMainContract.View): IMainContract.Presenter {

    override fun getUser() = UserRepository.getInstance().getUser()

    override fun logout() = UserRepository.getInstance().logout(view.getViewContext())

}