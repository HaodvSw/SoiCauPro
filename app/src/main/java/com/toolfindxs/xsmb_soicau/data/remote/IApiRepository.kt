package com.toolfindxs.xsmb_soicau.data.remote

import com.toolfindxs.xsmb_soicau.data.model.Code
import com.toolfindxs.xsmb_soicau.data.model.CodeOther
import com.toolfindxs.xsmb_soicau.data.model.Response
import com.toolfindxs.xsmb_soicau.data.remote.ApiEndPoint
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path


interface IApiRepository {

    @GET(ApiEndPoint.URL_API)
    fun getDataTypeOne(@Path("key")key : String, @Path("date")date : String, @Path("local")name : String): Single<MutableList<Response<Code>>>

    @GET(ApiEndPoint.URL_API)
    fun getDataTypeTow(@Path("key")key : String, @Path("date")date : String, @Path("local")name : String): Single<MutableList<Response<CodeOther>>>
}