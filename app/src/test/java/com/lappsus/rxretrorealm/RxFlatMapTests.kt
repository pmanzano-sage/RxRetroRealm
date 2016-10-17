package com.lappsus.rxretrorealm

import org.junit.Test
import rx.Observable
import java.lang.System.out
import java.util.concurrent.TimeUnit


/**
 * @author Pablo Manzano
 * @since 12/10/16
 */


class RxFlatMapTests {

    /**
     * FlatMap behaves very much like map, the difference is that it returns an observable itself, so its perfectly
     * suited to map over asynchronous operations.
     */
    @Test
    fun testArrayList() {
        out.println("ArrayList")
        val arr1 = arrayListOf(1, 2, 3)
        val arr2 = arrayListOf(4, 5)
        val arr3 = arrayListOf(6)
        val source = Observable.just(arr1, arr2, arr3)
        source.flatMap { arr -> Observable.from(arr) }
                .subscribe { out.println(it) }

    }

    /**
     * Basic synchronous example of flatmap that is very naive to really understand what's going on.
     */
    @Test
    fun testFlatMap1() {
        out.println("flatMap1")
        Observable.range(1, 3).flatMap { v -> Observable.range(v, 2) }.subscribe({ out.println(it) })
    }

    @Test
    fun testFlatMap2() {
        out.println("flatMap2")
        Observable.range(1, 3)
                .flatMap { v -> Observable.just(v).delay(300 - v.toLong(), TimeUnit.MILLISECONDS) }
                .toBlocking()
                .subscribe { out.println(it) }
    }

    /**
     * MaxConcurrent param for FlatMap
     */
    @Test
    fun testObservableChain() {
        Observable.just(arrayOf(1, 2, 3, 4, 5), arrayOf(6, 7, 8, 9, 10))
                .flatMap { num ->
                    Observable.from(num)
                            .flatMap({ i1 ->
                                Observable.just(i1)
                                        .doOnNext { item -> println("sending:" + item) }
                                        .delay(50, TimeUnit.MILLISECONDS)
                            }, 1)
                            .flatMap({ i2 ->
                                Observable.just(i2)
                                        .delay(50, TimeUnit.MILLISECONDS)
                                        .doOnNext { i3 -> println("completed:" + i3) }
                            }, 1)
                }
                .toBlocking().last()
    }
}