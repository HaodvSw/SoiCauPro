package com.toolfindxs.xsmb_soicau.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.viewbinding.ViewBinding
import com.core.BaseViewHolder
import com.core.OnItemClick
import com.core.adapterz.AdapterZ
import com.toolfindxs.xsmb_soicau.R
import com.toolfindxs.xsmb_soicau.databinding.ItemResultBinding
import com.toolfindxs.xsmb_soicau.utils.Const
import com.toolfindxs.xsmb_soicau.utils.Constants


class MBAdapter: AdapterZ<ItemResultBinding, String>() {

    var mapColor = intArrayOf(0, 1, 26, 25, 24, 23)
    var mapBg = intArrayOf(1, 4, 5, 6, 7, 8, 9, 14, 15, 16, 17, 18, 19, 23, 24, 25, 26)
    private var typeNumber = Constants.NUMBER_ALL
    private var isTry = false

    override fun onCreateViewHolderZ(
        parent: ViewGroup,
        viewType: Int,
    ): BaseViewHolder<ViewBinding> {
        return BaseViewHolder(
            ItemResultBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    fun updateWithNumber(typeNumber: Int) {
        this.typeNumber = typeNumber
        notifyDataSetChanged()
    }

    override fun onBindViewHolderZ(holder: BaseViewHolder<ItemResultBinding>, pos: Int) {
        var result = items[pos]
        with(holder) {
            if (result == Const.STRING_DEFAULT) {
                binding.txtData.visibility = View.INVISIBLE
                binding.imgLoading.visibility = View.VISIBLE
                val animation = AnimationUtils.loadAnimation(itemView.context, R.anim.rotation_loop)
                binding.imgLoading.startAnimation(animation)
            } else {
                binding.txtData.visibility = View.VISIBLE
                binding.imgLoading.visibility = View.INVISIBLE
                if (typeNumber == Constants.NUMBER_TOW)
                    result = result.substring(Math.max(result.length - 2, 0))
                if (typeNumber == Constants.NUMBER_THREE) result =
                    result.substring(Math.max(result.length - 3, 0))
                binding.txtData.text = result
                if (!isTry) {
                    for (i in mapColor.indices) {
                        if (pos == mapColor.get(i)) binding.txtData.setTextColor(
                            itemView.context.resources.getColor(R.color.color_red_087)
                        )
                    }
                    for (i in mapBg.indices) {
                        if (pos == mapBg[i]) binding.viewRoot.background =
                            itemView.context.resources.getDrawable(R.drawable.bg_pos_hightlight)
                    }
                } else {
                    binding.txtData.setTextColor(itemView.context.resources.getColor(R.color.black))
                    binding.viewRoot.background = itemView.context.resources.getDrawable(R.drawable.bg_pos)
                }
            }
        }
    }

    private var onItemClick: OnItemClick<String>? = null

    fun setOnItemClickListener(callback: (item: String, pos: Int) -> Unit) {
        onItemClick = OnItemClick(callback)
    }
}