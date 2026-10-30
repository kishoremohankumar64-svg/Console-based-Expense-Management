package ExpenseManager;
import java.time.LocalDate;
import java.time.YearMonth;
public class ExpenseAppMain {
public static void main(String[] args) {
System.out.println("========== EXPENSE MANAGEMENT SYSTEM ==========");
ExpenseService service = new ExpenseService();
// Polymorphism: Employee references hold both Employee and Manager objects
Employee arun   = new Employee(101, "Arun",   "Engineering", "arun@company.com");
Employee priya  = new Employee(102, "Priya",  "Sales",       "priya@company.com");
Employee karthik = new Employee(103, "Karthik", "HR",        "karthik@company.com");
Employee meena  = new Manager(201, "Meena",  "Engineering", "meena@company.com", 5000, 1, 6);
Employee rajesh = new Manager(202, "Rajesh", "Finance",     "rajesh@company.com", 5000, 3, 12);
Employee[] staff = { arun, priya, karthik, meena, rajesh };
System.out.println("\n--- Employees and Managers ---");
for (Employee e : staff) {
service.registerEmployee(e);
e.display();                                    // overridden display() runs for Manager
}
System.out.println("  Total employees created: " + Employee.getTotalEmployees()
+ " (Managers: " + Manager.getTotalManagers() + ")");
System.out.println("\n--- Submitting Expenses (overloaded submit) ---");
Expense e1 = service.submit(arun, Expense.Type.TRAVEL, 4500, LocalDate.of(2026, 10, 5),
"Client visit to Bengaluru", "ticket.pdf", "CC-ENG-01");
Expense e2 = service.submit(arun, Expense.Type.MEALS, 800, LocalDate.of(2026, 10, 7),
"Team lunch");
Expense e3 = service.submit(priya, Expense.Type.TRAVEL, 12000, LocalDate.of(2026, 10, 12),
"Sales conference, Mumbai", "invoice.pdf", "CC-SAL-02");
Expense e4 = service.submit(karthik, Expense.Type.MEALS, 1500, LocalDate.of(2026, 10, 15),
"Interview panel dinner", "bill.jpg");
Expense e5 = service.submit(priya, Expense.Type.MEALS, 2200, LocalDate.of(2026, 9, 20),
"Customer dinner");
Expense e6 = service.submit(meena, Expense.Type.TRAVEL, 3000, LocalDate.of(2026, 10, 18),
"Training at Pune", "train.pdf");
System.out.println("\n--- Invalid Submission (encapsulation / validation) ---");
try {
service.submit(arun, Expense.Type.MEALS, -50, LocalDate.of(2026, 10, 20), "Invalid");
} catch (IllegalArgumentException ex) {
System.out.println("  [ERROR] " + ex.getMessage());
}
System.out.println("\n--- Approval (polymorphic approve) ---");
service.approve(e1, meena);       // Rs.4500 <= Rs.5000   : approved
service.approve(e2, meena);       // Rs.800  <= Rs.5000   : approved
service.approve(e3, meena);       // Rs.12000 > Rs.5000   : denied (limit)
service.approve(e3, rajesh);      // Rs.12000 <= Rs.25000 : approved (Grade 3)
service.approve(e6, meena);       // own expense          : denied
service.approve(e6, rajesh);      // approved by higher manager
service.approve(e5, arun);        // Employee has no approval rights
System.out.println("\n--- Rejection ---");
service.reject(e4, meena, "Dinner not pre-approved by HR head");
service.reject(e5, arun, "No rights");
System.out.println("\n--- Reimbursement ---");
service.reimburse(e1);
service.reimburse(e3);
service.reimburse(e4);            // rejected, so reimbursement fails
int paid = service.reimburseAllApproved();
System.out.println("  Remaining approved expenses paid: " + paid);
System.out.println("\n--- Reports by Status (listByStatus) ---");
for (Expense.Status s : Expense.Status.values()) {
service.printByStatus(s);
}
System.out.println();
service.printPerEmployeeTotals();
System.out.println();
service.printMonthlySummary(YearMonth.of(2026, 10));
System.out.println();
service.printMonthlySummary(YearMonth.of(2026, 9));
System.out.println("\n--- Overall Figures ---");
System.out.printf("  Total claimed    : Rs.%.2f%n", service.calculateTotal());
System.out.printf("  Total submitted  : Rs.%.2f%n", service.calculateTotal(Expense.Status.SUBMITTED));
System.out.printf("  Total reimbursed : Rs.%.2f%n", service.getTotalReimbursed());
System.out.println("  Expenses created : " + Expense.getTotalCreated());
System.out.println("\nExpense Management Completed!");
}
}
