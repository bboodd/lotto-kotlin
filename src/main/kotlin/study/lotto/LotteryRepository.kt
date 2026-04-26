package study.lotto

import java.io.File

data class LotteryHistory(
    val round: Int,
    val numbers: List<Int>,
    val bonus: Int,
)

object LotteryRepository {
    private const val FILE_NAME = "lottery_history.csv"
    private const val COLUMN = "round,n1,n2,n3,n4,n5,n6,bonus"

    fun save(
        numbers: List<Int>,
        bonus: Int,
    ) {
        try {
            val file = File(FILE_NAME)
            if (!file.exists()) {
                file.writeText("$COLUMN\n")
            }

            // 컬럼이 첫번째 줄에 해당
            val lines = file.readLines().filter { it.isNotBlank() }
            val round = lines.size

            val lineToSave = "$round,${numbers.joinToString(",")},$bonus\n"

            file.appendText(lineToSave)
        } catch (e: Exception) {
            OutputUtil.printFileError(e.message)
        }
    }

    fun loadAll(): List<LotteryHistory> {
        return try {
            val file = File(FILE_NAME)
            if (!file.exists()) return emptyList()
            file
                .readLines()
                .drop(1)
                .filter { it.isNotBlank() }
                .mapNotNull { line ->
                    parseLoadLine(line)
                }
        } catch (e: Exception) {
            OutputUtil.printFileError(e.message)
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
                bonus = parts[7],
            )
        } catch (e: Exception) {
            OutputUtil.printFileError(e.message)
            null
        }
    }

    fun loadLast(): LotteryHistory? = loadAll().lastOrNull()

    fun loadByRound(round: Int): LotteryHistory? = loadAll().find { it.round == round }
}
