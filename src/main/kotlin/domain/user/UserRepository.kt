package com.www.domain.user

import com.www.domain.model.BaseRepository
import java.sql.Connection
import java.sql.ResultSet
import java.time.OffsetDateTime

class UserRepository : BaseRepository<UserEntity>() {
    override val tableName: String = "t_user"

    override fun mapRow(rs: ResultSet): UserEntity =
        UserEntity(
            rs.getString("username"),
            rs.getInt("balance")
        ).apply {
            fillBaseFields(this, rs)
        }
}
