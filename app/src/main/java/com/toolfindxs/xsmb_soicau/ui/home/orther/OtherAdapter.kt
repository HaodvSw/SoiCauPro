package com.toolfindxs.xsmb_soicau.ui.home.orther

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.core.BaseViewHolder
import com.core.OnItemClick
import com.core.adapterz.AdapterZ
import com.toolfindxs.xsmb_soicau.data.model.CodeOther
import com.toolfindxs.xsmb_soicau.databinding.ItemResultDynamicBinding
import com.toolfindxs.xsmb_soicau.ui.home.mb.DynamicAdapter
import com.toolfindxs.xsmb_soicau.utils.Constants
import com.toolfindxs.xsmb_soicau.utils.GenRequestParam
import com.toolfindxs.xsmb_soicau.utils.genListType2


class OtherAdapter: AdapterZ<ItemResultDynamicBinding, CodeOther>() {

    private val dynamicAdapter by lazy { DynamicAdapter() }
    private var typeNumber = Constants.NUMBER_ALL
    private var nameProvince = mutableListOf<String>()
    private var isTry = false

    override fun onCreateViewHolderZ(
        parent: ViewGroup,
        viewType: Int,
    ): BaseViewHolder<ViewBinding> {
        return BaseViewHolder(
            ItemResultDynamicBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    fun updateWithNumber(typeNumber: Int, nameProvince: MutableList<String>?) {
        this.typeNumber = typeNumber
        nameProvince?.let { this.nameProvince = it }
        notifyDataSetChanged()
    }

    override fun onBindViewHolderZ(holder: BaseViewHolder<ItemResultDynamicBinding>, pos: Int) {
        var result = items[pos]
        with(holder) {
            binding.txtName.text = GenRequestParam.convertNameProvince(nameProvince[pos])
            binding.listDynamic.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.VERTICAL, false)
            binding.listDynamic.adapter = dynamicAdapter
            dynamicAdapter.updateWithNumber(typeNumber)
            dynamicAdapter.items = genListType2(result)
        }
    }

    private var onItemClick: OnItemClick<CodeOther>? = null

    fun setOnItemClickListener(callback: (item: CodeOther, pos: Int) -> Unit) {
        onItemClick = OnItemClick(callback)
    }
}