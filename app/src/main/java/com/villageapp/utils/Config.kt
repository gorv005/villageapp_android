package com.villageapp.utils

class Config {

    object AdapterClickViewTypes {


        const val CLICK_VIEW_PRODUCT_SAVE = 99
        const val CLICK_VIEW_PRODUCT_DETAIL = 98
        const val CLICK_VIEW_EARN_FREE_POINT = 97
        const val CLICK_VIEW_WHOLE = 96
        const val CLICK_VIEW_RECIPE_OR_CATAGEORY = 95
        const val CLICK_VIEW_NOTIFICATION = 94


        const val CLICK_VIEW_DAILY_ALERT_MARK_FAV_OR_UNFAV = 93
        const val CLICK_VIEW_DAILY_ALERT_WHOLE = 92


    }

    object ConstantsAnswer {
        const val ONE = 1
        const val TWO = 2
        const val THREE = 3
        const val FOUR = 4
    }

    object ActionMarkReadConsumed {
        const val MARK_AS_READ = "mark_as_read"
        const val MEAL_CONSUMED = "meal_consumed"

    }

    object Constants {
        const val HEADER_AUTHORISATION = "Authorization"

        /* Distance is in meters */
        const val RADIUS_DEFAULT = 100 * 1000 // 100 km = 100 * 1000 meters

        const val RESPONSE_CODE_POLL_ALREADY_SKIPPED = 409
    }

    object Endpoints {
        /* BASE */
        const val BASE_PATH = "/api/v1/"

        /* AUTH RELATED */
        const val SIGN_UP_API = BASE_PATH + "users/"
        const val LOGIN_API = BASE_PATH + "oauth/token"

        const val HOME_FEEDS = BASE_PATH + "feeds"
        const val HOME_SEARCH = BASE_PATH + "searches"

        const val PRODUCT_LIST_FROM_CATEGORY = BASE_PATH + "categories/{categoryId}"
        const val PRODUCT_LIST_WITH_SEARCH = BASE_PATH + "products"
        const val PRODUCT_DETAIL = BASE_PATH + "products/{productId}"
        const val PRODUCT_MARK_FAVOURITE = BASE_PATH + "products/{productId}/favorites"
        const val PRODUCT_REMOVE_FAVOURITE = BASE_PATH + "products/{productId}/favorites/remove_favorite"

        const val USER_INFO = BASE_PATH + "users/{userId}"
        const val LOGOUT = BASE_PATH + "oauth/revoke"
        const val REMOVE_USER_IMAGE = BASE_PATH + "users/{userId}/remove_image"
        const val UPDATE_USER_PROFILE = BASE_PATH + "users/{userId}"
        const val DELETE_USER_PROFILE = BASE_PATH + "users/{userId}"
        const val LIST_FAVORITES = BASE_PATH + "favorites"

        const val FORGOT_PASSWORD = BASE_PATH + "users/forgot_password"
        const val RESET_PASSWORD = BASE_PATH + "users/reset_password"
        const val CHANGE_PASSWORD = BASE_PATH + "users/change_password"

        const val MARK_MEAL_CONSUMED = BASE_PATH + "restaurants/{restaurantId}/earned_points"
        const val MEAL_SEARCH_LIST = BASE_PATH + "meals"
        const val MEAL_CONSUMED_LIST = "$MEAL_SEARCH_LIST/consume"

        const val GET_DAILY_ALERTS = BASE_PATH + "daily_alerts/"
        const val GET_DAILY_ALERTS_DETAIL = "$GET_DAILY_ALERTS{alertId}"
        const val GET_DAILY_ALERTS_MARK_READ = "$GET_DAILY_ALERTS_DETAIL/earned_points"
        const val DAILY_ALERT_MARK_FAVOURITE = "$GET_DAILY_ALERTS_DETAIL/favorites"
        const val DAILY_ALERT_REMOVE_FAVOURITE = "$GET_DAILY_ALERTS_DETAIL/favorites/remove_favorite"


        const val NOTIFICATION_REGISTER_MOBILE_DEVICE = BASE_PATH + "mobile_devices"
        const val NOTIFICATION_UNREGISTER_MOBILE_DEVICE = "$NOTIFICATION_REGISTER_MOBILE_DEVICE/{deviceId}"
        const val NOTIFICATION_LISTING = BASE_PATH + "notifications"


    }

    object SharedPreferences {
        // shared preferences name
        const val PROPERTY_PREF = "PREFERENCE_DEFAULT"

        // keys
        // login related
        const val PROPERTY_LOGIN_PREF = "PROPERTY_LOGIN_PREF" // is user logged in
        const val PROPERTY_JWT_TOKEN = "PROPERTY_JWT_TOKEN" // auth token
        const val PROPERTY_USER_ID = "PROPERTY_USER_ID" // user id
        const val PROPERTY_USER_NAME = "PROPERTY_USER_NAME" // user name
        const val PROPERTY_USER_EMAIL = "PROPERTY_USER_EMAIL" // user email
        const val PROPERTY_USER_IMAGE = "PROPERTY_USER_IMAGE" // user image
        const val PROPERTY_USER_IMAGE_THUMB = "PROPERTY_USER_IMAGE_THUMB" // user image thumb
        const val PROPERTY_USER_EARNED_POINTS = "PROPERTY_USER_EARNED_POINTS" // user earned points
        const val PROPERTY_IS_ON_BOARDING_SEEN = "PROPERTY_IS_ON_BOARDING_SEEN" // user on boarding
        const val PROPERTY_USER_IS_FB_LOGIN = "PROPERTY_USER_IS_FB_LOGIN" // user on boarding

        // notification
        const val PROPERTY_FCM_REGISTRATION_TOKEN = "PROPERTY_FCM_REGISTRATION_TOKEN"
        const val PROPERTY_IS_FCM_SENT_TO_SERVER = "PROPERTY_IS_FCM_SENT_TO_SERVER"

    }


    object NotificationType {
        const val NEW_RECIPE = "new_recipe"
        const val PASSWORD_CHANGED = "password_changed"
        const val SIGN_UP = "sign_up"
        const val NEW_ALERT = "new_alert"
    }


    object NotifiableTypes {
        const val USER = "User"
        const val PRODUCT = "Product"
        const val DAILY_ALERT = "DailyAlert"
    }

}