package ExpenseManager;
public class Manager extends Employee {
private double approvalLimit;
private int grade;                 // 1 = Junior, 2 = Senior, 3 = Director
private int teamSize;
private static int totalManagers = 0;
public Manager(int empId, String name, String dept, String email,
double approvalLimit, int grade, int teamSize) {
super(empId, name, dept, email);
this.approvalLimit = approvalLimit;
this.grade = grade;
this.teamSize = teamSize;
totalManagers++;
}
public void setApprovalLimit(double approvalLimit) {
this.approvalLimit = approvalLimit;
}
public int getGrade() {
return grade;
}
public void setGrade(int grade) {
this.grade = grade;
}
public int getTeamSize() {
return teamSize;
}
public void setTeamSize(int teamSize) {
this.teamSize = teamSize;
}
public static int getTotalManagers() {
return totalManagers;
}
// Effective limit depends on the manager grade.
@Override
public double getApprovalLimit() {
switch (grade) {
case 1:  return approvalLimit;
case 2:  return approvalLimit * 2;
default: return approvalLimit * 5;
}
}
@Override
public String getRole() {
return "Manager(Grade " + grade + ")";
}
// Method overriding: custom approve() logic by manager grade.
@Override
public boolean approve(Expense expense) {
if (expense.getStatus() != Expense.Status.SUBMITTED) {
System.out.println("  [DENIED] Expense " + expense.getExpenseId()
+ " is already " + expense.getStatus() + ".");
return false;
}
if (expense.getRequester().getEmpId() == getEmpId()) {
System.out.println("  [DENIED] " + getName() + " cannot approve own expense "
+ expense.getExpenseId() + ".");
return false;
}
if (expense.getAmount() > getApprovalLimit()) {
System.out.println("  [DENIED] Expense " + expense.getExpenseId() + " (Rs." + expense.getAmount()
+ ") exceeds " + getName() + "'s limit of Rs." + getApprovalLimit()
+ ". Escalate to a higher grade.");
return false;
}
expense.markApproved(this);
System.out.println("  [APPROVED] Expense " + expense.getExpenseId() + " approved by "
+ getName() + " (Grade " + grade + ").");
return true;
}
public boolean reject(Expense expense, String reason) {
if (expense.getStatus() != Expense.Status.SUBMITTED) {
System.out.println("  [DENIED] Expense " + expense.getExpenseId()
+ " is already " + expense.getStatus() + ".");
return false;
}
expense.markRejected(this, reason);
System.out.println("  [REJECTED] Expense " + expense.getExpenseId() + " rejected by "
+ getName() + ". Reason: " + reason);
return true;
}
@Override
public void display() {
System.out.println("  " + getRole() + ": " + getEmpId() + " | " + getName() + " | " + getDept()
+ " | " + getEmail() + " | Limit: Rs." + getApprovalLimit() + " | Team: " + teamSize);
}
}
