package com.www.domain.user

import com.www.domain.model.BaseEntity

class UserEntity(
    val username: String,
    val balance: Int
) : BaseEntity()
