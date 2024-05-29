package com.toolfindxs.xsmb_soicau.ui.home.orther

import android.os.Bundle
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.core.BaseFragment
import com.toolfindxs.xsmb_soicau.data.model.CodeOther
import com.toolfindxs.xsmb_soicau.ui.home.HomeViewModel
import com.toolfindxs.xsmb_soicau.ui.home.LotoAdapter
import com.toolfindxs.xsmb_soicau.ui.home.TableLotoAdapter
import com.toolfindxs.xsmb_soicau.R
import com.toolfindxs.xsmb_soicau.data.model.CodeLoto
import com.toolfindxs.xsmb_soicau.databinding.FragmentOtherPlaceBinding
import com.toolfindxs.xsmb_soicau.utils.Constants
import com.toolfindxs.xsmb_soicau.utils.GenRequestParam
import com.toolfindxs.xsmb_soicau.utils.getDataListLoto
import org.koin.android.viewmodel.ext.android.viewModel

class PlaceOtherFragment: BaseFragment<FragmentOtherPlaceBinding>() {

    private val vm by viewModel<HomeViewModel>()
    private val otherAdapter by lazy { OtherAdapter() }
    private val adapterLoto by lazy { LotoAdapter() }
    private val adapterTableLoto by lazy { TableLotoAdapter() }
    private var provinces = mutableListOf<String>()

    companion object{
        const val SENT_TYPE = "SENT_TYPE"
        fun newInstance(placeType: String): Fragment {
            val fragment = PlaceOtherFragment()
            val bundle = Bundle()
            bundle.putSerializable(SENT_TYPE, placeType)
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var placeType: String

    override fun getLayoutBinding(): FragmentOtherPlaceBinding = FragmentOtherPlaceBinding.inflate(layoutInflater)

    override fun updateUI(savedInstanceState: Bundle?) {
        placeType = arguments?.getString(SENT_TYPE) ?: ""
        initView()
        listener()
        provinces = GenRequestParam.getLocal(requireContext(), placeType).split(",") as MutableList<String>
        vm.getResultOther(provinces)
    }

    private fun initView() {
        binding.layoutChoiceNumber.rdoGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { radioGroup, id ->
            when (id) {
                R.id.rdoAll -> otherAdapter.updateWithNumber(Constants.NUMBER_ALL, null)
                R.id.rdoTowNumber -> otherAdapter.updateWithNumber(Constants.NUMBER_TOW, null)
                R.id.rdoThreeNumber -> otherAdapter.updateWithNumber(Constants.NUMBER_THREE, null)
            }
        })
    }

    private fun listener() {
        vm.rxResultOther.subscribe {
            setList(it.first)
            val dataLoto =  getDataListLoto(it.first)
            setListLoto(dataLoto.first)
            setListTableLoto(dataLoto.second)
        }.addToBag()
    }

    private fun setList(listData: MutableList<CodeOther>){
        binding.listResult.layoutManager = GridLayoutManager(requireContext(), listData.size)
        binding.listResult.adapter = otherAdapter
        otherAdapter.updateWithNumber(Constants.NUMBER_ALL, provinces)
        otherAdapter.items = listData
    }
    private fun setListLoto(listData: MutableList<String>){
        binding.listLoto.layoutManager = GridLayoutManager(requireContext(), 9)
        binding.listLoto.adapter = adapterLoto
        adapterLoto.items = listData
    }

    private fun setListTableLoto(listData: MutableList<CodeLoto>){
        binding.listTableLoto.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.listTableLoto.adapter = adapterTableLoto
        adapterTableLoto.items = listData
    }
}