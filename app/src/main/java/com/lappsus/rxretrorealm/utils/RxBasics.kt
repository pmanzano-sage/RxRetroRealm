package com.lappsus.rxretrorealm.utils

import android.util.Log
import rx.Observer

/**
 * @author Pablo Manzano
 * @since 11/10/16
 */

val TAG = "RxBasics"

val stringObserver: Observer<String> = object : Observer<String> {

    override fun onCompleted() {
        Log.d(TAG, "Done!")
    }

    override fun onError(e: Throwable) {
        Log.d(TAG, "Error: ${e.message}")
    }

    override fun onNext(s: String) {
        Log.d(TAG, s)
    }
}

val integerObserver: Observer<Int> = object : Observer<Int> {

    override fun onCompleted() {
        Log.d(TAG, "Done!")
    }

    override fun onError(e: Throwable) {
        Log.d(TAG, "Error: ${e.message}")
    }

    override fun onNext(i: Int) {
        Log.d(TAG, "$i")
    }
}

