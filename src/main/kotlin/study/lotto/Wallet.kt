package study.lotto

class Wallet(
    var balance: Long = 0,
) {
    fun deposit(amount: Long) {
        require(amount > 0) { OutputUtil.printInvalidInput() }
        balance += amount
    }

    fun pay(amount: Long) {
        if (balance < amount) throw Exception("잔액이 부족합니다")
        balance -= amount
    }
}
