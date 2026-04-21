package com.www.domain.history

class UserMoneyHistoryEntity(
    val seq: Int,
    val userSeq: Int,
    val lotteryHistorySeq: Int?,
    val type: String,
    val amount: Int,
    val balanceAfter: Int,
    val createdAt: String,
    val updatedAt: String
) {
}
