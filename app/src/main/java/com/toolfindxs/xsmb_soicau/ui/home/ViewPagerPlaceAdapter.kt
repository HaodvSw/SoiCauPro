package com.toolfindxs.xsmb_soicau.ui.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.toolfindxs.xsmb_soicau.data.model.Code
import com.toolfindxs.xsmb_soicau.ui.home.mb.MBFragment
import com.toolfindxs.xsmb_soicau.ui.home.orther.PlaceOtherFragment
import com.toolfindxs.xsmb_soicau.utils.Constants

class ViewPagerPlaceAdapter (fragmentActivity: FragmentActivity, private var totalCount: Int, private var code: Code) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return totalCount
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> MBFragment.newInstance(code)
            1 -> PlaceOtherFragment.newInstance(Constants.MT_NAME)
            else -> PlaceOtherFragment.newInstance(Constants.MN_NAME)
        }
    }

}