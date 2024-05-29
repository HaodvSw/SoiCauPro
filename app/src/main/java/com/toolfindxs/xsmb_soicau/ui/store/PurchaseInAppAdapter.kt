package com.toolfindxs.xsmb_soicau.ui.store

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.android.billingclient.api.ProductDetails
import com.core.BaseViewHolder
import com.core.OnItemClick
import com.core.adapterz.AdapterZ
import com.toolfindxs.xsmb_soicau.databinding.ItemPurchaseBinding
import com.toolfindxs.xsmb_soicau.utils.setTitleValue
import com.utils.ext.clickWithDebounce

class PurchaseInAppAdapter: AdapterZ<ItemPurchaseBinding, ProductDetails>() {

    override fun onCreateViewHolderZ(
        parent: ViewGroup,
        viewType: Int,
    ): BaseViewHolder<ViewBinding> {
        return BaseViewHolder(
            ItemPurchaseBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    override fun onBindViewHolderZ(holder: BaseViewHolder<ItemPurchaseBinding>, position: Int) {
        val item = items[position] as ProductDetails
        with(holder) {
            binding.txtSubName.text = setTitleValue(itemView.context.resources, item.productId, item.oneTimePurchaseOfferDetails?.formattedPrice)
        itemView.clickWithDebounce {
            onItemClick?.onItemClickListener(item, position)
        }
        }
    }

    private var onItemClick: OnItemClick<ProductDetails>? = null

    fun setOnItemClickListener(callback: (item: ProductDetails, pos: Int) -> Unit) {
        onItemClick = OnItemClick(callback)
    }
}