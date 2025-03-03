package com.swiftbank.bll;
import com.swiftbank.model.Mortgage;

import java.sql.SQLException;

public class MortgageAmortizationService {
    private final InterestRateService interestRateService;
    private final InsuranceRateService insuranceRateService;

    public MortgageAmortizationService(InterestRateService interestRateService, InsuranceRateService insuranceRateService) {
        this.interestRateService = interestRateService;
        this.insuranceRateService = insuranceRateService;
    }

    public Mortgage calculateMortgage(double principal, double downPayment, int loanTermMonths, String bankName) throws SQLException {
        if (principal <= 0 || downPayment < 0 || loanTermMonths <= 0) {
            throw new IllegalArgumentException("Les valeurs doivent être positives et valides.");
        }

        double loanAmount = principal - downPayment;
        double annualInterestRate = interestRateService.getAnnualInterestRate(bankName);
        if (annualInterestRate < 0) {
            throw new IllegalArgumentException("Banque non trouvée ou taux d'intérêt invalide pour : " + bankName);
        }

        double annualInsuranceRate = insuranceRateService.getInsuranceRate(loanAmount, loanTermMonths);
        double monthlyPayment = calculateMonthlyPayment(loanAmount, annualInterestRate, annualInsuranceRate, loanTermMonths);
        double totalInterestPaid = (monthlyPayment * loanTermMonths) - loanAmount;

        return new Mortgage(principal, downPayment, loanTermMonths, annualInterestRate, annualInsuranceRate, monthlyPayment, totalInterestPaid);
    }

    private double calculateMonthlyPayment(double loanAmount, double annualInterestRate, double annualInsuranceRate, int loanTermMonths) {
        if (loanAmount <= 0 || loanTermMonths <= 0) {
            throw new IllegalArgumentException("Le montant du prêt et la durée doivent être positifs.");
        }
        double monthlyInterestRate = annualInterestRate / 12 / 100;
        double monthlyInsuranceRate = annualInsuranceRate / 12 / 100;
        double totalMonthlyRate = monthlyInterestRate + monthlyInsuranceRate;
        if (totalMonthlyRate <= 0) {
            throw new IllegalArgumentException("Le taux d'intérêt ou d'assurance est invalide.");
        }
        return loanAmount * (totalMonthlyRate * Math.pow(1 + totalMonthlyRate, loanTermMonths)) / (Math.pow(1 + totalMonthlyRate, loanTermMonths) - 1);
    }
}