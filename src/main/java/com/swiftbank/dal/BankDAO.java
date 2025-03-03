package com.swiftbank.dal;

import com.swiftbank.dl.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BankDAO implements IBankDAO, AutoCloseable {
    private final Connection connection;

    public BankDAO() throws SQLException {
        this.connection = DatabaseConnection.getConnection();
    }

    @Override
    public double getInterestRateByBankName(String bankName) throws SQLException {
        // Normalisation du nom de la banque
        bankName = bankName.trim().toLowerCase(); // Supprime les espaces et convertit en minuscules
        System.out.println("Recherche du taux d'intérêt pour la banque : " + bankName);

        String sql = "SELECT p.interest_rate FROM Product p JOIN Bank b ON p.bank_id = b.bank_id WHERE LOWER(TRIM(b.name)) = ? AND p.type = 'Mortgage' LIMIT 1";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, bankName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    double rate = rs.getDouble("interest_rate");
                    System.out.println("Taux d'intérêt trouvé : " + rate);
                    return rate;
                } else {
                    System.out.println("Aucun taux d'intérêt trouvé pour la banque : " + bankName);
                }
            }
        }
        return -1; // Retourner -1 si aucun taux d'intérêt n'est trouvé
    }

    @Override
    public String getBankWithLowestInterestRate(String productType) throws SQLException {
        String sql = "SELECT b.name FROM Bank b JOIN Product p ON b.bank_id = p.bank_id WHERE p.type = ? ORDER BY p.interest_rate ASC LIMIT 1";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, productType);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("name");
                }
            }
        }
        return null; // Retourner null si aucune banque n'est trouvée
    }

    @Override
    public void close() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
}