package com.toolfindxs.xsmb_soicau.di

import com.toolfindxs.xsmb_soicau.ui.home.HomeViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val view_module: Module = module {
    viewModel {
        HomeViewModel(get(), get())
    }

}