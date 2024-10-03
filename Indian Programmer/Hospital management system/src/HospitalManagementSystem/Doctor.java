package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Doctor {
    private Connection connection;

    public Doctor(Connection connection) {
        this.connection = connection;
    }

    public void viewDoctors(){
        String query = "SELECT * FROM doctors;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            System.out.println("Doctors:");
            System.out.println("+-------------+--------------------+-----------------------+");
            System.out.println("|  Doctor Id  |        Name        |     Specialization    |");
            System.out.println("+-------------+--------------------+-----------------------+");
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String specialization = resultSet.getString("specialization");
                System.out.printf("| %-11s | %-18s | %-21s |\n", id, name, specialization);
                System.out.println("+-------------+--------------------+-----------------------+");
            }

        } catch (SQLException e) {
            e.getStackTrace();
        }
    }


    public boolean getDoctorById(int id){

        try {
            String query = "Select * FROM doctors WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return true;
            }
        } catch (SQLException e) {
            e.getStackTrace();
        }
        return false;
    }


}