package com.toolfindxs.xsmb_soicau.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.toolfindxs.xsmb_soicau.ui.home.HomeFragment
import com.toolfindxs.xsmb_soicau.ui.idea.IdeaFragment
import com.toolfindxs.xsmb_soicau.ui.store.PurchaseFragment

class ViewPagerAdapter (fragmentActivity: FragmentActivity, private var totalCount: Int) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return totalCount
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment()
            1 -> IdeaFragment()
            else -> PurchaseFragment()
        }
    }

}