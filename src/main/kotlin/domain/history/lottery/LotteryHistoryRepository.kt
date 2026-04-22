package com.www.domain.history.lottery

import com.www.domain.model.BaseRepository
import java.sql.ResultSet
import java.time.OffsetDateTime

class LotteryHistoryRepository : BaseRepository<LotteryHistoryEntity>() {
    override val tableName: String = "t_lottery_history"

    override fun mapRow(rs: ResultSet): LotteryHistoryEntity =
        LotteryHistoryEntity(
            rs.getInt("lottery_seq"),
            rs.getInt("user_seq"),
            (rs.getArray("selected_numbers").array as Array<*>).map { it as Int },
            rs.getInt("selected_bonus_number"),
            rs.getString("type").let { LotteryHistoryType.valueOf(it) },
            rs.getString("status").let { LotteryHistoryStatus.valueOf(it) },
            rs.getInt("winning_rank")
        ).apply {
            fillBaseFields(this, rs)
        }
}
