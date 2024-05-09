import java.util.Scanner;

import java.sql.*;

/**
 * Aspiras
 */
public class Aspiras {
  static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
  static final String db_url = "jdbc:mysql://localhost:3306/ipt";
  static final String username = "root";
  static final String password = "";

  public static void main(String[] args) {
    Ask();
  }

  private static void Ask(){
    Connection conn = null;
    Statement stmt = null;
    Scanner dataIn = new Scanner(System.in);

    try{
      Class.forName(JDBC_DRIVER);
      conn = DriverManager.getConnection(db_url, username, password);
      stmt = conn.createStatement();

      int choice = 0;
      while(choice != 5){
    
        System.out.println("┌────────────────────┐");
        System.out.println("│ Student Management │");
        System.out.println("│ By: Aspiras        │");
        System.out.println("┝────────────────────┤");
        System.out.println("│ 1. Add Student     │");
        System.out.println("│ 2. View Student    │");
        System.out.println("│ 3. Update Student  │");
        System.out.println("│ 4. Delete Student  │");
        System.out.println("│ 5. Exit            │");
        System.out.println("└──┰─────────────────┘");
        System.out.print("   └Enter your choice> ");
        choice = dataIn.nextInt();
  
        switch (choice) {
          case 1:   
            addStudent(conn,dataIn);       
            break;
          case 2:
            viewStudent(stmt);
            break;
          case 3:
            updateStudent(conn, dataIn);
            break;
          case 4:
            deleteStudent(dataIn,conn, stmt);
            break;
          case 5:
            dataIn.close();
            break;    
        
          default:
            System.out.println("Invalid Input!");
        }
      }

    }
    catch (Exception e) {
      e.printStackTrace();
    }  
  }

  private static void addStudent(Connection conn, Scanner dataIn) throws SQLException{
    System.out.println("[1. Add Student]");
    System.out.print("└> Enter ID Number: ");
    String idno = dataIn.next();
    dataIn.nextLine();
    
    System.out.print("└> Enter Firstname: ");
    String fname = dataIn.nextLine();

    System.out.print("└> Enter Lastname: ");
    String lname = dataIn.nextLine();

    String sql = "INSERT INTO ipt.students (idno, fname, lname) VALUES (?,?,?)";
    
    try(PreparedStatement pstmt = conn.prepareStatement(sql)){
      pstmt.setString(1, idno);
      pstmt.setString(2, fname);
      pstmt.setString(3, lname);
      pstmt.executeUpdate();
      System.out.println("[Student added Successfully]\n");      
    }
  }
  private static void viewStudent(Statement stmt) throws SQLException{
    System.out.println("[2. View Student]\n");

    String sql = "SELECT * FROM ipt.students";
    try(ResultSet resultSet = stmt.executeQuery(sql)){
      while (resultSet.next()) {
        System.out.println("[ID]: " + resultSet.getInt("ID") + "\n[ID Number]: " + resultSet.getString("idno") + "\n[First Name]: " + resultSet.getString("fname") + "\n[Last Name]: " + resultSet.getString("lname") + "\n");
      }
    }
    System.out.println("");
  }
  private static void updateStudent(Connection conn, Scanner dataIn) throws SQLException 
  {
    System.out.println("[3. Update Student]");
    System.out.print("└> Enter ID Number: ");
    String idno = dataIn.next();
    dataIn.nextLine();
    
    System.out.print("└> Enter Firstname: ");
    String fname = dataIn.nextLine();

    System.out.print("└> Enter Lastname: ");
    String lname = dataIn.nextLine();
    
    String sql = "UPDATE ipt.students SET fname = ?, lname = ? WHERE idno = ?";
    try(PreparedStatement pstmt = conn.prepareStatement(sql))
    {
      
      pstmt.setString(1, fname);
      pstmt.setString(2, lname);
      pstmt.setString(3, idno);
      int rowsAffected  =  pstmt.executeUpdate();
      if(rowsAffected > 0 )
      {
         System.out.println("[Input Successfully update]\n");
      }
      else 
      {
         System.out.println("Info Was not found in stud table ID: "+ idno);
      }
    }

  }
  private static void deleteStudent(Scanner dataIn, Connection conn, Statement stmt) throws SQLException{
    System.out.print("Enter ID Number: ");
    String idValue = dataIn.next();    
    String sql = "DELETE FROM ipt.students WHERE idno = '" + idValue + "'";
    // Execute the delete query
    int rowsAffected = stmt.executeUpdate(sql);
    System.out.println("[Rows deleted: " + rowsAffected + "]\n");
  }
}