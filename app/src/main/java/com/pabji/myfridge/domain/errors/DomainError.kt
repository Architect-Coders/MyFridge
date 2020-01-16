package com.pabji.myfridge.domain.errors

sealed class DomainError
object SearchError : DomainError()
object DetailError : DomainError()


