package ONLINEMEDICALCONSULTANCY;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class doctors {
    private Connection connection;


    public doctors(Connection connection)
    {
        this.connection= connection;
    }

    public void viewDoctors(){
        String query = "select * from doctor";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Doctors: ");
            System.out.println("+------------+--------------------+------------------+");
            System.out.println("| Doctor Id  | Name               | Specialization   |");
            System.out.println("+------------+--------------------+------------------+");
            while(resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String specialization = resultSet.getString("specialization");
                System.out.printf("| %-10s | %-18s | %-16s |\n", id, name, specialization);
                System.out.println("+------------+--------------------+------------------+");
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public boolean getdoctorById(int Id)
    {
        String q = "SELECT * FROM doctor WHERE id = ? ";

        try
        {
            PreparedStatement pd = connection.prepareStatement(q);
            pd.setInt(1,Id);
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
