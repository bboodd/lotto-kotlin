package study.lotto

class Wallet(
    initBalance: Long = 0,
) {
    var balance: Long = initBalance
        private set

    fun deposit(amount: Long) {
        require(amount > 0) { "입금액은 0보다 커야 합니다." }
        balance += amount
    }

    fun pay(amount: Long) {
        require(amount <= balance) { "잔액이 부족합니다." }
        balance -= amount
    }
}
