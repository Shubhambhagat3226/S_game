package HospitalManagementSystem;


import java.sql.*;
import java.util.Scanner;

public class HospitalManagementSystem {
    private static final String url = "jdbc:mysql://127.0.0.1:100/hospital";
    private static final String username = "root";
    private static final String password = "3226";

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Scanner input = new Scanner(System.in);
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Patient patient = new Patient(connection, input);
            Doctor doctor = new Doctor(connection);

            while (true){
                System.out.println("HOSPITAL MANAGEMENT SYSTEM");
                System.out.println("1. Add Patient");
                System.out.println("2. View Patients");
                System.out.println("3. View Doctors");
                System.out.println("4. Book Appointment");
                System.out.println("5. Exit");

                System.out.print("Enter your choice: ");
                int choice = input.nextInt();

                switch (choice) {
                    case 1:
                        //ADD PATIENT
                        patient.addPatient();
                        System.out.println();
                        break;
                    case 2:
                        //VIEW PATIENTS
                        patient.viewPatients();
                        System.out.println();
                        break;
                    case 3:
                        //VIEW DOCTORS
                        doctor.viewDoctors();
                        System.out.println();
                        break;
                    case 4:
                        // BOOK APPOINTMENT
                        bookAppointment(patient, doctor, connection, input);
                        System.out.println();
                        break;
                    case 5:
                        //EXIT
                        System.out.println("THANK YOU! FOR USING HOSPITAL MANAGEMENT SYSTEM!!");
                        return;
                    default:
                        System.out.println("Enter valid choice!!!");
                        System.out.println();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void bookAppointment(Patient patient, Doctor doctor, Connection connection, Scanner scanner) {
        System.out.print("Patient id: ");
        int patient_id = scanner.nextInt();
        System.out.print("Doctor id: ");
        int doctor_id = scanner.nextInt();
        System.out.print("Enter appointment date (YYYY-MM-DD): ");
        String appointmentDate = scanner.next();

        if(patient.getPatientById(patient_id) && doctor.getDoctorById(doctor_id)){
            if (checkDoctorAvailable(doctor_id, appointmentDate, connection)){
                String appointmentQuery = "INSERT INTO appointments(patient_id, doctor_id, appointment_date) VALUES(?, ?, ?)";
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(appointmentQuery);
                    preparedStatement.setInt(1, patient_id);
                    preparedStatement.setInt(2, doctor_id);
                    preparedStatement.setString(3, appointmentDate);

                    int rowAffected = preparedStatement.executeUpdate();
                    if (rowAffected > 0) {
                        System.out.println("Appointment Booked!");
                    } else {
                        System.out.println("Failed to Book Appointment!");
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Doctor is not available on this date!!");
            }

        } else {
            System.out.println("Either doctor or Patient doesn't exist");
        }
    }

    public static boolean checkDoctorAvailable(int doctor_id, String appointmentDate, Connection connection){
        String query = "SELECT COUNT(*) FROM appointments WHERE doctor_id = ? AND appointment_date = ?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, doctor_id);
            preparedStatement.setString(2, appointmentDate);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                int count = resultSet.getInt(1);
                if (count == 0){
                    return true;
                } else {
                    return false;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
