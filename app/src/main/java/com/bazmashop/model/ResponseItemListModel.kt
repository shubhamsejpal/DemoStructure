package com.bazmashop.model

import com.google.gson.annotations.SerializedName

data class ResponseItemListModel(
        @SerializedName("success")
        var success: Boolean,
        @SerializedName("status")
        var status: Int,
        @SerializedName("message")
        var message: String,
        @SerializedName("data")
        var `data`: Data
) {
    data class Data(
            @SerializedName("products")
            var products: ArrayList<Product>,
            @SerializedName("total_products")
            var totalProducts: String,
            @SerializedName("total_pages")
            var totalPages: Int,
            @SerializedName("max_product_price")
            var maxProductPrice: String,
            @SerializedName("filter")
            var filter: ArrayList<Filter>
    ) {
        data class Filter(
                @SerializedName("filter_name")
                var filterName: String,
                @SerializedName("filter_type")
                var filterType: String,
                @SerializedName("filter_values")
                var filterValues: ArrayList<FilterValue>
        ) {
            data class FilterValue(
                    @SerializedName("id")
                    var id: String,
                    @SerializedName("value")
                    var value: String
            )
        }

        data class Product(
                @SerializedName("id")
                var id: String,
                @SerializedName("name")
                var name: String,
                @SerializedName("short_description")
                var shortDescription: String,
                @SerializedName("description")
                var description: String,
                @SerializedName("SKU")
                var sKU: String,
                @SerializedName("regular_price")
                var regularPrice: String,
                @SerializedName("final_price")
                var finalPrice: String,
                @SerializedName("currency_code")
                var currencyCode: String,
                @SerializedName("remaining_quantity")
                var remainingQuantity: Int,
                @SerializedName("is_featured")
                var isFeatured: Int,
                @SerializedName("brand_name")
                var brandName: String,
                @SerializedName("image")
                var image: String,
                @SerializedName("product_type")
                var productType: String,
                @SerializedName("item_in_wishlist")
                var itemInWishlist: Int
        )
    }
}