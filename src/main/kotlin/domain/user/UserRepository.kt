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

    fun findByName(
        connection: Connection,
        username: String,
    ): UserEntity? {
        val sql = "SELECT * FROM $tableName WHERE username = ?"
        return connection.prepareStatement(sql).use { pstmt ->
            pstmt.setString(1, username)
            pstmt.executeQuery().use { rs ->
                if (rs.next()) mapRow(rs) else null
            }
        }
    }

    fun insertByName(
        connection: Connection,
        username: String
    ): UserEntity? {
        val sql = "INSERT INTO $tableName (username) VALUES (?) RETURNING *"
        return connection.prepareStatement(sql).use { pstmt ->
            pstmt.setString(1, username)
            pstmt.executeQuery().use { rs ->
                if (rs.next()) mapRow(rs) else null
            }
        }
    }

    fun updateBalance(
        connection: Connection,
        seq: Int,
        amount: Int
    ): Int {
        val sql = "UPDATE $tableName SET balance = balance + ? WHERE seq = ? RETURNING balance"
        return connection.prepareStatement(sql).use { pstmt ->
            pstmt.setInt(1, amount)
            pstmt.setInt(2, seq)
            pstmt.executeUpdate()
        }
    }
}
