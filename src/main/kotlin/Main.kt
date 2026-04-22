package com.www

import com.www.domain.config.AppConfig
import com.www.domain.config.DbConfig
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.slf4j.LoggerFactory
import ch.qos.logback.classic.Level
import ch.qos.logback.classic.Logger

fun main() {
    val root = LoggerFactory.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME) as Logger
    root.level = Level.INFO

    LotteryProgram().start()
}

class LotteryProgram {
    val LOTTERY_PRICE = 1000

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

    fun start() {
        println("\n********************************************************")
        println("************************PLAY START!*********************")
        println("********************************************************\n")
        setUser()
    }

    fun setUser() {
        print("사용할 이름을 입력하세요:")

        val name = readln()
        // TODO: 예외처리

        var user = app.userService.getUserByName(name)
        if (user != null) {
            println("또오셨네요 ${user.username} 잔액은 ${user.balance}원 입니다.")
        } else {
            user = app.userService.registerUser(name)
            // TODO: 예외처리
            println("처음뵙겠습니다 ${user?.username} 잔액은 ${user?.balance}원 입니다.")
        }
    }

    fun deposit() {
        // TODO: 해야함
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
