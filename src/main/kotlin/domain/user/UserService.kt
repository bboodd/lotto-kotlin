package com.www.domain.user

import javax.sql.DataSource

class UserService(
    private val dataSource: DataSource,
    private val userRepository: UserRepository
) {
    fun getUserBySeq(seq: Int): UserEntity? =
        dataSource.connection.use { conn ->
            userRepository.findBySeq(conn, seq)
        }
}
