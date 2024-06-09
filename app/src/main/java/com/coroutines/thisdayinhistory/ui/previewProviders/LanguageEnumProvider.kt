package com.coroutines.thisdayinhistory.ui.previewProviders;

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.coroutines.data.models.LangEnum

class LanguageEnumProvider : PreviewParameterProvider<LangEnum> {
    override val values = listOf(
            LangEnum.ENGLISH,
            LangEnum.SWEDISH,
            LangEnum.PORTUGUESE,
            LangEnum.SPANISH,
            LangEnum.RUSSIAN,
            LangEnum.FRENCH,
            LangEnum.GERMAN,
            LangEnum.ARABIC).asSequence()
}
