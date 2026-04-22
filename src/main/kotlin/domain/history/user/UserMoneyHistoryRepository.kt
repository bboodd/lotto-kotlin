package com.www.domain.history.user

import com.www.domain.model.BaseRepository
import com.www.domain.user.UserEntity
import java.sql.Connection
import java.sql.ResultSet
import java.time.OffsetDateTime
import kotlin.use

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

    fun insert(
        connection: Connection,
        entity: UserMoneyHistoryEntity
    ): UserMoneyHistoryEntity? {
        val sql = "INSERT INTO $tableName (userSeq, lotteryHistorySeq, type, amount, balanceAfter) VALUES (?, ?, ?, ?, ?) RETURNING *"
        return connection.prepareStatement(sql).use { pstmt ->
            pstmt.setInt(1, entity.userSeq)
            if (entity.lotteryHistorySeq == null) {
                pstmt.setNull(2, java.sql.Types.INTEGER)
            } else {
                pstmt.setInt(2, entity.lotteryHistorySeq)
            }
            pstmt.setString(3, entity.type.name)
            pstmt.setInt(4, entity.amount)
            pstmt.setInt(5, entity.balanceAfter)
            pstmt.executeQuery().use { rs ->
                if (rs.next()) mapRow(rs) else null
            }
        }
    }
}
