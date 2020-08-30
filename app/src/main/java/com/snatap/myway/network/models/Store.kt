package com.snatap.myway.network.models

data class StoreResp(
    val store_items: List<Store>,
    val success: Boolean
)

data class OrderHistoryResp(
    val store_orders: List<OrderHistory>,
    val success: Boolean
)

data class OrderHistory(
    val card: String,
    val created_at: String,
    val id: Int,
    val is_expired: Int,
    val paid: Int,
    val payment_date: String,
    val payment_id: String,
    val status: Int,
    val store_order_items: List<Store>,
    val total_price: Int,
    val type: Int,
    val updated_at: String,
    val user_id: Int
)

data class Store(
    val available: Boolean,
    val category_id: Int,
    val coins: Int,
    val coins_cashback: Int,
    val created_at: String,
    val description: String,
    val hidden: Boolean,
    val id: Int,
    val photos: List<String>,
    val price: Int,
    val quantity: Int,
    val short_description: String,
    val title: String,
    val updated_at: String,
    var count: Int = 0
)

data class StoreCategories(
    val store_item_categories: List<StoreCategory>,
    val success: Boolean
)

data class StoreCategory(
    val id: Int,
    val title: String
)
