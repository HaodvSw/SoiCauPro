package com.toolfindxs.xsmb_soicau.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Response<T> (
    @SerializedName("issue")
    @Expose
     val issue: String? = null,

    @SerializedName("opendate")
    @Expose
     val opendate: String? = null,

    @SerializedName("code")
    @Expose
     val code: T? = null,

    @SerializedName("lotterycode")
    @Expose
     val lotterycode: String? = null,

    @SerializedName("officialissue")
    @Expose
     val officialissue: String? = null
)