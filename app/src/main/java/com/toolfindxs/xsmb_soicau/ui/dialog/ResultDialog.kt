package com.toolfindxs.xsmb_soicau.ui.dialog

import android.os.Bundle
import android.os.CountDownTimer
import com.core.BaseDialog
import com.toolfindxs.xsmb_soicau.R
import com.toolfindxs.xsmb_soicau.data.local.prefs.PrefsHelper
import com.toolfindxs.xsmb_soicau.utils.Const
import com.toolfindxs.xsmb_soicau.databinding.DialogResultBinding
import com.toolfindxs.xsmb_soicau.utils.asDateFormat
import com.toolfindxs.xsmb_soicau.utils.genValueResult
import com.utils.ext.clickWithDebounce
import org.koin.android.ext.android.inject
import java.util.*


class ResultDialog(private var type: Int): BaseDialog<DialogResultBinding>() {

    private val prefs: PrefsHelper by inject()

    override fun getLayoutBinding(): DialogResultBinding = DialogResultBinding.inflate(layoutInflater)

    override fun updateUI(savedInstanceState: Bundle?) {
        initView()
        binding.txtOk.clickWithDebounce { dismiss()}
    }

    private fun initView() {
        if (type == Const.TYPE_VIP)binding.txtOk.background = requireActivity().resources.getDrawable(R.drawable.bg_gen_type2)
        val result = if (type == Const.TYPE_SAMPLE) prefs.resultSample else prefs.resultVip
        if (result.contains(Date().asDateFormat())){
            val results = result.split("/")
            binding.txtResult.text = results[1]
        }else startGenResult()
    }

    private fun startGenResult(){
        val cTimer = object : CountDownTimer(10000, 100) {
            override fun onTick(millisUntilFinished: Long) {
                val random = Random().nextInt(99 ).toString()
                binding.txtResult.text = if (random.length == 1) "0$random" else random
            }
            override fun onFinish() {
                val result = genValueResult(binding.txtResult.text.toString())
                if (type == Const.TYPE_SAMPLE) {
                    prefs.resultSample = result
                }else{
                    prefs.coin -= Const.GOLD_BUY
                    prefs.resultVip = result
                }
            }
        }
        cTimer.start()
    }
}