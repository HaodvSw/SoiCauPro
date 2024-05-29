package com.toolfindxs.xsmb_soicau.ui.store

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.core.os.postDelayed
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.billingclient.api.*
import com.android.billingclient.api.BillingClient.ProductType
import com.android.billingclient.api.BillingFlowParams.ProductDetailsParams
import com.android.billingclient.api.QueryProductDetailsParams.Product
import com.core.BaseFragment
import com.google.common.collect.ImmutableList
import com.google.firebase.firestore.auth.User
import com.toolfindxs.xsmb_soicau.MainApp
import com.toolfindxs.xsmb_soicau.data.local.prefs.PrefsHelper
import com.toolfindxs.xsmb_soicau.utils.Const
import com.toolfindxs.xsmb_soicau.utils.DataController
import com.toolfindxs.xsmb_soicau.databinding.FragmentPurchaseBinding
import com.toolfindxs.xsmb_soicau.utils.getCoinFromKey
import org.koin.android.ext.android.inject

class PurchaseFragment: BaseFragment<FragmentPurchaseBinding>() {

    private val adapter by lazy { PurchaseInAppAdapter() }
    private var billingClient: BillingClient? = null
    private val prefsHelper: PrefsHelper by inject()

    companion object{
        fun newInstance(): Fragment{
            return PurchaseFragment()
        }
    }

    override fun getLayoutBinding(): FragmentPurchaseBinding {
        return FragmentPurchaseBinding.inflate(layoutInflater)
    }

    override fun updateUI(savedInstanceState: Bundle?) {
        initViews()
//        binding.imgBack.clickWithDebounce { onBackPressed() }
    }

    private fun initViews() {
        binding.listData.layoutManager = LinearLayoutManager(context)
        binding.listData.adapter = adapter
        adapter.setOnItemClickListener { item, pos ->
            launchPurchaseFlow(item)
        }
        billingClient = context?.let {
            BillingClient.newBuilder(it)
                .enablePendingPurchases()
                .setListener { billingResult: BillingResult, list: List<Purchase?>? ->
                    if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && list != null) {
                        for (purchase in list) {
                            handlePurchase(purchase)
                        }
                    }
                }.build()
        }
        establishConnection()
    }

    private fun launchPurchaseFlow(productDetails: ProductDetails) {
        val productDetailsParamsList = ImmutableList.of(
            ProductDetailsParams.newBuilder()
                .setProductDetails(productDetails)
                .build()
        )
        val billingFlowParams = BillingFlowParams.newBuilder()
            .setProductDetailsParamsList(productDetailsParamsList)
            .build()
        activity?.let { billingClient?.launchBillingFlow(it, billingFlowParams) }
    }

    private fun handlePurchase(purchase: Purchase?) {
        val consumeParams = purchase?.purchaseToken?.let {
            ConsumeParams.newBuilder()
                .setPurchaseToken(it)
                .build()
        }
        val listener =
            ConsumeResponseListener { billingResult, purchaseToken ->
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    // Handle the success of the consume operation.
                    //                    verifyInAppPurchase(purchase);
                }
            }
        if (consumeParams != null) {
            billingClient?.consumeAsync(consumeParams, listener)
        }
        verifyInAppPurchase(purchase)
    }

    private fun establishConnection() {
        billingClient?.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    showProducts()
                }
            }
            override fun onBillingServiceDisconnected() {
                establishConnection()
            }
        })
    }

    private fun verifyInAppPurchase(purchases: Purchase?) {
        val acknowledgePurchaseParams = purchases?.purchaseToken?.let {
            AcknowledgePurchaseParams
                .newBuilder()
                .setPurchaseToken(it)
                .build()
        }
        if (acknowledgePurchaseParams != null) {
            billingClient?.acknowledgePurchase(acknowledgePurchaseParams) { billingResult: BillingResult ->
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    allowMultiplePurchases(purchases)
                    setupResult(purchases.products[0], purchases.quantity)
                }
            }
        }
    }

    private fun allowMultiplePurchases(purchase: Purchase) {
        // gọi thằng này để mua nhiều lần
        val consumeParams = ConsumeParams
            .newBuilder()
            .setPurchaseToken(purchase.purchaseToken)
            .build()
        billingClient!!.consumeAsync(consumeParams) { billingResult, s ->
            Toast.makeText(
                context,
                " Resume item ",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun setupResult(proId: String, quantity: Int) {
        val newGold = prefsHelper.coin + getCoinFromKey(proId) * quantity
        prefsHelper.coin = newGold
        val dataController = DataController(MainApp.getInstance().deviceId)
        dataController.setOnListenerFirebase(object : DataController.OnListenerFirebase {
            override fun onCompleteGetUser(user: com.toolfindxs.xsmb_soicau.utils.User?) {
            }

            override fun onSuccess() {
                Toast.makeText(requireContext(), "Xin chúc mừng, bạn đã mua gold thành công!", Toast.LENGTH_LONG).show()
            }

            override fun onFailure() {
                Toast.makeText(requireContext(), "Có lỗi kết nối đến server!", Toast.LENGTH_LONG).show()
            }
        })
        dataController.updateDocument(newGold)
    }

    private fun showProducts() {
        val params = QueryProductDetailsParams.newBuilder()
            .setProductList(getInAppProductList())
            .build()
        billingClient?.queryProductDetailsAsync(params) { billingResult: BillingResult?, prodDetailsList: List<ProductDetails> ->
            Handler(Looper.getMainLooper()).postDelayed(1000) {
                if (prodDetailsList.isEmpty()) binding.txtNoResult.visibility = View.VISIBLE
                adapter.items = prodDetailsList as MutableList<ProductDetails>
            }
        }
    }

    private fun getInAppProductList(): ImmutableList<Product> {
        return ImmutableList.of<Product>( //Product 1
            Product.newBuilder()
                .setProductId(Const.KEY_TYPE_ZERO)
                .setProductType(ProductType.INAPP)
                .build(),  //Product 2
            Product.newBuilder()
                .setProductId(Const.KEY_TYPE_ONE)
                .setProductType(ProductType.INAPP)
                .build(),  //Product 2
            Product.newBuilder()
                .setProductId(Const.KEY_TYPE_TWO)
                .setProductType(ProductType.INAPP)
                .build(),  //Product 3
            Product.newBuilder()
                .setProductId(Const.KEY_TYPE_THREE)
                .setProductType(ProductType.INAPP)
                .build(),  //Product 4
            Product.newBuilder()
                .setProductId(Const.KEY_TYPE_FOUR)
                .setProductType(ProductType.INAPP)
                .build(),  //Product 5
            Product.newBuilder()
                .setProductId(Const.KEY_TYPE_FIVE)
                .setProductType(ProductType.INAPP)
                .build(),  //Product 6
            Product.newBuilder()
                .setProductId(Const.KEY_TYPE_SIX)
                .setProductType(ProductType.INAPP)
                .build(),
            Product.newBuilder()
                .setProductId(Const.KEY_TYPE_SEVEN)
                .setProductType(ProductType.INAPP)
                .build()
        )
    }
}