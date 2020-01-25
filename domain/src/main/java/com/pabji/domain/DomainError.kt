package com.pabji.domain

sealed class DomainError
object SearchError : DomainError()
object DetailError : DomainError()