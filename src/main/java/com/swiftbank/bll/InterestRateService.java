package com.swiftbank.bll;

import com.swiftbank.dal.IBankDAO;
import java.sql.SQLException;

public class InterestRateService {
    private final IBankDAO bankDAO;

    public InterestRateService(IBankDAO bankDAO) {
        this.bankDAO = bankDAO;
    }

    public double getAnnualInterestRate(String bankName) throws SQLException {
        return bankDAO.getInterestRateByBankName(bankName);
    }

}