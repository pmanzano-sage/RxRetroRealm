package com.lappsus.rxretrorealm

import org.junit.Test
import rx.Observable
import java.lang.System.out


/**
 * @author Pablo Manzano
 * @since 12/10/16
 */


class RxReduceTests {

    @Test
    fun testReduce() {
        out.println("reduce")
        val source = Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon")
        source.reduce { s1, s2 -> "$s1$s2" }
                .subscribe { out.println(it) }
    }
}