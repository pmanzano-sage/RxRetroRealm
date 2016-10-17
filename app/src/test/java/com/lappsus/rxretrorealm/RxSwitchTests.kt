package com.lappsus.rxretrorealm

import org.junit.Test
import rx.Observable
import java.util.*


/**
 * @author Pablo Manzano
 * @since 12/10/16
 *
 * Basic tests for:
 *  - switchIfEmpty
 *  - switchMap
 */
class RxSwitchTests {

    inner class Person(val name: String, var age: Int?) {
        override fun toString(): String {
            return "$name:$age"
        }
    }

    /**
     * If the initial observable is empty, we emit a "default" value Person{name='Neil', age=0}
     */
    @Test
    fun testSwitchEmpty1() {
        Observable.just(ArrayList<Person>())
                .flatMap { persons -> Observable.from(persons).switchIfEmpty(Observable.just(Person("Neil", 0))) }
                .subscribe(::println)
    }

    /**
     * SwitchIfEmpty will not switch to alternative observable because the origin observable is not empty.
     * Emitted: Person{name='Vivien', age=29}
     */
    @Test
    fun testSwitchEmpty2() {
        val people = ArrayList<Person>()
        people.add(Person("Vivien", 29))
        Observable.just(people).flatMap { persons -> Observable.from(persons).switchIfEmpty(Observable.just(Person("John", 0))) }
                .subscribe(::println)
    }

    /**
     * Switch from original item to a new observable.
     * Note that the initial observable is unsubscribed in order to subscribe to the new one.
     * If our observable is a network request, it will be cancelled.
     */
    @Test
    fun testSwitchMap() {
        Observable.just(Person("Pablo", 43)).switchMap { person -> Observable.just(Person("Pablo", 25)) }
                .subscribe(::println)
    }
}