package com.toolfindxs.xsmb_soicau.data.mvp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.core.BaseFragment
import com.core.MvpView
import com.toolfindxs.xsmb_soicau.ui.MainActivity

abstract class BaseMvpFragment<T : ViewBinding, V : MvpView, P : BasePresenter<V>> :
    BaseFragment<T> {
    var presenter: P? = null

    constructor() : super()

    protected lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        attachView()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    abstract fun initMvpPresenter(): P
    override fun updateUI(savedInstanceState: Bundle?) {
        mainActivity = activity as MainActivity
        initView()
        initData()
        initAction()

    }


    override fun onDestroyView() {
        detachView()
        super.onDestroyView()
    }

    abstract fun initView()
    abstract fun initData()
    abstract fun initAction()

    private fun attachView() {
        if (presenter == null) {
            presenter = initMvpPresenter()
        }
        presenter?.attachView(this as V)
    }

    private fun detachView() {
        presenter?.detachView()
    }
}