package ExpenseManager;
import java.time.LocalDate;
public class Expense {
public enum Type { TRAVEL, MEALS }
public enum Status { SUBMITTED, APPROVED, REJECTED, REIMBURSED }
private int expenseId;
private Type type;
private double amount;             // no setter: amount cannot be edited after submission
private Status status;             // changed only through the package-private methods below
private Employee requester;
private LocalDate date;
private String description;
private String attachment;
private String costCenter;
private Employee approvedBy;
private String remarks;
private static int totalCreated = 0;
public Expense(int expenseId, Type type, double amount, Employee requester,
LocalDate date, String description) {
this(expenseId, type, amount, requester, date, description, "NONE", "GENERAL");
}
public Expense(int expenseId, Type type, double amount, Employee requester,
LocalDate date, String description, String attachment, String costCenter) {
if (amount <= 0) {
throw new IllegalArgumentException("Amount must be greater than zero.");
}
this.expenseId = expenseId;
this.type = type;
this.amount = amount;
this.requester = requester;
this.date = date;
this.description = description;
this.attachment = attachment;
this.costCenter = costCenter;
this.status = Status.SUBMITTED;
this.remarks = "";
totalCreated++;
}
public int getExpenseId() {
return expenseId;
}
public Type getType() {
return type;
}
public double getAmount() {
return amount;
}
public Status getStatus() {
return status;
}
public Employee getRequester() {
return requester;
}
public LocalDate getDate() {
return date;
}
public String getDescription() {
return description;
}
public String getAttachment() {
return attachment;
}
public String getCostCenter() {
return costCenter;
}
public Employee getApprovedBy() {
return approvedBy;
}
public String getRemarks() {
return remarks;
}
public static int getTotalCreated() {
return totalCreated;
}
// Controlled state changes (package-private, only ExpenseService / Manager can call).
void markApproved(Employee approver) {
if (status == Status.SUBMITTED) {
status = Status.APPROVED;
approvedBy = approver;
}
}
void markRejected(Employee approver, String reason) {
if (status == Status.SUBMITTED) {
status = Status.REJECTED;
approvedBy = approver;
remarks = reason;
}
}
boolean markReimbursed() {
if (status == Status.APPROVED) {
status = Status.REIMBURSED;
return true;
}
return false;
}
public void display() {
System.out.printf("  EXP-%d | %-6s | Rs.%9.2f | %-10s | %s | %s | %s%n",
expenseId, type, amount, status, requester.getName(), date, description);
}
}
