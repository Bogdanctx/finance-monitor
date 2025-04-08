# 💸 Finance Monitor

A console-based personal finance tracker built in **Java**, designed to help users manage accounts, transactions, and financial goals. The app uses a **MySQL database (via JDBC)** for persistence and logs user actions in a CSV audit file.

---

## 📌 Features

- 🔐 **Account Management**: Add, view, and remove bank accounts.
- 🧾 **Transaction Tracking**: Record income and expenses with category support.
- 🎯 **Goal Monitoring**: Set financial goals and monitor progress.
- 📊 **Statistics**: View summaries and breakdowns of spendings.
- 🗂️ **Audit Logging**: All actions are logged to a CSV file with timestamps.
- 💾 **Database Persistence**: Uses MySQL and JDBC for data storage.
- 🧱 **Modular Design**: Clean, maintainable architecture using OOP and design patterns.

---

## 🛠️ Technologies Used

- **Java 17+**
- **MySQL**
- **JDBC**
- **CSV File Handling**

---


---

## 🚀 Getting Started

### 1. Install Dependencies

- Java (17 or higher)
- MySQL

### 2. Set Up Database

Run the SQL script named `finance_monitor.sql`.

Open the file `database_conf.properties`

### 📄 `database_conf.properties`
```properties
url=jdbc:mysql://localhost:3306/finance_monitor     < change with your own url
user=finance_monitor                                < change with your own user
password=root                                       < change with your own password
