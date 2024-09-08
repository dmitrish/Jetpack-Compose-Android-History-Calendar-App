package com.coroutines.thisdayinhistory.di

import android.content.Context
import com.coroutines.usecase.CountryCodeMappingsRawJson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.io.IOException
import java.io.InputStream
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object CountryCodeMappingsRawJsonModule {

    @Singleton
    @Provides
    fun provideCountryCodeMappingsRawJson(@ApplicationContext appContext: Context): CountryCodeMappingsRawJson {
        return CountryCodeMappingsRawJson(loadJSONFromAsset(appContext) ?: "")
    }

    @Suppress("PrintStackTrace")
    private fun loadJSONFromAsset(appContext: Context, fileName: String = "countrycodemappings.json"): String? {

        var json: String? = null
        json = try {
            val inputStream: InputStream = appContext.assets.open(fileName)
            val size: Int = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            String(buffer, Charsets.UTF_8)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return ""
        }
        return json
    }
}
