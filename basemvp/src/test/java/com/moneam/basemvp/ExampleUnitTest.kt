package com.moneam.basemvp

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    var disposable: Disposable? = null

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun test_rx_observable() {
        val observable = Observable.create<Int>
        {
            for (i in 1..10) {
                if (!it.isDisposed) {
                    it.onNext(i)
                }
            }
            it.onComplete()
        }

        val observer = object : Observer<Int> {
            override fun onComplete() {
                println("onComplete")
            }

            override fun onSubscribe(d: Disposable) {
                println("onSubscribe")
            }

            override fun onNext(t: Int) {
                println("onNext: $t")
            }

            override fun onError(e: Throwable) {
                println("onError: ${e.message}")
            }
        }

        disposable =
            Observable.create<Int>
            {
                for (i in 1..10) {
                    if (!it.isDisposed) {
                        it.onNext(i)
                    }
                }
                it.onComplete()
            }.filter { it % 2 == 0 }
                .takeLast(3)
                .flatMap { Observable.just("next is $it") }
                .subscribe(
                    {
                        println("onNext: $it")
                        disposable?.dispose()
                    },
                    {
                        println("onError: ${it.message}")
                    },
                    {
                        println("onComplete")
                    },
                    {
                        println("onSubscribe")

                    })

//        disposable.dispose()
//            .subscribe(observer)
    }

    @Test
    fun test_rx_single() {
        val observable = Single.create<Int>
        {
            //            it.onSuccess(10)
            it.onError(RuntimeException("error from calling method"))
        }

        var index = 0


        observable
            .doOnSuccess {
                println("doOnSuccess: $it")
            }
            .doOnError {
                println("doOnError: ${it.message} $index")
                index++
            }
            .flatMap { Single.just("next is $it") }
            .retry(10) {
                it is StringIndexOutOfBoundsException
            }
            .subscribe(
                {
                    println("onNext: $it")
                    disposable?.dispose()
                },
                {
                    println("onError: ${it.message}")
                })
    }
}
