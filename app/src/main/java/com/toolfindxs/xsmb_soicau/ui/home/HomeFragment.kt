package com.toolfindxs.xsmb_soicau.ui.home

import android.os.Bundle
import android.widget.Toast
import com.core.BaseFragment
import com.core.rx.RxBus
import com.github.jhonnyx2012.horizontalpicker.DatePickerListener
import com.google.android.material.tabs.TabLayout
import com.toolfindxs.xsmb_soicau.MainApp
import com.toolfindxs.xsmb_soicau.data.local.prefs.PrefsHelper
import com.toolfindxs.xsmb_soicau.data.model.Code
import com.toolfindxs.xsmb_soicau.ui.MainActivity
import com.toolfindxs.xsmb_soicau.R
import com.toolfindxs.xsmb_soicau.databinding.FragmentHomeBinding
import com.toolfindxs.xsmb_soicau.utils.Constants
import com.toolfindxs.xsmb_soicau.utils.GenRequestParam
import com.toolfindxs.xsmb_soicau.utils.loadStringRes
import com.toolfindxs.xsmb_soicau.utils.setDateFormatDay
import com.utils.ext.clickWithDebounce
import org.joda.time.DateTime
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment: BaseFragment<FragmentHomeBinding>(), DatePickerListener {

    private val vm by viewModel<HomeViewModel>()
    private val prefs: PrefsHelper by inject()

    override fun getLayoutBinding(): FragmentHomeBinding = FragmentHomeBinding.inflate(layoutInflater)

    override fun updateUI(savedInstanceState: Bundle?) {
        binding.txtGold.text = String.format(loadStringRes(R.string.amount_gold), prefs.coin)
        initView()
        listener()
    }

    override fun onStart() {
        super.onStart()
        registerBuser(MainApp.getInstance().eventBus) {
            binding.txtGold.text = String.format(loadStringRes(R.string.amount_gold), prefs.coin)
        }
    }

    private fun setPagerPlace(code: Code) {
        val adapter = ViewPagerPlaceAdapter(requireActivity(), 3, code)
        binding.viewPager.adapter = adapter
        binding.viewPager.isUserInputEnabled = false

        binding.layoutTabPlace.tabLayout.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    binding.viewPager.currentItem = it.position
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun initView() {
        binding.datePicker.setListener(this)
            .init()
        binding.datePicker.setDate(DateTime())
        binding.datePicker.setBackgroundDrawable(resources.getDrawable(R.drawable.bg_date))
        binding.imgPrevious.clickWithDebounce {
            val dateSelect = MainApp.getInstance().dateSelect
            MainApp.getInstance().dateSelect = dateSelect.minusDays(1)
            calApi()
        }
        binding.imgNext.clickWithDebounce {
            val dateSelect = MainApp.getInstance().dateSelect
            MainApp.getInstance().dateSelect = dateSelect.plusDays(1)
            calApi()
        }
        binding.txtGold.clickWithDebounce {
            (requireContext() as MainActivity).scrollPositionPage(2)
        }
    }

    private fun listener() {
        vm.rxResult.subscribe {
            setPagerPlace(it)
        }.addToBag()
        vm.isLoading.subscribe {
            if (it)showDialog()
            else hideDialog()
        }.addToBag()
    }

    override fun onDateSelected(dateSelected: DateTime) {
        if (dateSelected.isAfterNow){
            Toast.makeText(requireContext(), "Không thể chọn trước ngày quay số!", Toast.LENGTH_SHORT).show()
            binding.datePicker.setDate(DateTime())
        }else if (DateTime().dayOfWeek == dateSelected.dayOfWeek){
            MainApp.getInstance().dateSelect = DateTime().minusDays(1)
            calApi()
        }else {
            MainApp.getInstance().dateSelect = dateSelected
            calApi()
        }
    }

    override fun onDateHighlighted(dateHighlighted: DateTime) {

    }

    private fun calApi(){
        vm.getResult(GenRequestParam.getLocal(requireContext(), Constants.MB_NAME))
        binding.txtDate.setDateFormatDay()
    }

    override fun onStop() {
        super.onStop()
        unRegisterBuser(RxBus())
    }

}