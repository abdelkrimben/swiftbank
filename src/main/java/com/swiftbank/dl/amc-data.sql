INSERT INTO Bank (name) VALUES
                            ('Royal Bank of Canada'),
                            ('Toronto-Dominion Bank'),
                            ('Scotiabank'),
                            ('Bank of Montreal'),
                            ('Canadian Imperial Bank of Commerce'),
                            ('National Bank of Canada'),
                            ('Desjardins Group');

INSERT INTO Product (bank_id, name, type, description, interest_rate) VALUES
                                                                          (1, '30-Year Fixed Mortgage', 'Mortgage', 'A fixed-rate mortgage with a term of 30 years.', 3.75),
                                                                          (2, '15-Year Fixed Mortgage', 'Mortgage', 'A fixed-rate mortgage with a term of 15 years.', 3.25),
                                                                          (3, 'Adjustable Rate Mortgage', 'Mortgage', 'A mortgage with an interest rate that may change periodically.', 3.50),
                                                                          (4, 'Interest Only Mortgage', 'Mortgage', 'A mortgage where the borrower pays only the interest for a set period.', 4.00),
                                                                          (5, 'FHA Loan', 'Mortgage', 'A government-backed mortgage with lower down payment requirements.', 3.85),
                                                                          (6, 'Checking Account', 'Checking Account', 'An account for everyday transactions.', 0.01),
                                                                          (7, 'Home Equity Line of Credit', 'Mortgage', 'A line of credit secured by the equity in your home.', 4.25);
