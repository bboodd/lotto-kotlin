package com.www

import java.sql.DriverManager
import java.util.Properties

// TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    val name = "Kotlin"
    // TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
    // to see how IntelliJ IDEA suggests fixing it.
    println("Hello, " + name + "!")

    for (i in 1..5) {
        // TIP Press <shortcut actionId="Debug"/> to start debugging your code. We have set one <icon src="AllIcons.Debugger.Db_set_breakpoint"/> breakpoint
        // for you, but you can always add more by pressing <shortcut actionId="ToggleLineBreakpoint"/>.
        println("i = $i")
    }

    val url = "jdbc:postgresql://localhost:5432/lottery_local"
    val props = Properties().apply {
        setProperty("user", "admin")
        setProperty("password", "admin!32")
    }

    try {
        DriverManager.getConnection(url, props).use { connection ->
            println("DB 연결 성공")

            val sql = "SELECT username, balance FROM t_user"

            connection.prepareStatement(sql).use { pstmt ->
                pstmt.executeQuery().use { rs ->
                    while (rs.next()) {
                        val username = rs.getString("username")
                        val balance = rs.getInt("balance")
                        println("$username: $balance")
                    }
                }
            }

            val statement = connection.createStatement()

            val resultSet = statement.executeQuery("SELECT * FROM t_user")

            while (resultSet.next()) {
                println(resultSet.getString("username"))
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
