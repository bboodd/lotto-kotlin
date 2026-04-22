package com.www.domain.history.user

import com.www.domain.model.BaseEntity

class UserMoneyHistoryEntity(
    val userSeq: Int,
    val lotteryHistorySeq: Int? = null,
    val type: UserMoneyHistoryType,
    val amount: Int,
    val balanceAfter: Int
) : BaseEntity()
