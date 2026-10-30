package ExpenseManager;
public class Employee {
private int empId;
private String name;
private String dept;
private String email;
private static int totalEmployees = 0;
public Employee(int empId, String name, String dept, String email) {
this.empId = empId;
this.name = name;
this.dept = dept;
this.email = email;
totalEmployees++;
}
public int getEmpId() {
return empId;
}
public String getName() {
return name;
}
public String getDept() {
return dept;
}
public String getEmail() {
return email;
}
public void setName(String name) {
this.name = name;
}
public void setDept(String dept) {
this.dept = dept;
}
public void setEmail(String email) {
this.email = email;
}
public static int getTotalEmployees() {
return totalEmployees;
}
// Overridden by Manager. A normal employee has no approval power.
public double getApprovalLimit() {
return 0;
}
public String getRole() {
return "Employee";
}
// Overridden by Manager with grade based approval logic.
public boolean approve(Expense expense) {
System.out.println("  [DENIED] " + name + " is an Employee and cannot approve expenses.");
return false;
}
public void display() {
System.out.println("  " + getRole() + ": " + empId + " | " + name + " | " + dept + " | " + email);
}
@Override
public String toString() {
return empId + " - " + name + " (" + getRole() + ")";
}
}
