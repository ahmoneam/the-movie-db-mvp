package com.moneam.themoviedb.login

import com.moneam.themoviedb.R
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.verify
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.ArgumentMatchers.contains
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


class LoginPresenterTest {

    private lateinit var presenter: Presenter

    @Mock
    private lateinit var view: IView

    @Mock
    private lateinit var model: IModel

    val callbackArgCaptor = argumentCaptor<(Boolean) -> Unit>()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = Presenter(view, model)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun login_invalid_username_test() {
        presenter.login("", "dsdffd")

        Mockito.verify(view).showError(R.string.error_invalid_user_name)
    }

    @Test
    fun login_required_password_test() {
        presenter.login("dfdfd", "")

        Mockito.verify(view).showError(R.string.error_password_required)
    }

    @Test
    fun login_invalid_password_test() {
        presenter.login("dfdfd", "1234567")

        Mockito.verify(view).showError(R.string.error_invalid_password)
    }

    @Test
    fun login_network_success_test() {
        // when
        Mockito.`when`(model.login(contains("test"), anyString()))
            .thenReturn(true)

        // given
        presenter.login("testjhasgdjhagsjdhg", "12345678")

        // then
        verify(view).startHomeScreen()
    }

//    @Test
//    fun login_network_fail_test() {
//        // when
//        Mockito.`when`(model.login(anyString(), anyString()))
//            .thenReturn(false)
//
//        // given
//        presenter.login("ashjdghajs", "asndbamsbdmnabsmdb")
//
//        // then
//        verify(view).showError(R.string.error_invalid_user_name_or_password)
//    }


    //    @Test
//    fun login_network_success_callback_test() {
//
//        Mockito.`when`(
//            model.login(
//                Mockito.anyString(),
//                Mockito.anyString(),
//                callbackArgCaptor.capture()
//            )
//        ).then {
//            callbackArgCaptor.firstValue.invoke(true)
//        }
//
//        presenter.login("test123344", "12345678")
//
//        Mockito.verify(view).startHomeScreen()
//    }
//
    @Test
    fun login_network_fail_callback_test() {

        Mockito.`when`(
            model.login(
                Mockito.anyString(),
                Mockito.anyString(),
                callbackArgCaptor.capture()
            )
        ).then {
            callbackArgCaptor.firstValue.invoke(false)
        }

        presenter.login("123344", "12345678")

        Mockito.verify(view).showError(R.string.error_invalid_user_name_or_password)
    }
}