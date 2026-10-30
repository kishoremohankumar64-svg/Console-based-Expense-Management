package ExpenseManager;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
public class ExpenseService {
private List<Expense> expenses = new ArrayList<>();
private Map<Integer, Employee> employees = new LinkedHashMap<>();
private double totalReimbursed = 0;
private static int nextExpenseId = 1001;
public void registerEmployee(Employee e) {
employees.put(e.getEmpId(), e);
}
public List<Expense> getExpenses() {
return new ArrayList<>(expenses);     // copy: callers cannot modify the real list
}
public double getTotalReimbursed() {
return totalReimbursed;
}
// ---------- Method overloading: submit() ----------
public Expense submit(Employee emp, Expense.Type type, double amount,
LocalDate date, String description) {
return addExpense(new Expense(nextExpenseId++, type, amount, emp, date, description));
}
public Expense submit(Employee emp, Expense.Type type, double amount,
LocalDate date, String description, String attachment) {
return addExpense(new Expense(nextExpenseId++, type, amount, emp, date,
description, attachment, "GENERAL"));
}
public Expense submit(Employee emp, Expense.Type type, double amount,
LocalDate date, String description, String attachment, String costCenter) {
return addExpense(new Expense(nextExpenseId++, type, amount, emp, date,
description, attachment, costCenter));
}
private Expense addExpense(Expense e) {
expenses.add(e);
System.out.println("  [SUBMITTED] EXP-" + e.getExpenseId() + " by " + e.getRequester().getName()
+ " | " + e.getType() + " | Rs." + e.getAmount()
+ " | Attachment: " + e.getAttachment() + " | Cost Centre: " + e.getCostCenter());
return e;
}
// ---------- Approval / rejection (polymorphic call on Employee reference) ----------
public boolean approve(Expense expense, Employee approver) {
return approver.approve(expense);       // Employee or Manager version runs at runtime
}
public boolean reject(Expense expense, Employee approver, String reason) {
if (approver instanceof Manager) {
return ((Manager) approver).reject(expense, reason);
}
System.out.println("  [DENIED] " + approver.getName() + " is an Employee and cannot reject expenses.");
return false;
}
// ---------- Reimbursement ----------
public boolean reimburse(Expense expense) {
if (expense.markReimbursed()) {
totalReimbursed += expense.getAmount();
System.out.println("  [REIMBURSED] EXP-" + expense.getExpenseId() + " Rs." + expense.getAmount()
+ " paid to " + expense.getRequester().getName());
return true;
}
System.out.println("  [DENIED] EXP-" + expense.getExpenseId() + " is " + expense.getStatus()
+ ", only APPROVED expenses can be reimbursed.");
return false;
}
public int reimburseAllApproved() {
int count = 0;
for (Expense e : expenses) {
if (e.getStatus() == Expense.Status.APPROVED && reimburse(e)) {
count++;
}
}
return count;
}
// ---------- Reports ----------
public List<Expense> listByStatus(Expense.Status status) {
List<Expense> result = new ArrayList<>();
for (Expense e : expenses) {
if (e.getStatus() == status) {
result.add(e);
}
}
return result;
}
public void printByStatus(Expense.Status status) {
System.out.println("--- Expenses with status " + status + " ---");
List<Expense> list = listByStatus(status);
if (list.isEmpty()) {
System.out.println("  (none)");
}
for (Expense e : list) {
e.display();
}
}
// calculateTotal() overloaded: per employee, per status, or everything
public double calculateTotal(Employee emp) {
double total = 0;
for (Expense e : expenses) {
if (e.getRequester().getEmpId() == emp.getEmpId()) {
total += e.getAmount();
}
}
return total;
}
public double calculateTotal(Expense.Status status) {
double total = 0;
for (Expense e : listByStatus(status)) {
total += e.getAmount();
}
return total;
}
public double calculateTotal() {
double total = 0;
for (Expense e : expenses) {
total += e.getAmount();
}
return total;
}
public void printPerEmployeeTotals() {
System.out.println("--- Per-Employee Totals ---");
for (Employee emp : employees.values()) {      // Employee refs hold both Employee and Manager
System.out.printf("  %-10s (%-17s) : Rs.%9.2f%n",
emp.getName(), emp.getRole(), calculateTotal(emp));
}
}
public void printMonthlySummary(YearMonth month) {
double total = 0, travel = 0, meals = 0;
int count = 0;
for (Expense e : expenses) {
if (YearMonth.from(e.getDate()).equals(month)) {
count++;
total += e.getAmount();
if (e.getType() == Expense.Type.TRAVEL) {
travel += e.getAmount();
} else {
meals += e.getAmount();
}
}
}
System.out.println("--- Monthly Summary: " + month + " ---");
System.out.println("  Expenses : " + count);
System.out.printf("  Travel   : Rs.%.2f%n", travel);
System.out.printf("  Meals    : Rs.%.2f%n", meals);
System.out.printf("  Total    : Rs.%.2f%n", total);
}
}
