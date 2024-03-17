package ONLINEMEDICALCONSULTANCY;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class patient {
    private Connection connection;
    private Scanner scanner;

    public patient(Connection connection,Scanner scanner)
    {
        this.connection= connection;
        this.scanner = scanner;
    }
   public void addPatient()
   {
       System.out.print("Enter patient name : ");
       String name = scanner.next();
       System.out.print("Enter patient Age : ");
       int age = scanner.nextInt();
       System.out.print("Enter patient Gender : ");
       String gender = scanner.next();

       try
       {
           String q  = "INSERT INTO patients(name, age , gender) VALUES(?,?,?) ";
           PreparedStatement pd = connection.prepareStatement(q);
           pd.setString(1,name);
           pd.setInt(2,age);
           pd.setString(3,gender);
           int effectrows = pd.executeUpdate();
           if(effectrows > 0 )
           {
               System.out.println("patient added successfully !!!");
           }
           else System.out.println("Failed to add patient...");

       }
       catch(SQLException e)
       {
           System.out.println(e);
       }
   }

   public void viewPatients()
   {
       String q1 = "SELECT * FROM patients";
       try
       {
           PreparedStatement ps = connection.prepareStatement(q1);
           ResultSet rs = ps.executeQuery();
           System.out.println("+------------+--------------+-------+--------+");
           System.out.println("| Patient Id |     Name     |  Age  | Gender |");
           System.out.println("+------------+--------------+-------+--------+");
           while(rs.next())
           {
               int id = rs.getInt("id");
               String name = rs.getString("name");
               int  age = rs.getInt("age");
               String gender = rs.getString("gender");
               System.out.printf("|%-12s|%-14s|%-7s|%-7s|\n",id,name,age,gender);
               System.out.println("+------------+--------------+-------+--------+");


           }

       }
       catch(SQLException e)
       {
           System.out.println(e);
       }
   }

   public boolean getPatientById(int id)
   {
       String q = "SELECT * FROM patients WHERE ID = ? ";

       try
       {
           PreparedStatement pd = connection.prepareStatement(q);
           pd.setInt(1,id);
           ResultSet rs = pd.executeQuery();
           if(rs.next())
           {
               return true;
           }
           else  return false;

       }
       catch(SQLException e)
       {
           System.out.println(e);
       }
       return false;
   }
}
