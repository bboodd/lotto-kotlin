package com.www.domain.config

import com.www.domain.history.lottery.LotteryHistoryRepository
import com.www.domain.history.lottery.LotteryHistoryService
import com.www.domain.history.user.UserMoneyHistoryRepository
import com.www.domain.history.user.UserMoneyHistoryService
import com.www.domain.lottery.LotteryRepository
import com.www.domain.lottery.LotteryService
import com.www.domain.user.UserRepository
import com.www.domain.user.UserService
import javax.sql.DataSource

class AppConfig(
    private val dataSource: DataSource
) {
    val userRepository = UserRepository()
    val lotteryRepository = LotteryRepository()
    val userMoneyHistoryRepository = UserMoneyHistoryRepository()
    val lotteryHistoryRepository = LotteryHistoryRepository()

    val userService = UserService(dataSource, userRepository, userMoneyHistoryRepository)
    val lotteryService = LotteryService(dataSource, lotteryRepository)
    val userMoneyHistoryService = UserMoneyHistoryService(dataSource, userMoneyHistoryRepository)
    val lotteryHistoryService = LotteryHistoryService(dataSource, lotteryHistoryRepository)
}
