package com.www.domain.lottery

import javax.sql.DataSource

class LotteryService(
    private val dataSource: DataSource,
    private val lotteryRepository: LotteryRepository
) {
    fun getLotteryBySeq(seq: Int): LotteryEntity? =
        dataSource.connection.use { conn ->
            lotteryRepository.findBySeq(conn, seq)
        }
}
