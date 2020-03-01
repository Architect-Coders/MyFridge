package com.pabji.myfridge.model.network.api

const val PRODUCTS = "products"
const val PRODUCT = "product"
const val PRODUCT_NAME = "product_name"
const val IMAGE_SMALL_URL = "image_small_url"
const val IMAGE_FRONT_URL = "image_front_url"
const val QUANTITY = "quantity"
const val CODE = "code"
const val STORES = "stores"
const val COUNTRIES = "countries"
const val IMAGE_NUTRITION_URL = "image_nutrition_url"
const val BRANDS = "brands"
const val GENERIC_NAME = "generic_name"
const val INGREDIENTS_TEXT = "ingredients_text"
const val IMAGE_INGREDIENTS_URL = "image_ingredients_url"
const val CATEGORIES = "categories"

val SIMPLE_FIELDS = listOf(
    PRODUCT_NAME,
    IMAGE_SMALL_URL,
    QUANTITY,
    CODE
)
val DETAIL_FIELDS = listOf(
    IMAGE_FRONT_URL,
    STORES,
    COUNTRIES,
    IMAGE_NUTRITION_URL,
    GENERIC_NAME,
    INGREDIENTS_TEXT,
    IMAGE_INGREDIENTS_URL,
    CATEGORIES,
    BRANDS
) + SIMPLE_FIELDS