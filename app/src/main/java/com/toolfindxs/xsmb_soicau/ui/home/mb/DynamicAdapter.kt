package com.toolfindxs.xsmb_soicau.ui.home.mb

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.core.BaseViewHolder
import com.core.OnItemClick
import com.core.adapterz.AdapterZ
import com.toolfindxs.xsmb_soicau.databinding.ItemResultBinding
import com.toolfindxs.xsmb_soicau.utils.Constants


class DynamicAdapter: AdapterZ<ItemResultBinding, String>() {

    private var typeNumber = Constants.NUMBER_ALL

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
            if (typeNumber == Constants.NUMBER_TOW) result =
                result.substring((result.length - 2).coerceAtLeast(0))
            if (typeNumber == Constants.NUMBER_THREE) result =
                result.substring((result.length - 3).coerceAtLeast(0))
            binding.txtData.text = result
        }
    }

    private var onItemClick: OnItemClick<String>? = null

    fun setOnItemClickListener(callback: (item: String, pos: Int) -> Unit) {
        onItemClick = OnItemClick(callback)
    }
}