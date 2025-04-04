CREATE DATABASE IF NOT EXISTS finance_monitor;
USE finance_monitor;

CREATE TABLE accounts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    balance DECIMAL(10,2) NOT NULL,
    initial_balance DECIMAL(10,2) NOT NULL
);

CREATE TABLE goals (
    id INT AUTO_INCREMENT PRIMARY KEY,
    goal TEXT NOT NULL,
    account_id INT NOT NULL,
    value DECIMAL(10,2) NOT NULL,

    FOREIGN KEY (account_id) REFERENCES accounts(id) ON DELETE CASCADE
);

CREATE TABLE transactions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    account_id INT NOT NULL,
    type ENUM('Household', 'Utilities', 'Shopping', 'Essentials', 'Transportation', 'Food', 'Health', 'Education', 'Entertainment', 'Other') NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    description TEXT,

    FOREIGN KEY (account_id) REFERENCES accounts(id) ON DELETE CASCADE
);

