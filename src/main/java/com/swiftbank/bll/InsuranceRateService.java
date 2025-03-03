package com.swiftbank.bll;

import com.swiftbank.dal.IProductDAO;
import java.sql.SQLException;

public class InsuranceRateService {
    private final IProductDAO productDAO;

    public InsuranceRateService(IProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    public double getInsuranceRate(double loanAmount, int loanTermMonths) throws SQLException {
        return productDAO.getInsuranceRate(loanAmount, loanTermMonths);
    }
}