package com.toolfindxs.xsmb_soicau.data.mvp

import androidx.lifecycle.ViewModel
import com.core.adapterz.StatusAdapterZ
import com.toolfindxs.xsmb_soicau.data.remote.config.NetworkInterceptor
import com.toolfindxs.xsmb_soicau.R
import com.utils.SchedulerProvider
import com.utils.ext.postNormal
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import retrofit2.HttpException
import java.net.ConnectException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class BaseViewModelZ(
    val schedulerProvider: SchedulerProvider
) : ViewModel() {

    val isLoading: PublishSubject<Boolean> = PublishSubject.create()
    val rxMessage: PublishSubject<String> = PublishSubject.create()

    val disposable by lazy { CompositeDisposable() }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    fun Disposable.addToBag() {
        disposable.add(this)
    }

    companion object {
        private const val PAGE_DEFAULT = 1
        private const val INTEGER_DEFAULT = 1
    }

    fun <T> Single<T>.onLoading(): Single<T> {
        return this.doOnSubscribe { isLoading.onNext(true) }
            .doFinally { isLoading.onNext(false) }
    }

    fun PublishSubject<StatusAdapterZ>.onStatusLoading(
        isLoading: Boolean,
        page: Int = PAGE_DEFAULT, isRefresh: Boolean = false
    ) {
        onNext(
            if (page == PAGE_DEFAULT) {
                if (isRefresh) {
                    StatusAdapterZ.Refresh(isLoading)
                } else {
                    StatusAdapterZ.Loading(isLoading)
                }
            } else {
                StatusAdapterZ.LoadMore(isLoading)
            }
        )
    }

    fun PublishSubject<StatusAdapterZ>.onStatusError(
        msg: String? = null,
    ) {
        onNext(StatusAdapterZ.Error(msg, null, false, null))
    }

    fun PublishSubject<StatusAdapterZ>.onStatusError(
        msg: String? = null,
        showButton: Boolean = false,
    ) {
        onNext(StatusAdapterZ.Error(msg, null, showButton, null))
    }

    fun PublishSubject<StatusAdapterZ>.onStatusError(
        msg: String? = null,
        showButton: Boolean = false,
        requestCode: Int = INTEGER_DEFAULT
    ) {
        onNext(StatusAdapterZ.Error(msg, null, showButton, null, requestCode))
    }

    fun PublishSubject<StatusAdapterZ>.onStatusError(
        msg: String? = null,
        src: Int? = null,
        showButton: Boolean = false,
        requestCode: Int = INTEGER_DEFAULT
    ) {
        onNext(StatusAdapterZ.Error(msg, src, showButton, null, requestCode))
    }

    fun PublishSubject<StatusAdapterZ>.onStatusError(
        msg: String?,
        src: Int?,
        showButton: Boolean,
        textButton: String?,
        requestCode: Int = INTEGER_DEFAULT
    ) {
        onNext(StatusAdapterZ.Error(msg, src, showButton, textButton, requestCode))
    }

    fun PublishSubject<StatusAdapterZ>.onStatusErrorCastJson() {
        onNext(StatusAdapterZ.Error(msgJsonError, null, false, null))
    }

    fun PublishSubject<StatusAdapterZ>.onStatusThrowable(throwable: Throwable) {
        onNext(throwable.onStatusError)
    }

    fun PublishSubject<StatusAdapterZ>.onStatusEmpty(
        msg: String? = null,
        showButton: Boolean = false,
        textButton: String? = null,
    ) {
        onNext(
            StatusAdapterZ.Error(
                msg,
                R.drawable.ic_home,
                showButton,
                textButton,
                StatusAdapterZ.REQUEST_NO_DATA
            )
        )
    }

    val msgJsonError: String =
        "Dữ liệu không chính xác.Vui lòng liên hệ với nhà phát triển để khắc phục sự cố này"

    val Throwable.msgError: String
        get() {
            return when (this) {
                is NetworkInterceptor.NoConnectivityException -> "Không có kết nối mạng.\nKiểm tra lại kết nối 3G/WIFI của bạn"
                is ConnectException -> "Không thể kết nối đến server\nVui lòng thử lại."
                is SocketTimeoutException -> "Không thể kết nối đến server\nVui lòng thử lại."
                is UnknownHostException -> "Không thể kết nối đến server\nVui lòng thử lại."
                is HttpException -> {
                    when (this.code()) {
                        HttpURLConnection.HTTP_UNAUTHORIZED -> {
                            postNormal(EventUnAuthen())
                            "Phiên đăng nhập đã hết hạn\nVui lòng đăng nhập lại"
                        }
                        HttpURLConnection.HTTP_INTERNAL_ERROR -> {
                            "Server đang bảo trì.Vui lòng thử lại sau ít phút"
                        }
                        else -> this.response()?.message() ?: "unknown error"
                    }
                }
                else -> this.message ?: "unknown error"
            }
        }

    val Throwable.onStatusError: StatusAdapterZ.Error
        get() {
            return StatusAdapterZ.Error(
                this.message ?: "unknown error",
                R.drawable.ic_home,
                false,
            )
        }
}