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
        try {
            val file = File(FILE_NAME)

            // 현재 파일의 줄 수를 세서 다음 회차 결정 (파일 없으면 1회차)
            val currentLines = if (file.exists()) file.readLines().size else 0
            val round = currentLines + 1

            val lineToSave = "$round,${numbers.joinToString(",")},$bonus\n"

            file.appendText(lineToSave)
        } catch (e: Exception) {
            println("파일 저장 중 오류가 발생했습니다: ${e.message}")
        }
    }

    fun loadAll(): List<LotteryHistory> {
        return try {
            val file = File(FILE_NAME)
            if (!file.exists()) return emptyList()
            file
                .readLines()
                .filter { it.isNotBlank() }
                .mapNotNull { line ->
                    parseLoadLine(line)
                }
        } catch (e: Exception) {
            println("파일 로드 중 오류가 발생했습니다: ${e.message}")
            emptyList()
        }
    }

    private fun parseLoadLine(line: String): LotteryHistory? {
        return try {
            val parts = line.split(",").map { it.trim().toInt() }
            if (parts.size < 8) return null

            LotteryHistory(
                round = parts[0],
                numbers = parts.subList(1, 7),
                bonus = parts[7]
            )
        } catch (e: Exception) {
            println("파일 파싱 중 오류가 발생했습니다: ${e.message}")
            null
        }
    }

    fun loadLast(): LotteryHistory? = loadAll().lastOrNull()

    fun loadByRound(round: Int): LotteryHistory? = loadAll().find { it.round == round }
}
