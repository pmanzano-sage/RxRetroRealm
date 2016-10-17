package com.lappsus.rxretrorealm

import org.junit.Test
import rx.Observable
import java.lang.System.out
import kotlin.test.assertEquals


/**
 * @author Pablo Manzano
 * @since 12/10/16
 */


class RxZipTests {

    /**
     * Zip, pretty clear.
     */
    @Test
    fun testZip() {
        out.println("Zip")
        val observable1 = Observable.from(arrayOf(1, 2, 3, 4))
        val observable2 = Observable.from(arrayOf(6, 7, 8, 9, 10))
        val zippedObservable = Observable.zip(observable1, observable2) { int1, int2 -> int1 + int2 }

        // Note: If observable1 and 2 doesn't emit the same number of items, zip will only emit the minimum of the two.
        zippedObservable.subscribe { out.println(it) }

        // How to count the number of items emitted by an Observable
        assertEquals(4, zippedObservable.count().toBlocking().single())
    }

}