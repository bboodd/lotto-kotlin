package study.lotto

import study.lotto.InputUtil.getZeroWithPositiveIntInput

class LotteryProgram {
    var wallet = Wallet()
    val tickets: MutableList<LotteryTicket> = mutableListOf()
    var lastRound = (LotteryRepository.loadLast()?.round ?: 0) + 1

    fun start() {
        OutputUtil.printStartMessage()
        entry()
    }

    fun entry() {
        OutputUtil.printMenu(wallet.balance, lastRound)
        try {
            val input = getZeroWithPositiveIntInput()
            val menu = MenuOption.from(input) ?: throw IllegalArgumentException("유효하지 않은 메뉴입니다")

            when (menu) {
                MenuOption.EXIT -> return
                MenuOption.DEPOSIT -> deposit()
                MenuOption.BUY_TICKET -> buyTickets()
                MenuOption.FIND_HISTORY -> findLotteryHistoryByRound()
                MenuOption.CHECK_WINNING -> checkWinning()
            }
        } catch (e: Exception) {
            OutputUtil.printInvalidInput()
            entry()
        }
    }

    fun findLotteryHistoryByRound() {
        OutputUtil.printRoundInputPrompt()
        val round = getZeroWithPositiveIntInput()
        if (round == lastRound) {
            OutputUtil.printRoundNotAvailable()
        } else if (round > lastRound) {
            OutputUtil.printRoundNotFound()
        } else {
            val findHistory = LotteryRepository.loadByRound(round)
            OutputUtil.printHistory(findHistory)
        }
        entry()
    }

    fun deposit() {
        OutputUtil.printDepositPrompt()
        try {
            val input = getZeroWithPositiveIntInput()
            wallet.deposit(input.toLong())
            entry()
        } catch (e: Exception) {
            OutputUtil.printInvalidInput()
            deposit()
        }
    }

    fun checkWinning() {
        OutputUtil.printWinningHeader(lastRound, tickets.size)

        val winningLottery = WinningLotteryGenerator.generate()
        OutputUtil.printWinningNumbers(winningLottery)

        var totalWinningAmount = 0L
        for (ticket in tickets) {
            val rank = LotteryMatcher.match(winningLottery, ticket)
            totalWinningAmount += rank.prize

            OutputUtil.printTicketResult(ticket, rank)
        }

        LotteryRepository.save(winningLottery.ticket.numbers.map { it.number }, winningLottery.bonus.number)
        lastRound += 1
        OutputUtil.printTotalPrize(totalWinningAmount)

        entry()
    }

    fun buyTickets() {
        try {
            val totalCount = InputUtil.receiveTotalTicketCount(LOTTERY_PRICE, wallet.balance)
            val totalPrice = (totalCount * LOTTERY_PRICE).toLong()

            val strategyCounts = InputUtil.receiveStrategyCounts(totalCount)

            wallet.pay(totalPrice)

            val strategies = createStrategies(strategyCounts)

            strategies.forEach { strategy ->
                val ticket = LotteryMachine.issue(strategy)
                tickets.add(ticket)
                OutputUtil.printTicket(ticket)
            }

            OutputUtil.printIssued(wallet.balance)
            entry()
        } catch (e: Exception) {
            OutputUtil.printInvalidInput()
            buyTickets()
        }
    }

    private fun createStrategies(counts: StrategyCounts): List<TicketIssueStrategy> =
        buildList {
            repeat(counts.auto) {
                add(AutoTicketIssueStrategy())
            }
            repeat(counts.manual) {
                OutputUtil.printManualInputPrompt()
                add(ManualTicketIssueStrategy(InputUtil.receiveLotteryNumbers()))
            }
            repeat(counts.semi) {
                OutputUtil.printSemiAutoInManualInputPrompt()
                add(SemiAutoTicketIssueStrategy(InputUtil.receiveSemiAutoNumbers()))
            }
        }

    companion object {
        private const val LOTTERY_PRICE = 1000
    }
}
