package study.lotto

object OutputUtil {
    fun printStartMessage() {
        println("\n***********************************************")
        println("*****************Lottery started***************")
        println("***********************************************")
    }

    fun printMenu(
        balance: Long,
        round: Int,
    ) {
        println("\n현재 잔액은 ${balance}원 입니다.")
        println("현재 회차는 ${round}회 입니다")
        println("진행을 원하시는 번호를 입력 해 주세요.")
        println(
            "${MenuOption.EXIT.value}. 프로그램 종료 ${MenuOption.DEPOSIT.value}. 입금 ${MenuOption.BUY_TICKET.value}. 로또 구매 ${MenuOption.FIND_HISTORY.value}. 이전 회차 당첨 번호 확인 ${MenuOption.CHECK_WINNING.value}. ${round}회차 종료 및 로또 당첨 확인",
        )
    }

    fun printInvalidInput() = println("잘못된 입력입니다 다시 입력해 주세요.")

    fun printInsufficientBalance() = println("잔액이 부족합니다.")

    fun printDepositPrompt() = println("얼마를 입금하시겠습니까?")

    fun printRoundInputPrompt() = println("몇 회차 번호를 확인 하시겠습니까?")

    fun printBuyPrompt(
        price: Int,
        balance: Long,
    ) {
        println("로또를 구매합니다. 1장에 ${price}원 / 현재 잔액 ${balance}원")
        println("구매할 총 장수를 입력하세요")
    }

    fun printStrategyPrompt() = println("자동, 수동, 반자동 장수를 차례대로 입력하세요 (예: 2 1 1):")

    fun printCountMismatch() = println("입력한 장수의 합이 총 구매 장수와 다릅니다.")

    fun printIssued(remaining: Long) = println("티켓이 발급되었습니다. / 남은 잔액 ${remaining}원")

    fun printWinningHeader(
        round: Int,
        count: Int,
    ) {
        println("$round 회차 로또 당첨을 확인합니다.")
        println("구매한 총 복권 ${count}개")
    }

    fun printWinningNumbers(winning: WinningLottery) = println("당첨번호: ${winning.ticket} 보너스 번호: ${winning.bonus}")

    fun printTicketResult(
        ticket: LotteryTicket,
        rank: Rank,
    ) = println("구매한 번호: $ticket -> ${rank.comment}")

    fun printTotalPrize(amount: Long) = println("총 당첨금액: $amount")

    fun printHistory(history: LotteryHistory?) {
        println("${history?.round}회차 번호")
        println("[${history?.numbers?.joinToString()}] 보너스: ${history?.bonus}")
    }

    fun printRoundNotAvailable() = println("현재 회차는 아직 확인 하실 수 없습니다")

    fun printRoundNotFound() = println("존재하지 않는 회차입니다.")

    fun printFileError(message: String?) = println("파일 관련 오류가 발생했습니다: $message")

    fun printTicket(ticket: LotteryTicket) = println("[$ticket]")

    fun printManualInputPrompt() =
        println(
            "${LotteryTicket.TICKET_SIZE}개의 번호를 입력해 주세요 (${LotteryNumber.MIN}-${LotteryNumber.MAX} 사이의 숫자를 띄어쓰기로 구분하여 입력 해 주세요)",
        )

    fun printSemiAutoInManualInputPrompt() =
        println(
            "수동으로 선택할 번호들을 띄어쓰기로 구분하여 입력해 주세요 ${TicketIssueStrategy.SEMI_AUTO_MIN_MANUAL_TICKET_SIZE}개~${TicketIssueStrategy.SEMI_AUTO_MAX_MANUAL_TICKET_SIZE}개  (나머지 자동)",
        )
}
