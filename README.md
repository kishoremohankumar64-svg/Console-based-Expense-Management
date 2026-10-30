# 💰 Expense Management System

## 📌 Project Description
The Expense Management System is a console-based Java application designed to manage employee expenses. It allows employees to submit expenses and managers to approve or reject them. Approved expenses can be reimbursed.

This project demonstrates Object-Oriented Programming (OOP) concepts using Java.

## ✨ Features
- Employee and manager registration
- Submit travel and meal expenses
- Approve or reject employee expenses
- Manager-based approval limits
- Expense reimbursement processing
- View expenses by status
- Calculate total expenses per employee
- Generate monthly expense summaries
- Validate expense amounts

## 🛠️ Technologies Used
- **Programming Language:** Java
- **Concepts:** Object-Oriented Programming (OOP)
- **Application Type:** Console-Based Application

## 📂 Project Structure

```text
ExpenseManager/
├── Employee.java
├── Manager.java
├── Expense.java
├── ExpenseService.java
└── ExpenseAppMain.java
```

## 🧠 OOP Concepts Used

### 1. Inheritance
The `Manager` class extends the `Employee` class.

### 2. Encapsulation
Private variables protect employee and expense information.

### 3. Method Overloading
Multiple `submit()` methods allow expenses to be submitted with different details.

### 4. Method Overriding
The `Manager` class overrides methods to implement manager-specific approval logic.

### 5. Polymorphism
Employee references can represent both employees and managers.

## ⚙️ Requirements
- Java JDK 8 or later
- Visual Studio Code, Eclipse, IntelliJ IDEA, or another Java IDE

## 🚀 How to Run

**Step 1:** Download or clone this repository.

**Step 2:** Open the project folder in your Java IDE.

**Step 3:** Open a terminal in the directory containing the `ExpenseManager` folder.

**Step 4:** Compile the Java files:

```bash
javac ExpenseManager/*.java
```

**Step 5:** Run the application:

```bash
java ExpenseManager.ExpenseAppMain
```

## 📊 Expected Output

The application demonstrates:

- Employee and manager details
- Expense submission
- Approval and rejection
- Reimbursement processing
- Expense reports by status
- Per-employee expense totals
- Monthly expense summaries

## 🔒 Validation Rules
- Expense amounts must be greater than zero.
- Regular employees cannot approve expenses.
- Managers cannot approve their own expenses.
- Approval limits depend on manager grade.
- Only approved expenses can be reimbursed.

## 🎯 Project Objective
The objective of this project is to demonstrate Java programming skills and OOP concepts by building a simple employee expense management application.
