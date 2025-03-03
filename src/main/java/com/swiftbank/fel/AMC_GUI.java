package com.swiftbank.fel;

import com.swiftbank.bll.InsuranceRateService;
import com.swiftbank.bll.InterestRateService;
import com.swiftbank.bll.MortgageAmortizationService;
import com.swiftbank.dal.BankDAO;
import com.swiftbank.dal.ProductDAO;
import com.swiftbank.model.Mortgage;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AMC_GUI extends JFrame {
    private final JTextField principalField;
    private final JTextField downPaymentField;
    private final JTextField loanTermField;
    private final JComboBox<String> bankComboBox;
    private final JTextArea resultArea;
    private final JTextArea historyArea;
    private final List<String> historyList;

    public AMC_GUI() {
        // Configuration de la fenêtre
        setTitle("Calculateur d'Hypothèque");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrer la fenêtre

        // Initialisation de la liste d'historique
        historyList = new ArrayList<>();

        // Création des composants
        JLabel principalLabel = new JLabel("Prix de la maison (> 0):");
        principalField = new JTextField(10);

        JLabel downPaymentLabel = new JLabel("Apport initial (>= 0):");
        downPaymentField = new JTextField(10);

        JLabel loanTermLabel = new JLabel("Durée du prêt (en mois, > 0):");
        loanTermField = new JTextField(10);

        JLabel bankLabel = new JLabel("Nom de la banque:");
        String[] banks = {"Royal Bank of Canada", "Toronto-Dominion Bank", "Scotiabank", "Bank of Montreal", "Canadian Imperial Bank of Commerce", "National Bank of Canada", "Desjardins Group"};
        bankComboBox = new JComboBox<>(banks);

        JButton calculateButton = new JButton("Calculer");
        JButton resetButton = new JButton("Réinitialiser");
        resultArea = new JTextArea(5, 30);
        resultArea.setEditable(false);
        historyArea = new JTextArea(10, 30);
        historyArea.setEditable(false);

        // Gestion des événements des boutons
        calculateButton.addActionListener(e -> calculateMortgage());
        resetButton.addActionListener(e -> resetFields());


        // Organisation des composants dans des panneaux
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(4, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Saisie des données"));
        inputPanel.add(principalLabel);
        inputPanel.add(principalField);
        inputPanel.add(downPaymentLabel);
        inputPanel.add(downPaymentField);
        inputPanel.add(loanTermLabel);
        inputPanel.add(loanTermField);
        inputPanel.add(bankLabel);
        inputPanel.add(bankComboBox);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(calculateButton);
        buttonPanel.add(resetButton);

        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new BorderLayout());
        resultPanel.setBorder(BorderFactory.createTitledBorder("Résultats"));
        resultPanel.add(new JScrollPane(resultArea), BorderLayout.CENTER);

        JPanel historyPanel = new JPanel();
        historyPanel.setLayout(new BorderLayout());
        historyPanel.setBorder(BorderFactory.createTitledBorder("Historique des calculs"));
        historyPanel.add(new JScrollPane(historyArea), BorderLayout.CENTER);

        // Ajout des panneaux à la fenêtre
        setLayout(new BorderLayout(10, 10));
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(resultPanel, BorderLayout.SOUTH);
        add(historyPanel, BorderLayout.EAST);
    }

    private void calculateMortgage() {
        try {
            // Récupération des valeurs saisies
            double principal = Double.parseDouble(principalField.getText());
            double downPayment = Double.parseDouble(downPaymentField.getText());
            int loanTermMonths = Integer.parseInt(loanTermField.getText());
            String bankName = (String) bankComboBox.getSelectedItem();

            // Initialisation des services
            try (BankDAO bankDAO = new BankDAO();
                 ProductDAO productDAO = new ProductDAO()) {

                InterestRateService interestRateService = new InterestRateService(bankDAO);
                InsuranceRateService insuranceRateService = new InsuranceRateService(productDAO);
                MortgageAmortizationService mortgageService = new MortgageAmortizationService(interestRateService, insuranceRateService);

                // Calcul de l'hypothèque
                Mortgage mortgage = mortgageService.calculateMortgage(principal, downPayment, loanTermMonths, bankName);

                // Affichage des résultats
                String result = String.format(
                        """
                        Versement mensuel : %.2f
                        Total des intérêts payés : %.2f
                        Taux d'intérêt annuel : %.2f%%
                        Taux d'assurance annuel : %.2f%%
                
                        """,
                        mortgage.getMonthlyPayment(),
                        mortgage.getTotalInterestPaid(),
                        mortgage.getAnnualInterestRate(),
                        mortgage.getAnnualInsuranceRate()
                );

                resultArea.setText(result);

                // Ajout du résultat à l'historique
                historyList.add(result);
                updateHistory();
            }
        } catch (NumberFormatException e) {
            resultArea.setText("Erreur : Veuillez entrer des valeurs numériques valides.");
        } catch (SQLException e) {
            resultArea.setText("Erreur de connexion à la base de données : " + e.getMessage());
        } catch (IllegalArgumentException e) {
            resultArea.setText("Erreur : " + e.getMessage());
        }
    }

    private void resetFields() {
        principalField.setText("");
        downPaymentField.setText("");
        loanTermField.setText("");
        bankComboBox.setSelectedIndex(0);
        resultArea.setText("");
    }

    private void updateHistory() {
        StringBuilder historyText = new StringBuilder();
        for (String entry : historyList) {
            historyText.append(entry).append("\n");
        }
        historyArea.setText(historyText.toString());
    }

    public static void main(String[] args) {
        // Création et affichage de l'interface graphique
        SwingUtilities.invokeLater(() -> new AMC_GUI().setVisible(true));

    }
}