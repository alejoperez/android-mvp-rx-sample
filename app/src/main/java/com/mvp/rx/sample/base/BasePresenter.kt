package com.mvp.rx.sample.base

import io.reactivex.disposables.CompositeDisposable

abstract class BasePresenter(val compositeDisposable: CompositeDisposable = CompositeDisposable()): IBasePresenter {

    override fun unsubscribe() = compositeDisposable.dispose()

}