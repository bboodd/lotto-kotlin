package com.www.domain.lottery

class LotteryEntity(
    val seq: Int,
    val times: Int,
    val winningNumbers: List<Int>,
    val bonusNumber: Int,
    val createdAt: String,
    val updatedAt: String
) {
}
