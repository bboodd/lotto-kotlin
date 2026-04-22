package com.www.domain.model

import java.sql.Connection
import java.sql.ResultSet
import java.time.OffsetDateTime

abstract class BaseRepository<T : BaseEntity> {
    protected abstract val tableName: String

    protected abstract fun mapRow(rs: ResultSet): T

    protected fun fillBaseFields(
        entity: T,
        rs: ResultSet
    ): T =
        entity.apply {
            seq = rs.getInt("seq")
            createdAt = rs.getObject("created_at", OffsetDateTime::class.java)
            updatedAt = rs.getObject("updated_at", OffsetDateTime::class.java)
        }

    fun findBySeq(
        connection: Connection,
        seq: Int
    ): T? {
        val sql = "SELECT * FROM $tableName WHERE seq = ?"
        return connection.prepareStatement(sql).use { pstmt ->
            pstmt.setInt(1, seq)
            pstmt.executeQuery().use { rs ->
                if (rs.next()) mapRow(rs) else null
            }
        }
    }
}
