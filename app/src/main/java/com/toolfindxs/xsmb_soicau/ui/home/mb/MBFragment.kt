package com.toolfindxs.xsmb_soicau.ui.home.mb

import android.os.Bundle
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.core.BaseFragment
import com.toolfindxs.xsmb_soicau.data.model.Code
import com.toolfindxs.xsmb_soicau.ui.home.LotoAdapter
import com.toolfindxs.xsmb_soicau.ui.home.MBAdapter
import com.toolfindxs.xsmb_soicau.ui.home.TableLotoAdapter
import com.toolfindxs.xsmb_soicau.R
import com.toolfindxs.xsmb_soicau.data.model.CodeLoto
import com.toolfindxs.xsmb_soicau.databinding.FragmentMbBinding
import com.toolfindxs.xsmb_soicau.utils.Constants
import com.toolfindxs.xsmb_soicau.utils.getDataListLoto
import com.toolfindxs.xsmb_soicau.utils.getListResult

class MBFragment: BaseFragment<FragmentMbBinding>() {


    companion object{
        const val SENT_CODE = "SENT_CODE"
        fun newInstance(code: Code): Fragment {
            val fragment = MBFragment()
            val bundle = Bundle()
            bundle.putSerializable(SENT_CODE, code)
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var codeResult: Code
    private val adapterMB by lazy { MBAdapter() }
    private val adapterLoto by lazy { LotoAdapter() }
    private val adapterTableLoto by lazy { TableLotoAdapter() }

    override fun getLayoutBinding(): FragmentMbBinding = FragmentMbBinding.inflate(layoutInflater)

    override fun updateUI(savedInstanceState: Bundle?) {
        codeResult = arguments?.getSerializable(SENT_CODE) as Code
        initView()
        listener()
    }

    private fun initView() {
        binding.layoutResult.listResult.layoutManager = getLayoutManager()
        binding.layoutResult.listResult.adapter = adapterMB
        adapterMB.setOnItemClickListener { item, _ ->

        }
        adapterMB.items = getListResult(codeResult)
        val dataLoto =  getDataListLoto(codeResult)
        setListLoto(dataLoto.first)
        setListTableLoto(dataLoto.second)
    }

    private fun listener() {
        binding.layoutResult.layoutChoiceNumber.rdoGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { radioGroup, id ->
            when (id) {
                R.id.rdoAll -> adapterMB.updateWithNumber(Constants.NUMBER_ALL)
                R.id.rdoTowNumber -> adapterMB.updateWithNumber(Constants.NUMBER_TOW)
                R.id.rdoThreeNumber -> adapterMB.updateWithNumber(Constants.NUMBER_THREE)
            }
        })
    }

    private fun setListLoto(listData: MutableList<String>){
        binding.layoutResult.listLoto.layoutManager = GridLayoutManager(requireContext(), 9)
        binding.layoutResult.listLoto.adapter = adapterLoto
        adapterLoto.items = listData
    }

    private fun setListTableLoto(listData: MutableList<CodeLoto>){
        binding.layoutResult.listTableLoto.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.layoutResult.listTableLoto.adapter = adapterTableLoto
        adapterTableLoto.items = listData
    }

    private fun getLayoutManager(): GridLayoutManager{
        val layoutManager = GridLayoutManager(requireContext(), 12)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            var map = intArrayOf(12, 12, 6, 6, 4, 4, 4, 4, 4, 4, 3, 3, 3, 3, 4, 4, 4, 4, 4, 4, 4, 4, 4, 3, 3, 3, 3)
            override fun getSpanSize(position: Int): Int {
                return map[position % map.size]
            }
        }
        return layoutManager
    }
}