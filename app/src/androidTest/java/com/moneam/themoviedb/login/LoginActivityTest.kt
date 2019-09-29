package com.moneam.themoviedb.login


import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.moneam.themoviedb.R
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class LoginActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(LoginActivity::class.java)

    @Before
    fun setUp() {
        Espresso.registerIdlingResources(LoginActivity.resource)
    }

    @After
    fun tearDown() {
        Espresso.unregisterIdlingResources(LoginActivity.resource)
    }

    @Test
    fun loginActivityTestUserNameRequired() {
        val btnLogin = onView(withId(R.id.btn_login))

        btnLogin.perform(click())

        val tvMessage = onView(withId(R.id.tv_message))

        tvMessage.check(matches(withText(R.string.error_invalid_user_name)))
    }

    @Test
    fun loginActivityTestPasswordRequired() {
        val etUserName = onView(withId(R.id.et_username))

        etUserName.perform(
            replaceText("tttt"),
            ViewActions.closeSoftKeyboard()
        )

        val btnLogin = onView(withId(R.id.btn_login))

        btnLogin.perform(click())

        val tvMessage = onView(withId(R.id.tv_message))

        tvMessage.check(matches(withText(R.string.error_password_required)))
    }

    @Test
    fun loginActivityTestPasswordInvalid() {
        val etUsername = onView(withId(R.id.et_username))

        etUsername.perform(
            replaceText("jdgsahgfj"),
            closeSoftKeyboard()
        )

        val etPassword = onView(withId(R.id.et_password))

        etPassword.perform(
            replaceText("smsm"),
            closeSoftKeyboard()
        )

        val btnLogin = onView(withId(R.id.btn_login))

        btnLogin.perform(click())

        val tvMessage = onView(withId(R.id.tv_message))

        tvMessage.check(matches(withText(R.string.error_invalid_password)))
    }

    @Test
    fun loginActivityTestInvalidUsernameOrPassword() {
        val etUsername = onView(withId(R.id.et_username))
        val etPassword = onView(withId(R.id.et_password))
        val tvMessage = onView(withId(R.id.tv_message))
        val btnLogin = onView(withId(R.id.btn_login))

        // given
        etUsername.perform(replaceText("hasjh"))
        etPassword.perform(replaceText("12345678"))

        // when
        btnLogin.perform(click())

        // then
        tvMessage.check(matches(withText(R.string.error_invalid_user_name_or_password)))
    }

    @Test
    fun loginActivityTestLoginWithUsernameOrPasswordSuccess() {
        val etUsername = onView(withId(R.id.et_username))
        val etPassword = onView(withId(R.id.et_password))
        val tvMessage = onView(withId(R.id.tv_message))
        val btnLogin = onView(withId(R.id.btn_login))

        // given
        etUsername.perform(replaceText("dhtest"))
        etPassword.perform(replaceText("12345678"))

        // when
        btnLogin.perform(click())

        // then
        tvMessage.check(matches(withText("success")))
    }
}
