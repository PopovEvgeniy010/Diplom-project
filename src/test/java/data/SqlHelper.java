package data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.DriverManager;


public class SqlHelper {
    private static String url = System.getProperty("db.url");
    private static String userDB = System.getProperty("app.userDB");
    private static String password = System.getProperty("app.password");

    @SneakyThrows
    public static void clearAllData() {
        var runner = new QueryRunner();
        var conn = DriverManager.getConnection(url, userDB, password);
        runner.update(conn, "DELETE FROM credit_request_entity;");
        runner.update(conn, "DELETE FROM payment_entity;");
        runner.update(conn, "DELETE FROM order_entity;");
    }

    @SneakyThrows
    public static String getcheckPaymentStatus() {
        var runner = new QueryRunner();
        var conn = DriverManager.getConnection(url, userDB, password);
        var payment = "SELECT status FROM payment_entity WHERE transaction_id = (SELECT payment_id FROM order_entity ORDER BY created DESC limit 1);";
        return runner.query(conn, payment, new ScalarHandler<>());
    }


    @SneakyThrows
    public static String getCheckCreditStatus() {
        var runner = new QueryRunner();
        var conn = DriverManager.getConnection(url, userDB, password);
        var creditDataSQL = "SELECT status FROM credit_request_entity WHERE bank_id = (SELECT credit_id FROM order_entity ORDER BY created DESC limit 1);";
        return runner.query(conn, creditDataSQL, new ScalarHandler<>());
    }
}
