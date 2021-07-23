package ru.netology.data;

import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import ru.netology.objects.CreditRequest;
import ru.netology.objects.PaymentRequest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLHelper {
    private static final String URL = System.getProperty("db.url");
    private static final String USERNAME = System.getProperty("db.username");
    private static final String PASSWORD = System.getProperty("db.password");
    private static Connection connect;

    private static Connection getConnection() {
        try {
            connect = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return connect;
    }

    public static String getPaymentStatus() {
        val runner = new QueryRunner();
        val payStatus = "SELECT status FROM payment_entity";

        try (val connect = getConnection()) {
            val paymentStatus = runner.query(connect, payStatus, new BeanHandler<>(PaymentRequest.class));
            return paymentStatus.getStatus();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return null;
    }

    public static String getPaymentAmount() {
        val runner = new QueryRunner();
        val payAmount = "SELECT amount FROM payment_entity";

        try (val connect = getConnection()) {
            val paymentAmount = runner.query(connect, payAmount, new BeanHandler<>(PaymentRequest.class));
            return paymentAmount.getAmount();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return null;
    }

    public static String getCreditStatus() {
        val runner = new QueryRunner();
        val cStatus = "SELECT status FROM credit_request_entity";

        try (val connect = getConnection()) {
            val creditStatus = runner.query(connect, cStatus, new BeanHandler<>(CreditRequest.class));
            return creditStatus.getStatus();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return null;
    }
}