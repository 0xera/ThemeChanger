package ru.aydarov.themechanger

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
internal data class Coordinates(
    val posX: Int,
    val posY: Int
) : Parcelable