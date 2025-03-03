package com.swiftbank.dal;

import com.swiftbank.model.Product;
import java.sql.SQLException;
import java.util.List;

public interface IProductDAO {
    List<Product> getProductsByType(String type) throws SQLException;
    double getInsuranceRate(double loanAmount, int loanTermMonths) throws SQLException;
}