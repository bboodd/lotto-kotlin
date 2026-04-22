package com.www.domain.lottery

import com.www.domain.model.BaseRepository
import java.sql.ResultSet
import java.time.OffsetDateTime

class LotteryRepository : BaseRepository<LotteryEntity>() {
    override val tableName: String = "t_lottery"

    override fun mapRow(rs: ResultSet): LotteryEntity =
        LotteryEntity(
            rs.getInt("times"),
            (rs.getArray("winning_numbers").array as Array<*>).map { it as Int },
            rs.getInt("bonus_number")
        ).apply {
            fillBaseFields(this, rs)
        }
}
