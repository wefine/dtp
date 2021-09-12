package org.wefine.dtx.jdbc;

import java.sql.*;

import org.junit.jupiter.api.Test;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestJdbcTransaction {

    @SneakyThrows
    @Test
    void test1() {
        String plusAmountSQL = "UPDATE t_user SET amount = amount + 100 WHERE username = ?";
        String minusAmountSQL = "UPDATE t_user SET amount = amount - 100 WHERE username = ?";

        Connection connection = getDBConnection();
        log.debug("会话1...开始");
        connection.setAutoCommit(false);

        PreparedStatement plusAmountPS = connection.prepareStatement(plusAmountSQL);
        plusAmountPS.setString(1, "SuperMan");
        plusAmountPS.executeUpdate();

        simulateError();

        PreparedStatement minusAmountPS = connection.prepareStatement(minusAmountSQL);
        minusAmountPS.setString(1, "BatMan");
        minusAmountPS.executeUpdate();

        connection.commit();
        log.debug("会话1...结束");

        plusAmountPS.close();
        minusAmountPS.close();
        connection.close();
    }

    @SneakyThrows
    @Test
    void test2() {
        // 通过for update加锁
        String sql = "SELECT * FROM t_user FOR UPDATE";
        String plusAmountSQL = "UPDATE t_user SET amount = ? WHERE username = ?";

        Connection dbConnection = getDBConnection();
        log.debug("会话2...开始");

        PreparedStatement queryPS = dbConnection.prepareStatement(sql);
        ResultSet rs = queryPS.executeQuery();
        long superManAmount = 0L;
        while (rs.next()) {
            String name = rs.getString(2);
            Long amount = rs.getLong(3);
            log.info("{} has amount:{}", name, amount);
            if (name.equals("SuperMan")) {
                superManAmount = amount;
            }
        }

        PreparedStatement updatePS = dbConnection.prepareStatement(plusAmountSQL);
        updatePS.setLong(1, superManAmount + 100);
        updatePS.setString(2, "SuperMan");
        updatePS.executeUpdate();

        log.debug("会话2...结束");
        queryPS.close();
        updatePS.close();
        dbConnection.close();
    }

    private static void simulateError() throws SQLException {
        throw new SQLException("模拟异常！");
    }

    private static Connection getDBConnection() throws SQLException {
        String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
        String DB_CONNECTION = "jdbc:mysql://localhost:3306/dtp";
        String DB_USER = "root";
        String DB_PASSWORD = "mysql";
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage());
        }

        return DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
    }
}
