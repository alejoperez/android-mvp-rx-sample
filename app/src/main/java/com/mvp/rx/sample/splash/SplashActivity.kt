package com.mvp.rx.sample.splash

import android.os.Bundle
import android.os.Handler
import com.mvp.rx.sample.R
import com.mvp.rx.sample.base.BaseActivity
import com.mvp.rx.sample.main.MainActivity
import com.mvp.rx.sample.register.RegisterActivity
import org.jetbrains.anko.startActivity

private const val SPLASH_DELAY = 2000L

class SplashActivity : BaseActivity(), ISplashContract.View {

    private val presenter by lazy { SplashPresenter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({ goToNextScreen() }, SPLASH_DELAY)
    }

    override fun goToNextScreen() {
        if (presenter.isLoggedIn()) {
            startActivity<MainActivity>()
        } else {
            startActivity<RegisterActivity>()
        }
        finish()
    }
}
