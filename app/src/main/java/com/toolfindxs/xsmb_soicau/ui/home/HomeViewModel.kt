package com.toolfindxs.xsmb_soicau.ui.home

import com.toolfindxs.xsmb_soicau.MainApp
import com.toolfindxs.xsmb_soicau.data.model.Code
import com.toolfindxs.xsmb_soicau.data.model.CodeOther
import com.toolfindxs.xsmb_soicau.data.mvp.BaseViewModelZ
import com.toolfindxs.xsmb_soicau.data.remote.IApiRepository
import com.toolfindxs.xsmb_soicau.utils.Constants
import com.toolfindxs.xsmb_soicau.utils.asDateFormat
import com.utils.SchedulerProvider
import io.reactivex.subjects.PublishSubject

class HomeViewModel(
    private val apiHelper: IApiRepository,
    schedulerProvider: SchedulerProvider,
) : BaseViewModelZ(schedulerProvider) {

    private var amountRequest: Int = Constants.INT_DEFAULT
    private var listData: MutableList<CodeOther> = mutableListOf()
    val rxResult: PublishSubject<Code> = PublishSubject.create()
    val rxResultOther: PublishSubject<Pair<MutableList<CodeOther>, String>> = PublishSubject.create()


    fun getResult(local: String) {
        apiHelper.getDataTypeOne("K263f4d320e4c15", MainApp.getInstance().dateSelect.toDate().asDateFormat(), local)
            .onLoading()
            .compose(schedulerProvider.ioToMainSingleScheduler())
            .subscribe({ response ->
                val data = response[0].code
                data?.let { rxResult.onNext(data) }
            }, {
                rxMessage.onNext(it.msgError)
            }).addToBag()
    }

    fun getResultOther(locals: MutableList<String>) {
        listData.clear()
        amountRequest = locals.size
        for (i in locals.indices) {
            resultOther(locals[i].replace(" ", ""))
        }
    }

    private fun resultOther(local: String) {
        apiHelper.getDataTypeTow("K263f4d320e4c15", MainApp.getInstance().dateSelect.toDate().asDateFormat(), local)
            .onLoading()
            .compose(schedulerProvider.ioToMainSingleScheduler())
            .subscribe({ response ->
                response[0].code?.let {
                    listData.add(it)
                    amountRequest -= 1
                    if (amountRequest == 0) rxResultOther.onNext(Pair(listData, response[0].officialissue?:""))
                }
            }, {
                rxMessage.onNext(it.msgError)
            }).addToBag()
    }


}