package com.pabji.data.repositories

interface PermissionChecker {

    enum class Permission { CAMERA }

    suspend fun check(permission: Permission): Boolean
}