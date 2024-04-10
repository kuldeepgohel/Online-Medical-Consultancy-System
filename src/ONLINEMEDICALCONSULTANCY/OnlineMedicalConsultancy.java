package ONLINEMEDICALCONSULTANCY;

import java.sql.*;
import java.util.Scanner;

public class OnlineMedicalConsultancy {
    private static final String url = "jdbc:mysql://localhost:3306/hospital";
    private static final String username = "root";
    private static final String password = "K6h1g2004";

    public static void main(String[] args) {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch(ClassNotFoundException e)
        {
            System.out.println(e);
        }
        Scanner sc = new Scanner(System.in);
        try
        {
            Connection cn = DriverManager.getConnection(url,username,password);
            patient patient  = new patient(cn,sc);
            doctors doctor = new doctors(cn);

            while(true)
            {
                System.out.println("ONLINE MEDICAL CONSULTANCY SYSTEM");
                System.out.println("1.Add Patient");
                System.out.println("2.view Patient ");
                System.out.println("3.view Doctor ");
                System.out.println("4.Book Appointment");
                System.out.println("5.Exit");
                System.out.println("Enter your choice:-");

                int c = sc.nextInt();
                switch (c) {
                    case 1 -> {
                        //add patient
                        patient.addPatient();
                        System.out.println();
                    }
                    case 2 -> {
                        //view patient
                        patient.viewPatients();
                        System.out.println();
                    }
                    case 3 -> {
                        //view Doctor
                        doctor.viewDoctors();
                        System.out.println();
                    }
                    case 4 -> {
                        //Book Appointment
                        bookAppointment(patient, doctor, cn, sc);
                        System.out.println();
                    }
                    case 5 -> {
                        //Exit
                        return;
                    }
                    default -> System.out.println("enter legal choice...");
                }
            }



        }catch(SQLException e)
        {
            System.out.println(e);
        }
    }
          public static void bookAppointment(patient patient,doctors doctor, Connection connection,Scanner scanner)
          {
              System.out.println("Enter Patient Id:");
              int patientId= scanner.nextInt();
              System.out.println("Enter Doctor Id:");
              int doctorId = scanner.nextInt();
              System.out.println("Enter Appointment Date(YYYY/MM/DD)");
              String date = scanner.next();
              if(patient.getPatientById(patientId) && doctor.getdoctorById(doctorId)){
                  if(checkDoctoraVailibility(doctorId,date,connection))
                  {
                     String appointq = "INSERT INTO appointments(patient_id,doctor_id,appointment_date)values(?,?,?)";
                     try
                     {
                         PreparedStatement ps = connection.prepareStatement(appointq);
                         ps.setInt(1,patientId);
                         ps.setInt(2,doctorId);
                         ps.setString(3,date);

                         int rows =ps.executeUpdate();
                         if(rows > 0)
                         {
                             System.out.println("Appointment Booked!");
                         }
                         else {

                             System.out.println("Field to book appointment..");
                         }

                     }catch(SQLException e)
                     {
                         System.out.println(e);
                     }

                  }
                  else {
                      System.out.println("doctor not available on this date...");
                  }
              }
              else {
                  System.out.println("Either doctor or patient doesn't exist!!");
              }

          }
          public static boolean checkDoctoraVailibility(int doctorId,String appointment_date,Connection connection)
          {
              String q = " SELECT COUNT(*) FROM appointments WHERE doctor_id=? AND appointment_date = ?";
              try
              {
                  PreparedStatement ps = connection.prepareStatement(q);
                  ps.setInt(1,doctorId);
                  ps.setString(2,appointment_date);
                  ResultSet rs = ps.executeQuery();
                  if(rs.next())
                  {
                      int count = rs.getInt(1);
                      return count == 0;
                  }


              } catch (SQLException e) {
                  System.out.println(e);
              }
              return false;
          }
}
