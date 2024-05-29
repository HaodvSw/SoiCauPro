package com.toolfindxs.xsmb_soicau.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CodeOther {
    @SerializedName("code")
    @Expose
     var code: String? = null
        get() = field?.replace(",", "")

    @SerializedName("code1")
    @Expose
     var code1: String? = null
        get() = field?.replace(",", "")

    @SerializedName("code2")
    @Expose
     var code2: String? = null
        get() = field?.replace(",", "")

    @SerializedName("code3")
    @Expose
    var code3: List<String>? = null
        get() = formatDataKQ(field)

    @SerializedName("code4")
    @Expose
    var code4: List<String>? = null
        get() = formatDataKQ(field)

    @SerializedName("code5")
    @Expose
     var code5: String? = null
        get() = field?.replace(",", "")

    @SerializedName("code6")
    @Expose
    var code6: List<String>? = null
        get() = formatDataKQ(field)

    @SerializedName("code7")
    @Expose
     var code7: String? = null
        get() = field?.replace(",", "")

    @SerializedName("code8")
    @Expose
     var code8: String? = null
        get() = field?.replace(",", "")

    private fun formatDataKQ(data: List<String>?): List<String> {
        val listData: MutableList<String> = ArrayList()
        for (i in data!!.indices) {
            listData.add(data[i].replace(",", ""))
        }
        return listData
    }
}