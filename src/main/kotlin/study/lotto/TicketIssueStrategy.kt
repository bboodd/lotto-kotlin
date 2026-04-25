package study.lotto

import study.lotto.InputUtil.getZeroWithPositiveIntInputWithSpace

interface TicketIssueStrategy {
    companion object {
        const val NUMBER_OF_STRATEGIES = 3
    }

    fun issueTicket(): LotteryTicket
}

class AutoTicketIssueStrategy : TicketIssueStrategy {
    override fun issueTicket(): LotteryTicket {
        val ticket = LotteryTicketGenerator.generateAuto()
        OutputUtil.printAutoTicket(ticket)
        return ticket
    }
}

class ManualTicketIssueStrategy : TicketIssueStrategy {
    override fun issueTicket(): LotteryTicket {
        OutputUtil.printManualInputPrompt()
        return try {
            val input = getZeroWithPositiveIntInputWithSpace(LotteryTicket.TICKET_SIZE, LotteryTicket.TICKET_SIZE)
            val ticket = LotteryTicketGenerator.generateManual(input.map { LotteryNumber(it) })
            OutputUtil.printManualTicket(ticket)
            return ticket
        } catch (e: Exception) {
            OutputUtil.printInvalidInput()
            issueTicket()
        }
    }
}

class SemiAutoTicketIssueStrategy : TicketIssueStrategy {
    override fun issueTicket(): LotteryTicket {
        return try {
            OutputUtil.printSemiAutoInManualInputPrompt()
            val input =
                getZeroWithPositiveIntInputWithSpace(
                    LotteryTicket.SEMI_AUTO_MIN_MANUAL_TICKET_SIZE,
                    LotteryTicket.SEMI_AUTO_MAX_MANUAL_TICKET_SIZE,
                )
            val ticket = LotteryTicketGenerator.generateSemiAuto(input.map { LotteryNumber(it) })
            OutputUtil.printSemiAutoTicket(ticket)
            return ticket
        } catch (e: Exception) {
            OutputUtil.printInvalidInput()
            issueTicket()
        }
    }
}
