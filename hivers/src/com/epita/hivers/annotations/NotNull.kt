package com.epita.hivers.annotations

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.FIELD, AnnotationTarget.LOCAL_VARIABLE,
        AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class NotNull {

}