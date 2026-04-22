package com.www

import com.www.domain.config.AppConfig
import com.www.domain.config.DbConfig
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import java.sql.DriverManager
import java.util.Properties

fun main() {
    val dbConfig = DbConfig()

    val dataSource =
        HikariDataSource(
            HikariConfig().apply {
                jdbcUrl = dbConfig.url
                username = dbConfig.username
                password = dbConfig.password
            }
        )

    val app = AppConfig(dataSource)

    app.userService.getUserBySeq(1).also { println(it?.username) }
}

class Game {
    fun play() {
        println("PLAY START!")
        print("사용할 이름을 입력하세요: ")
        val name = readln()
    }

    fun win() {
    }

    fun lose() {
    }

    fun endLottery() {
    }

    fun findUser() {
    }

    fun startLottery() {
    }
}
