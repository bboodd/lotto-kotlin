package com.www.domain.history.user

import com.www.domain.model.BaseRepository
import java.sql.ResultSet
import java.time.OffsetDateTime

class UserMoneyHistoryRepository : BaseRepository<UserMoneyHistoryEntity>() {
    override val tableName: String = "t_user_money_history"

    override fun mapRow(rs: ResultSet): UserMoneyHistoryEntity =
        UserMoneyHistoryEntity(
            rs.getInt("user_seq"),
            rs.getInt("lottery_history_seq"),
            rs.getString("type").let { UserMoneyHistoryType.valueOf(it) },
            rs.getInt("amount"),
            rs.getInt("balance_after")
        ).apply {
            fillBaseFields(this, rs)
        }
}
