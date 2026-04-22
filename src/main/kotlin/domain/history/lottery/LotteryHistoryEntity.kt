package com.www.domain.history.lottery

import com.www.domain.model.BaseEntity

class LotteryHistoryEntity(
    val lotterySeq: Int,
    val userSeq: Int,
    val selectedNumbers: List<Int>,
    val selectedBonusNumber: Int,
    val type: LotteryHistoryType,
    val status: LotteryHistoryStatus,
    val winningRank: Int? = null
) : BaseEntity()
