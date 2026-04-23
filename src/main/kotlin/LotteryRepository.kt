package com.www

import java.io.File

data class LotteryHistory(
    val round: Int,
    val numbers: List<Int>,
    val bonus: Int
)

object LotteryRepository {
    private const val FILE_NAME = "lottery_history.txt"

    fun save(
        numbers: List<Int>,
        bonus: Int
    ) {
        val file = File(FILE_NAME)

        // 현재 파일의 줄 수를 세서 다음 회차 결정 (파일 없으면 1회차)
        val currentLines = if (file.exists()) file.readLines().size else 0
        val round = currentLines + 1

        val lineToSave = "$round,${numbers.joinToString(",")},$bonus\n"

        file.appendText(lineToSave)
    }

    fun loadAll(): List<LotteryHistory> {
        val file = File(FILE_NAME)
        if (!file.exists()) return emptyList()

        return file.readLines().map { line ->
            val parts = line.split(",").map { it.trim().toInt() }

            LotteryHistory(
                round = parts[0],
                numbers = parts.subList(1, 7),
                bonus = parts[7]
            )
        }
    }

    fun loadLast(): LotteryHistory? = loadAll().lastOrNull()

    fun loadByRound(round: Int): LotteryHistory? = loadAll().find { it.round == round }
}
