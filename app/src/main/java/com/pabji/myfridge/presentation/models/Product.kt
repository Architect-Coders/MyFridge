package com.pabji.myfridge.presentation.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(val id: Long?, val name: String = "", val previewUrl: String = "") : Parcelable