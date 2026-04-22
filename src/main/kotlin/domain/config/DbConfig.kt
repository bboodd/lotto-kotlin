package com.www.domain.config

data class DbConfig(
    val url: String = "jdbc:postgresql://localhost:5432/lottery_local",
    val username: String = "admin",
    val password: String = "admin!32"
)
