package com.uhc.gymtrack.domain.parcelize

import android.os.Parcel
import android.os.Parcelable
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toLocalDateTime
import kotlinx.parcelize.Parceler // NOTE: kotlinx.parcelize.*
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.TypeParceler

actual typealias CommonParcelize = Parcelize // defined on Android, skipped on iOS
actual typealias CommonParcelable = Parcelable // defined on Android, skipped on iOS

actual typealias CommonParceler<T> = Parceler<T> // defined on Android, skipped on iOS
actual typealias CommonTypeParceler<T,P> = TypeParceler<T, P> // defined on Android, skipped on iOS

// Performs the type conversion to/from a Parcelable supported type (primitives only)
actual object LocalDateTimeParceler : Parceler<LocalDateTime> {  // defined on Android, skipped on iOS
    override fun create(parcel: Parcel): LocalDateTime {
        val date = parcel.readString()
        return date?.toLocalDateTime()
            ?: LocalDateTime(0, 0, 0, 0, 0)
    }
    override fun LocalDateTime.write(parcel: Parcel, flags: Int) {
        parcel.writeString(this.toString())
    }
}