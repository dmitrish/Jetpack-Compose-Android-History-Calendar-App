package com.coroutines.thisdayinhistory.uimodels

import com.coroutines.thisdayinhistory.R


enum class ShareWith (
    val displayName: String,
    val packageName: String?,
    val iconId: Int) {
    WHATSAPP ("WhatsApp","com.whatsapp", R.drawable.whatsapp),
    TWITTER ("Twitter","com.twitter.android", R.drawable.icons_twitterx),
    OTHER("Other",null, R.drawable.icons_share)
}

