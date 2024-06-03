package com.coroutines.models.synonyms

import com.coroutines.models.annotations.IntRange

@JvmInline
value class HistoryDay(@IntRange(from = 1, to = 31) val value: Int)