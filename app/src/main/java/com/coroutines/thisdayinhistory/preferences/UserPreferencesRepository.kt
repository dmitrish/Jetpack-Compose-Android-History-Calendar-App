package com.coroutines.thisdayinhistory.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.coroutines.common.preferences.getEnum
import com.coroutines.common.preferences.setEnum
import com.coroutines.data.models.LangEnum
import com.coroutines.data.models.OnboardingStatusEnum
import com.coroutines.thisdayinhistory.ui.theme.ThisDayInHistoryThemeEnum
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class UserPreferencesRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    fun getLanguagePreference(): Flow<LangEnum> {
        return dataStore.getEnum<LangEnum>()
    }
    fun getThemePreference(): Flow<ThisDayInHistoryThemeEnum> {
        return dataStore.getEnum<ThisDayInHistoryThemeEnum>()
    }

    fun getOnboardedStatus(): Flow<OnboardingStatusEnum>{
        return dataStore.getEnum<OnboardingStatusEnum>()
    }

    suspend fun setLanguagePreference(langEnum: LangEnum){
        dataStore.setEnum(langEnum)
    }

    suspend fun setThemePreference(historyCatThemeEnum: ThisDayInHistoryThemeEnum){
        dataStore.setEnum(historyCatThemeEnum)
    }

    suspend fun setOnboarded(){
        dataStore.setEnum(OnboardingStatusEnum.Onboarded)
    }
}