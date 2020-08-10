package com.snatap.myway.network.models

data class StoreResp(
    val store_items: List<Store>,
    val success: Boolean
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
    val updated_at: String
)

data class StoreCategories(
    val store_item_categories: List<StoreCategory>,
    val success: Boolean
)

data class StoreCategory(
    val id: Int,
    val title: String
)
