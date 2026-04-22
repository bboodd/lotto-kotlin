package com.www.domain.lottery

import com.www.domain.model.BaseEntity

class LotteryEntity(
    val times: Int,
    val winningNumbers: List<Int>,
    val bonusNumber: Int
) : BaseEntity()
