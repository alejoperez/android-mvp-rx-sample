package com.mvp.rx.sample.base

import android.content.Context

interface IBaseView {

    fun isActive(): Boolean

    fun showAlert(textResource: Int)

    fun getViewContext(): Context
}