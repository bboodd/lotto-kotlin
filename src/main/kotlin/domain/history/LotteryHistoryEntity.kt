package com.www.domain.history

class LotteryHistoryEntity(
    val seq: Int,
    val lotterySeq: Int,
    val userSeq: Int,
    val selectedNumbers: List<Int>,
    val selectedBonusNumber: Int?,
    val type: String,
    val bonusNumber: Int,
    val createdAt: String,
    val updatedAt: String
) {
}
