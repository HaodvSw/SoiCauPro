package com.toolfindxs.xsmb_soicau.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.core.BaseViewHolder
import com.core.OnItemClick
import com.core.adapterz.AdapterZ
import com.toolfindxs.xsmb_soicau.data.model.CodeLoto
import com.toolfindxs.xsmb_soicau.databinding.ItemTableLotoBinding


class TableLotoAdapter: AdapterZ<ItemTableLotoBinding, CodeLoto>() {

    override fun onCreateViewHolderZ(
        parent: ViewGroup,
        viewType: Int,
    ): BaseViewHolder<ViewBinding> {
        return BaseViewHolder(
            ItemTableLotoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolderZ(holder: BaseViewHolder<ItemTableLotoBinding>, pos: Int) {
        var lotoItem = items[pos]
        with(holder) {
            binding.endPosition.text = lotoItem.first
            binding.startPosition.text = lotoItem.last
        }
    }

    private var onItemClick: OnItemClick<CodeLoto>? = null

    fun setOnItemClickListener(callback: (item: CodeLoto, pos: Int) -> Unit) {
        onItemClick = OnItemClick(callback)
    }
}