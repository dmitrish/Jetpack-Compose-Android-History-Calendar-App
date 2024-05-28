package com.coroutines.models.annotations

@MustBeDocumented
@Retention(AnnotationRetention.BINARY)
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER,
    AnnotationTarget.VALUE_PARAMETER,
    AnnotationTarget.FIELD,
    AnnotationTarget.LOCAL_VARIABLE,
    AnnotationTarget.ANNOTATION_CLASS
)
public annotation class IntRange(
    /** Smallest value, inclusive */
    val from: Long = Long.MIN_VALUE,
    /** Largest value, inclusive */
    val to: Long = Long.MAX_VALUE,
)