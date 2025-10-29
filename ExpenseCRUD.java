package exp;
import java.sql.*;
import java.util.Scanner;

public class ExpenseCRUD {
    Connection con;
    Scanner sc = new Scanner(System.in);

    ExpenseCRUD() {
        con = DBConnection.getConnection();
    }

    public void addExpense() {
        try {
            System.out.print("Enter Description: ");
            String desc = sc.nextLine();
            System.out.print("Enter Amount: ");
            double amount = sc.nextDouble();
            sc.nextLine(); // clear buffer
            System.out.print("Enter Date (YYYY-MM-DD): ");
            String date = sc.nextLine();

            PreparedStatement ps = con.prepareStatement("INSERT INTO expense(description, amount, date) VALUES(?,?,?)");
            ps.setString(1, desc);
            ps.setDouble(2, amount);
            ps.setString(3, date);
            ps.executeUpdate();
            System.out.println("✅ Expense Added Successfully!");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void viewExpenses() {
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM expense ORDER BY date DESC");
            System.out.println("\nID\tDescription\tAmount\tDate");
            System.out.println("-------------------------------------------");
            while (rs.next()) {
                System.out.println(rs.getInt(1) + "\t" + rs.getString(2) + "\t \t "  + rs.getDouble(3) + "\t" + rs.getDate(4));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void calculateTotal() {
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT SUM(amount) FROM expense");
            if (rs.next()) {
                double total = rs.getDouble(1);
                System.out.println("\n💵 Total Expense = ₹" + total);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void deleteExpense() {
        try {
            System.out.print("Enter Expense ID to Delete: ");
            int id = sc.nextInt();
            PreparedStatement ps = con.prepareStatement("DELETE FROM expense WHERE id=?");
            ps.setInt(1, id);
            int rows = ps.executeUpdate();

            if (rows > 0)
                System.out.println("✅ Expense Deleted Successfully!");
            else
                System.out.println("❌ Expense ID Not Found!");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        ExpenseCRUD obj = new ExpenseCRUD();
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n--- DAILY EXPENSE TRACKER ---");
            System.out.println("1. Add Expense");
            System.out.println("2. View All Expenses");
            System.out.println("3. Calculate Total Expense");
            System.out.println("4. Delete Expense");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt(); sc.nextLine();

            switch (choice) {
                case 1: obj.addExpense(); break;
                case 2: obj.viewExpenses(); break;
                case 3: obj.calculateTotal(); break;
                case 4: obj.deleteExpense(); break;
                case 5: System.out.println("Exiting..."); break;
                default: System.out.println("Invalid choice!");
            }
        } while (choice != 5);
    }
}