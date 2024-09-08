package com.coroutines.thisdayinhistory.ui.theme

import com.coroutines.common.preferences.EnumPreference
import com.coroutines.common.preferences.key

enum class ThisDayInHistoryThemeEnum : EnumPreference by key("theme") {
    Light,
    Dark,
    Auto { override val isDefault = true },
}
