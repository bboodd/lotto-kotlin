package com.www.domain.user

import com.www.domain.history.user.UserMoneyHistoryEntity
import com.www.domain.history.user.UserMoneyHistoryRepository
import com.www.domain.history.user.UserMoneyHistoryType
import javax.sql.DataSource

class UserService(
    private val dataSource: DataSource,
    private val userRepository: UserRepository,
    private val userMoneyHistoryRepository: UserMoneyHistoryRepository
) {
    fun getUserBySeq(seq: Int): UserEntity? =
        dataSource.connection.use { conn ->
            userRepository.findBySeq(conn, seq)
        }

    fun getUserByName(username: String): UserEntity? =
        dataSource.connection.use { conn ->
            userRepository.findByName(conn, username)
        }

    fun registerUser(username: String): UserEntity? =
        dataSource.connection.use { conn ->
            userRepository.insertByName(conn, username)
        }

    fun chargeBalance(seq: Int, amount: Int): Int =
        dataSource.connection.use { conn ->
            val balanceAfter = userRepository.updateBalance(conn, seq, amount)
            val history = UserMoneyHistoryEntity(
                userSeq = seq,
                type = UserMoneyHistoryType.CHARGE,
                amount = amount,
                balanceAfter = balanceAfter
            )
            userMoneyHistoryRepository.insert(conn, history)
            balanceAfter
        }
}
