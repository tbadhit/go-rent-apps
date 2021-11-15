package com.example.gorentapps.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Car(
    val name: String?,
    val rentalPrice: String?,
    val passenger: String?,
    val image: Int?
): Parcelable