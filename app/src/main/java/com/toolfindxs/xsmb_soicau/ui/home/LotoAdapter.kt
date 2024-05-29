package com.toolfindxs.xsmb_soicau.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.core.BaseViewHolder
import com.core.OnItemClick
import com.core.adapterz.AdapterZ
import com.toolfindxs.xsmb_soicau.databinding.ItemResultBinding


class LotoAdapter: AdapterZ<ItemResultBinding, String>() {

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

    override fun onBindViewHolderZ(holder: BaseViewHolder<ItemResultBinding>, pos: Int) {
        var result = items[pos]
        with(holder) {
            binding.txtData.text = result
        }
    }

    private var onItemClick: OnItemClick<String>? = null

    fun setOnItemClickListener(callback: (item: String, pos: Int) -> Unit) {
        onItemClick = OnItemClick(callback)
    }
}