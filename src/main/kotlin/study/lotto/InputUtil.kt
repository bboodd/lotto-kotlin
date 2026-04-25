package study.lotto

object InputUtil {
    fun getPositiveIntInput(): Int {
        val input = readln().toIntOrNull()
        return if (input != null && input > 0) {
            input
        } else {
            OutputUtil.printInvalidInput()
            getPositiveIntInput()
        }
    }

    fun getZeroWithPositiveIntInput(): Int {
        val input = readln().toIntOrNull()
        return if (input != null && input >= 0) {
            input
        } else {
            OutputUtil.printInvalidInput()
            getZeroWithPositiveIntInput()
        }
    }

    fun getZeroWithPositiveIntInputWithSpace(
        minSize: Int,
        maxSize: Int,
    ): List<Int> {
        val line = readln()
        val parts = line.split(" ").filter { it.isNotBlank() }
        val numbers = parts.mapNotNull { it.toIntOrNull() }

        return if (parts.size != numbers.size || numbers.any { it < 0 } || numbers.size !in minSize..maxSize) {
            OutputUtil.printInvalidInput()
            getZeroWithPositiveIntInputWithSpace(minSize, maxSize)
        } else {
            numbers
        }
    }

    fun receiveTotalTicketCount(
        price: Int,
        balance: Long,
    ): Int {
        OutputUtil.printBuyPrompt(price, balance)
        val input = getPositiveIntInput()
        require(input * price <= balance) { OutputUtil.printInsufficientBalance() }
        return input
    }

    fun receiveStrategyCounts(totalCount: Int): StrategyCounts {
        OutputUtil.printStrategyPrompt()
        val (auto, manual, semi) =
            getZeroWithPositiveIntInputWithSpace(
                TicketIssueStrategy.NUMBER_OF_STRATEGIES,
                TicketIssueStrategy.NUMBER_OF_STRATEGIES,
            )
        require(auto + manual + semi == totalCount) { OutputUtil.printCountMismatch() }
        return StrategyCounts(auto, manual, semi)
    }
}
