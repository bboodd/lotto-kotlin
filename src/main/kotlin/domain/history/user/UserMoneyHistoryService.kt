package com.www.domain.history.user

import javax.sql.DataSource

class UserMoneyHistoryService(
    private val dataSource: DataSource,
    private val userMoneyHistoryRepository: UserMoneyHistoryRepository
) {
    fun getUserMoneyHistoryBySeq(seq: Int): UserMoneyHistoryEntity? =
        dataSource.connection.use { conn ->
            userMoneyHistoryRepository.findBySeq(conn, seq)
        }

    fun registerHistory(entity: UserMoneyHistoryEntity) =
        dataSource.connection.use { conn ->
            userMoneyHistoryRepository.insert(conn, entity)
        }
}
