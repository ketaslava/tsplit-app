package com.ktvincco.tsplit.data

interface PermissionController {
    fun requestPermissions(callback: (result: Boolean) -> Unit)
}