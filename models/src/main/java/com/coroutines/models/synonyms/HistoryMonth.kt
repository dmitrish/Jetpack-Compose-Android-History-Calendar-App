package com.coroutines.models.synonyms

import androidx.annotation.IntRange

@JvmInline
value class HistoryMonth(@IntRange(from = 1, to = 12) val value: Int)