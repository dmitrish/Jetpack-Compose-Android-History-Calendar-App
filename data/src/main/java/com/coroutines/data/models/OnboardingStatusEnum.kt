package com.coroutines.data.models

import com.coroutines.common.preferences.EnumPreference
import com.coroutines.common.preferences.key


enum class OnboardingStatusEnum() : EnumPreference by key("onboarded"){
    NotOnboarded { override val isDefault = true },
    Onboarded
}