package com.bazmashop

class Constants {

    object Prefs {
        var AUTH_TOKEN = "auth"
    }

    object App {

    }

    object Api {

        var BASE_URL = "http://dev.bazmashop.com/api/v2/"

        object ResponseCode {
            val UNAUTHORIZED_CODE = 401
        }

        object RequestCode {
            val GET_ITEMS = 1
        }

        object EndUrl {
            const val GET_ITEMS = "search?q=&lang=en&category_id=&brand_id=&price_range=&in_stock=&page=1&per_page=20&is_featured=1&latest=&best_selling=&sort_by=1&store=KW"
        }
    }
}
