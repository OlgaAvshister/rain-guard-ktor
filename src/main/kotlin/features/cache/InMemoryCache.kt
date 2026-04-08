package com.olga.avshister.features.cache

import com.olga.avshister.features.auth.AuthReceiveRemote


data class TokenCache(
    val phone: String,
    val token: String,
)

object InMemoryCache {
    val userList: MutableList<AuthReceiveRemote> = mutableListOf()
    val token: MutableList<TokenCache> = mutableListOf()
}