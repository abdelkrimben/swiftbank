package com.swiftbank.dal;

import com.swiftbank.dl.DatabaseConnection;
import com.swiftbank.model.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO implements IProductDAO, AutoCloseable {
    private final Connection connection;

    public ProductDAO() throws SQLException {
        this.connection = DatabaseConnection.getConnection();
    }

    @Override
    public List<Product> getProductsByType(String type) throws SQLException {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM Product WHERE type = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, type);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Product product = new Product();
                    product.setProductId(rs.getInt("product_id"));
                    product.setName(rs.getString("name"));
                    product.setType(rs.getString("type"));
                    product.setDescription(rs.getString("description"));
                    product.setInterestRate(rs.getDouble("interest_rate"));
                    products.add(product);
                }
            }
        }
        return products;
    }

    @Override
    public double getInsuranceRate(double loanAmount, int loanTermMonths) throws SQLException {
        String sql = "SELECT interest_rate FROM Product WHERE type = 'Insurance' LIMIT 1";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("interest_rate");
                }
            }
        }
        return 0.0; // Retourner 0.0 si aucun taux d'assurance n'est trouvé
    }

    @Override
    public void close() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
}