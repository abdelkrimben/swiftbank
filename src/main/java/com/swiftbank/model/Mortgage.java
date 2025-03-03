package com.swiftbank.model;

public class Mortgage {
    private double principal;
    private double downPayment;
    private int loanTermMonths;
    private final double annualInterestRate;
    private final double annualInsuranceRate;
    private final double monthlyPayment;
    private final double totalInterestPaid;

    public Mortgage(double principal, double downPayment, int loanTermMonths, double annualInterestRate, double annualInsuranceRate, double monthlyPayment, double totalInterestPaid) {
        this.principal = principal;
        this.downPayment = downPayment;
        this.loanTermMonths = loanTermMonths;
        this.annualInterestRate = annualInterestRate;
        this.annualInsuranceRate = annualInsuranceRate;
        this.monthlyPayment = monthlyPayment;
        this.totalInterestPaid = totalInterestPaid;
    }

    // Getters

    public double getAnnualInterestRate() { return annualInterestRate; }
    public double getAnnualInsuranceRate() { return annualInsuranceRate; }
    public double getMonthlyPayment() { return monthlyPayment; }
    public double getTotalInterestPaid() { return totalInterestPaid; }

}