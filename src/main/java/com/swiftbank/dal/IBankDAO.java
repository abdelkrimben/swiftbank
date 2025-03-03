package com.swiftbank.dal;

import java.sql.SQLException;

public interface IBankDAO {
    double getInterestRateByBankName(String bankName) throws SQLException;
    String getBankWithLowestInterestRate(String productType) throws SQLException;
}