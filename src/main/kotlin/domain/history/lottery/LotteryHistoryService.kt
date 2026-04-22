package com.www.domain.history.lottery

import javax.sql.DataSource

class LotteryHistoryService(
    private val dataSource: DataSource,
    private val lotteryHistoryRepository: LotteryHistoryRepository
) {
    fun getLotteryHistoryBySeq(seq: Int): LotteryHistoryEntity? =
        dataSource.connection.use { conn ->
            lotteryHistoryRepository.findBySeq(conn, seq)
        }
}
