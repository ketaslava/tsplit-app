package com.ktvincco.tsplit.data

interface Logger {
    fun log(logTag: String, message: String)
    fun logW(logTag: String, message: String)
    fun logE(logTag: String, message: String)
    fun logUniqueString(string: String, fileName: String)
}