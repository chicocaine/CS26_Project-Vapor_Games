package GameCredit;

import Utility.DBConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class CodeGenerator {

    public String code100() {
        String credit100 = "ASY" + UUID.randomUUID().toString();
        insertCode("100", credit100);
        return credit100;
    }

    public String code250() {
        String credit250 = "DGL" + UUID.randomUUID().toString();
        insertCode("250", credit250);
        return credit250;
    }

    public String code500() {
        String credit500 = "DHL" + UUID.randomUUID().toString();
        insertCode("500", credit500);
        return credit500;
    }

    public String code1000() {
        String credit1000 = "PLM" + UUID.randomUUID().toString();
        insertCode("1000", credit1000);
        return credit1000;
    }

    public String code5000() {
        String credit5000 = "PCY" + UUID.randomUUID().toString();
        insertCode("5000", credit5000);
        return credit5000;
    }

    private void insertCode(String creditAmount, String code) {
        String sql = "INSERT INTO game_credits (credit_amount, code) VALUES (?, ?)";

        try (Connection conn = DBConnectionPool.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, creditAmount);
            pstmt.setString(2, code);
            pstmt.executeUpdate();
            System.out.println("Inserted code into database: " + code);

        } catch (SQLException e) {
            System.err.println("Error inserting code: " + e.getMessage());
        }
    }


    public static void main(String[] args) {
        CodeGenerator generator = new CodeGenerator();

        System.out.println("Generated Code 100: " + generator.code100());
        System.out.println("Generated Code 250: " + generator.code250());
        System.out.println("Generated Code 500: " + generator.code500());
        System.out.println("Generated Code 1000: " + generator.code1000());
        System.out.println("Generated Code 5000: " + generator.code5000());

        DBConnectionPool.close();
    }
}
