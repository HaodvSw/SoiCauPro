package com.toolfindxs.xsmb_soicau.data.mvp

import com.core.MvpView
import java.lang.ref.WeakReference

abstract class BasePresenter<in V : MvpView>  {
    private val isViewAttached: Boolean
        get() = weakView != null && weakView?.get() != null

    private var weakView: WeakReference<V>? = null

    internal val view: @UnsafeVariance V?
        get() = weakView?.get()

     fun attachView(view: V) {
        if (!isViewAttached) {
            weakView = WeakReference(view)
        }
    }

     fun detachView() {
        weakView?.clear()
        weakView = null
    }

}