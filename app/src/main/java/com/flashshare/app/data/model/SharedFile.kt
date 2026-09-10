package com.flashshare.app.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SharedFile(
    val id: String = "",
    val name: String,
    val path: String,
    val size: Long,
    val mimeType: String,
    val thumbnail: String? = null,
    val isSelected: Boolean = false
) : Parcelable
