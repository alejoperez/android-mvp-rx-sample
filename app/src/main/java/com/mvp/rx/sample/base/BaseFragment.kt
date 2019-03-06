package com.mvp.rx.sample.base

import android.content.Context
import androidx.fragment.app.Fragment
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton

abstract class BaseFragment: Fragment(), IBaseView {

    private lateinit var fragmentContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentContext = context
    }

    override fun isActive(): Boolean = isAdded

    override fun showAlert(textResource: Int) {
        activity?.let {
            if (isActive() && !it.isFinishing) {
                it.alert(textResource) {
                    yesButton { dialog -> dialog.dismiss() }
                }.show()
            }
        }
    }

    override fun getViewContext(): Context = fragmentContext
}