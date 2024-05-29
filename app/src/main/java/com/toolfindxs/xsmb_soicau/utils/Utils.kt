package com.toolfindxs.xsmb_soicau.utils

import android.content.res.Resources
import android.os.Build
import android.util.Log
import android.widget.TextView
import androidx.annotation.StringRes
import com.toolfindxs.xsmb_soicau.MainApp
import com.toolfindxs.xsmb_soicau.data.model.Code
import com.toolfindxs.xsmb_soicau.data.model.CodeOther
import com.toolfindxs.xsmb_soicau.R
import com.toolfindxs.xsmb_soicau.data.model.CodeLoto
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

//fun openScreenWithContainer(
//    context: Context,
//    fragmentClazz: Class<*>,
//    bundle: Bundle?,
//) {
//    getInstance(fragmentClazz, bundle)?.let {
//        it.open(true)
//    } ?: run {
//        context.startActivity(Intent(context, ContainActivity::class.java))
//    }
//}

fun loadStringRes(@StringRes resId: Int): String =
    MainApp.getInstance().getString(resId)

fun formatDataKQ(data: List<String>?): List<String> {
    data?.let {
        val listData: MutableList<String> = ArrayList()
        for (i in it.indices) {
            listData.add(data[i].replace(",", ""))
        }
        return listData
    }?: kotlin.run {
        return mutableListOf()
    }
}

fun Date.asDateFormat(): String {
    val df = SimpleDateFormat("yyyyMMdd")
    return df.format(this)
}

fun genValueResult(result: String):String{
    val dateFormat = Date().asDateFormat()
    return "$dateFormat/$result"
}

fun TextView.setDateFormatDay(){
    val dateFormat = SimpleDateFormat("EEEE, dd/MM/yyyy", Locale("vi", "VN"))
    val formatted = dateFormat.format(MainApp.getInstance().dateSelect.toDate())
    text = formatted
}

fun getListResult(kq: Code): MutableList<String> {
    val listData = mutableListOf<String>()
    listData.add(kq.code ?: "")
    listData.add(kq.code1?: "")
    listData.addAll(kq.code2 ?: mutableListOf())
    listData.addAll(kq.code3?: mutableListOf())
    listData.addAll(kq.code4?: mutableListOf())
    listData.addAll(kq.code5?: mutableListOf())
    listData.addAll(kq.code6?: mutableListOf())
    listData.addAll(kq.code7?: mutableListOf())
    return listData
}

fun genListType2(value: CodeOther): MutableList<String> {
    val listResult = mutableListOf<String>()
    listResult.add(value.code8?: "")
    listResult.add(value.code7?: "")
    listResult.addAll(value.code6?: mutableListOf())
    listResult.add(value.code5?: "")
    listResult.addAll(value.code4?: mutableListOf())
    listResult.addAll(value.code3?: mutableListOf())
    listResult.add(value.code2?: "")
    listResult.add(value.code1?: "")
    listResult.add(value.code?: "")
    return listResult
}

fun getDataListLoto(kq: Code): Pair<MutableList<String>, MutableList<CodeLoto>> {
    var mListLoto = mutableListOf<CodeLoto>()
    val mapFirstDigit = HashMap<String, MutableList<String>>()
    val mapLastDigit = HashMap<String, MutableList<String>>()
    val valueLoto: List<String> = getListResult(kq)
    val newValueLoto: MutableList<String> = java.util.ArrayList()
    for (i in valueLoto.indices) {
        newValueLoto.add(valueLoto[i].substring(Math.max(valueLoto[i].length - 2, 0)))
    }
    newValueLoto.sort()
    for (loto in newValueLoto) {
        val first = loto.substring(0, 1)
        val last = loto.substring(1)
        val firstList: MutableList<String> =
            mapFirstDigit.getOrDefault(first, java.util.ArrayList<String>())
        firstList.add(last)
        mapFirstDigit.put(first, firstList)
        val lastList: MutableList<String> =
            mapLastDigit.getOrDefault(last, java.util.ArrayList<String>())
        lastList.add(first)
        mapLastDigit.put(last, lastList)
    }
    for (i in 0..9) {
        val lastList = mapFirstDigit[i.toString()]
        val sb = StringBuilder()
        if (lastList == null) {
            sb.append("-")
        } else {
            for (last in lastList) {
                sb.append(last)
                sb.append(",")
            }
            sb.deleteCharAt(sb.length - 1)
        }
        val startList = mapLastDigit[i.toString()]
        val sbStart = StringBuilder()
        if (startList == null) {
            sbStart.append("-")
        } else {
            for (first in startList) {
                sbStart.append(first)
                sbStart.append(",")
            }
            sbStart.deleteCharAt(sbStart.length - 1)
        }
        val itemLoto = CodeLoto(sb.toString(), sbStart.toString())
        sb.delete(0, sb.length - 1)
        sbStart.delete(0, sbStart.length - 1)
        mListLoto.add(itemLoto)
    }
    return Pair(newValueLoto, mListLoto)
}

