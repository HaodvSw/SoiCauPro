package com.toolfindxs.xsmb_soicau.ui

import android.os.Bundle
import com.core.BaseActivity
import com.google.android.material.tabs.TabLayout
import com.toolfindxs.xsmb_soicau.MainApp
import com.toolfindxs.xsmb_soicau.databinding.ActivityMainBinding
import com.toolfindxs.xsmb_soicau.utils.Const

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getLayoutBinding(): ActivityMainBinding =
        ActivityMainBinding.inflate(layoutInflater)

    override fun updateUI(savedInstanceState: Bundle?) {
        initView()
    }

    private fun initView() {
        val adapter = ViewPagerAdapter(this, 3)
        binding.viewPager.adapter = adapter
        binding.viewPager.isUserInputEnabled = false

        binding.tabLayout.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    binding.viewPager.currentItem = it.position
                    if (it.position == 0){
                        sendBuser(MainApp.getInstance().eventBus, Const.EVENT_UPDATE, "")
                    }
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    fun scrollPositionPage(pos: Int) {
        binding.tabLayout.getTabAt(pos)?.select()
    }

}