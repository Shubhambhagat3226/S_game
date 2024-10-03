package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {
    private Connection connection;
    private Scanner input;

    public Patient(Connection connection, Scanner input) {
        this.connection = connection;
        this.input = input;
    }

    public void addPatient(){
        System.out.print("Enter Patient name: ");
        String name = input.next();
        System.out.print("Enter Patient age: ");
        int age = input.nextInt();
        System.out.print("Enter Patient gender : ");
        String gender = input.next();

        try {
            String query = "INSERT INTO patients(name, age, gender) VALUES(?, ?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            preparedStatement.setString(3, gender);

            int affectedRow = preparedStatement.executeUpdate();

            if(affectedRow > 0){
                System.out.println("Patient detail add successfully!!");
            } else {
                System.out.println("Failed to add detail...");
            }

        } catch (SQLException e) {
            e.getStackTrace();
        }
    }

    public void viewPatients(){
        String query = "SELECT * FROM patients;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            System.out.println("Patients:");
            System.out.println("+------------+--------------------+---------+--------------+");
            System.out.println("| Patient Id |        Name        |   Age   |    Gender    |");
            System.out.println("+------------+--------------------+---------+--------------+");
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String gender = resultSet.getString("gender");
                System.out.printf("| %-10s | %-18s | %-7s | %-12s |\n", id, name, age, gender);
                System.out.println("+------------+--------------------+---------+--------------+");
            }

        } catch (SQLException e) {
            e.getStackTrace();
        }
    }


    public boolean getPatientById(int id){

        try {
            String query = "Select * FROM patients WHERE id = ?";
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
