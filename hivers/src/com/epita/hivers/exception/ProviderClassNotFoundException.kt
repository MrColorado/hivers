package com.epita.hivers.exception

class ProviderClassNotFoundException(classType: Class<*>) : Exception(classType.name + " class not found") {
}