package GameCredit;

import Utility.DBConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

public class CodeGenerator {

    private static final String ALPHANUMERIC = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int CODE_LENGTH = 10;
    private final Random random = new Random();

    private String generateCode() {
        StringBuilder code = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            code.append(ALPHANUMERIC.charAt(random.nextInt(ALPHANUMERIC.length())));
        }
        return code.toString();
    }

    public String code100() {
        String credit100 = generateCode();
        insertCode("100", "AGS"+credit100);
        return credit100;
    }

    public String code250() {
        String credit250 = generateCode();
        insertCode("250", "DGL"+credit250);
        return credit250;
    }

    public String code500() {
        String credit500 = generateCode();
        insertCode("500", "DHL"+credit500);
        return credit500;
    }

    public String code1000() {
        String credit1000 = generateCode();
        insertCode("1000", "PLM"+credit1000);
        return credit1000;
    }

    public String code5000() {
        String credit5000 = generateCode();
        insertCode("5000", "PCY"+credit5000);
        return credit5000;
    }

    public String code10000() {
        String credit10000 = generateCode();
        insertCode("10000", "STG"+credit10000);
        return credit10000;
    }

    private void insertCode(String creditAmount, String code) {
        String sql = "INSERT INTO VaporGames.game_credits (credit_amount, code) VALUES (?, ?)";

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
        System.out.println("Generated Code 10000: " + generator.code10000());

        DBConnectionPool.close();
    }
}