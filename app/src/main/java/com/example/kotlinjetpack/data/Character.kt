package com.example.kotlinjetpack.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Character (val name: String, val image: String, val species: String?, val status: String?) : Parcelable