package hr.tvz.android.projektplaftak.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Jersey(val id: Int, val team: String, val league: String, val manufacturer: String, val gender: String, val material: String, val price: Int, val imageUrl: String): Parcelable {}