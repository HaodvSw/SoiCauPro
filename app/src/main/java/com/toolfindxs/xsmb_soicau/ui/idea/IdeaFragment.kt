package com.toolfindxs.xsmb_soicau.ui.idea

import android.os.Bundle
import android.widget.Toast
import com.core.BaseFragment
import com.toolfindxs.xsmb_soicau.data.local.prefs.PrefsHelper
import com.toolfindxs.xsmb_soicau.ui.MainActivity
import com.toolfindxs.xsmb_soicau.databinding.FragmentIdeaBinding
import com.toolfindxs.xsmb_soicau.ui.dialog.ResultDialog
import com.toolfindxs.xsmb_soicau.utils.Const
import com.utils.ext.clickWithDebounce
import org.koin.android.ext.android.inject

class IdeaFragment: BaseFragment<FragmentIdeaBinding>() {

    private val prefs: PrefsHelper by inject()
    override fun getLayoutBinding(): FragmentIdeaBinding = FragmentIdeaBinding.inflate(layoutInflater)

    override fun updateUI(savedInstanceState: Bundle?) {
        initView()
    }

    private fun initView() {
        binding.tvGenTypeOne.clickWithDebounce {openDialogResult(Const.TYPE_SAMPLE)}

        binding.tvGenTypeVip.clickWithDebounce {
            if (prefs.coin >= Const.GOLD_BUY)openDialogResult(Const.TYPE_VIP)
            else{
                Toast.makeText(requireContext(), "Vui lòng nạp thêm gold để chúng tôi phục vụ bạn tốt hơn!", Toast.LENGTH_SHORT).show()
                (requireActivity() as MainActivity).scrollPositionPage(2)
            }
        }
    }

    private fun openDialogResult(type: Int) {
        val dialogResult = ResultDialog(type)
        dialogResult.isCancelable = false
        dialogResult.show(childFragmentManager, ResultDialog::class.java.name)
    }
}