fun getDataListLoto(kq: MutableList<CodeOther>): Pair<MutableList<String>, MutableList<CodeLoto>> {
    var mListLoto = mutableListOf<CodeLoto>()
    val mapFirstDigit = HashMap<String, MutableList<String>>()
    val mapLastDigit = HashMap<String, MutableList<String>>()
    val newValueLoto: MutableList<String> = ArrayList()
    val valueLoto: MutableList<String> = ArrayList()
    for (itemKq in kq) {
        valueLoto.addAll(genListType2(itemKq))
    }
    for (i in valueLoto.indices) {
        newValueLoto.add(valueLoto[i].substring(Math.max(valueLoto[i].length - 2, 0)))
    }
    newValueLoto.sort()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        for (loto in newValueLoto) {
            val first = loto.substring(0, 1)
            val last = loto.substring(1)
            val firstList: MutableList<String> = mapFirstDigit.getOrDefault(first, ArrayList<String>())
            firstList.add(last)
            mapFirstDigit.put(first, firstList)
            val lastList : MutableList<String> = mapLastDigit.getOrDefault(last, ArrayList<String>())
            lastList.add(first)
            mapLastDigit.put(last, lastList)
        }
        for (i in 0..9) {
            val lastList = mapFirstDigit.get(i.toString())
            val sb = java.lang.StringBuilder()
            if (lastList == null) {
                sb.append("-")
            } else {
                for (last in lastList) {
                    sb.append(last)
                    sb.append(",")
                }
                sb.deleteCharAt(sb.length - 1)
            }
            val startList: List<String> =
                mapLastDigit.getOrDefault(i.toString(), ArrayList<String>())
            val sbStart = java.lang.StringBuilder()
            for (start in startList) {
                sbStart.append(start)
                sbStart.append(",")
            }
            sbStart.deleteCharAt(sbStart.length - 1)
            val itemLoto = CodeLoto(sb.toString(), sbStart.toString())
            mListLoto.add(itemLoto)
            sb.setLength(0)
            sbStart.setLength(0)
        }
    }
    return Pair(newValueLoto, mListLoto)
}

fun checkTimings(): Boolean {
    val currentTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
    Log.e("checkTimings", currentTime)
    val pattern = "HH:mm"
    val sdf = SimpleDateFormat(pattern)
    try {
        val dateStart = sdf.parse("00:01")
        val dateEnd = sdf.parse("18:35")
        val dateCurrent = sdf.parse(currentTime)
        return (dateCurrent.after(dateStart) && dateCurrent.before(dateEnd))
    } catch (e: ParseException) {
        Log.e("checkTimings", e.message.toString())
        e.printStackTrace()
    }
    Log.e("checkTimings", "false")
    return false
}

fun getCoinFromKey(coin: String?): Int {
    return when (coin) {
        Const.KEY_TYPE_ZERO -> 1
        Const.KEY_TYPE_ONE -> 5
        Const.KEY_TYPE_TWO -> 10
        Const.KEY_TYPE_THREE -> 20
        Const.KEY_TYPE_FOUR -> 30
        Const.KEY_TYPE_FIVE -> 100
        Const.KEY_TYPE_SIX -> 150
        Const.KEY_TYPE_SEVEN -> 300
        else -> 0
    }
}

fun setTitleValue(resources: Resources, productId: String?, price: String?): String {
    return when (productId) {
        Const.KEY_TYPE_ZERO -> String.format(
            resources.getString(R.string.message_purchase),
            "$price/1 vàng"
        )
        Const.KEY_TYPE_ONE -> String.format(
            resources.getString(R.string.message_purchase),
            "$price/5 vàng"
        )
        Const.KEY_TYPE_TWO -> String.format(
            resources.getString(R.string.message_purchase),
            "$price/10 vàng"
        )
        Const.KEY_TYPE_THREE -> String.format(
            resources.getString(R.string.message_purchase),
            "$price/20 vàng"
        )
        Const.KEY_TYPE_FOUR -> String.format(
            resources.getString(R.string.message_purchase),
            "$price/30 vàng"
        )
        Const.KEY_TYPE_FIVE -> String.format(
            resources.getString(R.string.message_purchase),
            "$price/100 vàng"
        )
        Const.KEY_TYPE_SIX -> String.format(
            resources.getString(R.string.message_purchase),
            "$price/150 vàng"
        )
        Const.KEY_TYPE_SEVEN -> String.format(
            resources.getString(R.string.message_purchase),
            "$price/300 vàng"
        )
        else -> String.format(resources.getString(R.string.message_purchase), "/0 vàng")
    }
}