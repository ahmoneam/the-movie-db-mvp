package com.moneam.themoviedb.login

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.test.espresso.idling.CountingIdlingResource
import com.moneam.themoviedb.R
import com.moneam.themoviedb.login.LoginActivity.Companion.resource
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), IView {

    companion object {
        val resource = CountingIdlingResource("New Thread", true)
    }

    override fun startHomeScreen() {
        tv_message.text = "success"
    }

    private lateinit var presenter: Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        presenter = Presenter(this, Model())

        btn_login.setOnClickListener {
            presenter.login(et_username.text.toString(), et_password.text.toString())
        }
    }

    override fun showError(error: Int) {
//        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
        tv_message.setText(error)
    }

}

interface IView {
    fun showError(error: Int)
    fun startHomeScreen()
}

interface IModel {
    fun login(username: String, password: String, callback: (success: Boolean) -> Unit)

    fun login(username: String, password: String): Boolean
}

class Model : IModel {
    override fun login(
        username: String,
        password: String,
        testtesttest: (success: Boolean) -> Unit
    ) {
        resource.increment()
        Handler().postDelayed(Runnable {
            if (username.contains("test")) {
                testtesttest.invoke(true)
            } else {
                testtesttest.invoke(false)
            }
            resource.decrement()
        }, 3000)
    }


    override fun login(username: String, password: String): Boolean {
        return username.contains("test")
    }
}

class Presenter(val view: IView, val model: IModel) {
    fun login(username: String, password: String) {

        if (!validateUserName(username)) {
            view.showError(R.string.error_invalid_user_name)
            return
        }

        if (!validatePassword(password)) {
            view.showError(R.string.error_password_required)
            return
        }

        if (!validatePassword1(password)) {
            view.showError(R.string.error_invalid_password)
            return
        }

        model.login(username, password) {
            if (it) {
                view.startHomeScreen()
            } else {
                view.showError(R.string.error_invalid_user_name_or_password)
            }
        }

        /*val loginResult = model.login(username, password)

        if (loginResult) {
            view.startHomeScreen()
        } else {
            view.showError(R.string.error_invalid_user_name_or_password)
        }*/
    }

    private fun validateUserName(username: String): Boolean {
        return username.length != 0
    }


    private fun validatePassword(password: String): Boolean {
        return password.length != 0
    }

    private fun validatePassword1(password: String): Boolean {
        return password.length >= 8
    }
}
