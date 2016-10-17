package com.lappsus.rxretrorealm

import org.junit.Test
import rx.Observable


/**
 * @author Pablo Manzano
 * @since 12/10/16
 */


class RxErrorTests {

    fun randomInt(min: Int, max: Int): Int {
        return Math.round((max - min) * Math.random() + min).toInt()
    }

    fun failOnZeroRemainder(i: Int) {
        val div = randomInt(2, 4)
        if (i.mod(div) === 0) {
            throw IllegalArgumentException("remainder of $i / $div is 0")
        }
    }

    fun failOnBigNumber(i: Int) {
        val big = randomInt(2, 4)
        if (i > big) {
            throw IllegalArgumentException("$i > $big! Not supported!")
        }
    }


    /**
     * Once an exception is thrown, that observable won't emit any more items.
     */
    fun mayFail(): Observable<Int?> {
        val numbers = arrayOf(1, 2, 3, 4)
        return Observable.from(numbers)
                .doOnNext { number ->
                    failOnZeroRemainder(number)
                    failOnBigNumber(number)
                }
    }

    @Test
    fun observableException1() {
        mayFail().subscribe({ println("OBS1: $it") }, { System.err.println("OBS1: $it") })
    }

    @Test
    fun observableException2() {
        mayFail().doOnError { t -> System.err.println("Oopps2: " + t.message) }
                .subscribe({ println("OBS2: $it") }, { System.err.println("OBS2: $it") })
    }

    @Test
    fun observableException3() {
        mayFail()
                .doOnError { t -> System.err.println("Oopps3: " + t.message) }
                .subscribe({ println("OBS3: $it") }, { System.err.println("OBS3: $it") })
    }

}