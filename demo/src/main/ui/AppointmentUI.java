package main.ui;

import main.service.AppointmentService;
import main.domain.Appointment;

import java.util.List;
import java.util.Scanner;

public class AppointmentUI {
    private final AppointmentService appointmentService;
    private final Scanner scanner;

    private static final int CREATE_APPOINTMENT = 1;
    private static final int VIEW_ALL_APPOINTMENTS = 2;
    private static final int CANCEL_APPOINTMENT = 3;
    private static final int FINISH_APPOINTMENT = 4;
    private static final int DELETE_APPOINTMENT = 5;
    private static final int FILTER_BY_STATUS = 6;
    private static final int FILTER_BY_DATE = 7;
    private static final int FILTER_BY_PATIENT = 8;
    private static final int FILTER_BY_PATIENT_AND_DATE = 9;
    private static final int FILTER_BY_PATIENT_AND_STATUS = 10;
    private static final int BACK_MAIN_MENU = 11;

    public AppointmentUI(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
        this.scanner = new Scanner(System.in);
    }

    public void manageAppointments() {
        boolean exit = false;
        while (!exit) {
            System.out.println("\nAppointment menu:");
            System.out.println("1. Create an appointment");
            System.out.println("2. View all appointments");
            System.out.println("3. Cancel an appointment");
            System.out.println("4. Finish an appointment");
            System.out.println("5. Delete an appointment");
            System.out.println("6. Filter appointments by status");
            System.out.println("7. Filter appointments by date");
            System.out.println("8. Filter appointments by patient");
            System.out.println("9. Filter appointments by patient and date");
            System.out.println("10. Filter appointments by patient and status");
            System.out.println("11. Back to main menu");
            System.out.println(" ");

            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case CREATE_APPOINTMENT:
                    createAppointment();
                    break;
                case VIEW_ALL_APPOINTMENTS:
                    viewAllAppointments();
                    break;
                case CANCEL_APPOINTMENT:
                    cancelAppointment();
                    break;
                case FINISH_APPOINTMENT:
                    finishAppointment();
                    break;
                case DELETE_APPOINTMENT:
                    deleteAppointment();
                    break;
                case FILTER_BY_STATUS:
                    filterAppointmentsByStatus();
                    break;
                case FILTER_BY_DATE:
                    filterAppointmentsByDate();
                    break;
                case FILTER_BY_PATIENT:
                    filterAppointmentsByPatient();
                    break;
                case FILTER_BY_PATIENT_AND_DATE:
                    filterAppointmentsByPatientAndDate();
                    break;
                case FILTER_BY_PATIENT_AND_STATUS:
                    filterAppointmentsByPatientAndStatus();
                    break;
                case BACK_MAIN_MENU:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }

    private void createAppointment() {
        System.out.print("Enter appointment ID: ");
        Integer id = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter patient ID: ");
        Integer patientId = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter appointment date: ");
        String date = scanner.nextLine();
        System.out.print("Enter appointment status (scheduled/cancelled/finished): ");
        String status = scanner.nextLine();

        try {
            appointmentService.createAppointment(id, patientId, date, status);
            System.out.println("Appointment created successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error creating appointment: " + e.getMessage());
        }
    }

    private void viewAllAppointments() {
        appointmentService.getAllAppointments().forEach(System.out::println);
    }

    private void cancelAppointment() {
        System.out.print("Enter the ID of the appointment you want to cancel: ");
        Integer appointmentId = Integer.parseInt(scanner.nextLine());
        try {
            appointmentService.cancelAppointment(appointmentId);
            System.out.println("Appointment cancelled successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error cancelling appointment: " + e.getMessage());
        }
    }

    private void finishAppointment() {
        System.out.print("Enter the ID of the appointment you want to finish: ");
        Integer appointmentId = Integer.parseInt(scanner.nextLine());
        try {
            appointmentService.finishAppointment(appointmentId);
            System.out.println("Appointment finished successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error finishing appointment: " + e.getMessage());
        }
    }

    private void deleteAppointment() {
        System.out.print("Enter the ID of the appointment you want to delete: ");
        Integer appointmentId = Integer.parseInt(scanner.nextLine());
        try {
            appointmentService.deleteAppointment(appointmentId);
            System.out.println("Appointment deleted successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error deleting appointment: " + e.getMessage());
        }
    }

    private void filterAppointmentsByStatus() {
        System.out.print("Enter appointment status (scheduled/cancelled/finished): ");
        String status = scanner.nextLine();
        List<Appointment<Integer>> appointments = appointmentService.getAppointmentsByStatus(status);
        appointments.forEach(System.out::println);
    }

    private void filterAppointmentsByDate() {
        System.out.print("Enter appointment date: ");
        String date = scanner.nextLine();
        List<Appointment<Integer>> appointments = appointmentService.getAppointmentsByDate(date);
        appointments.forEach(System.out::println);
    }

    private void filterAppointmentsByPatient() {
        System.out.print("Enter patient ID: ");
        Integer patientId = Integer.parseInt(scanner.nextLine());
        List<Appointment<Integer>> appointments = appointmentService.getAppointmentsForPatient(patientId);
        appointments.forEach(System.out::println);
    }

    private void filterAppointmentsByPatientAndDate() {
        System.out.print("Enter patient ID: ");
        Integer patientId = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter appointment date: ");
        String date = scanner.nextLine();
        List<Appointment<Integer>> appointments = appointmentService.getAppointmentsForPatientByDate(patientId, date);
        appointments.forEach(System.out::println);
    }

    private void filterAppointmentsByPatientAndStatus() {
        System.out.print("Enter patient ID: ");
        Integer patientId = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter appointment status (scheduled/cancelled/finished): ");
        String status = scanner.nextLine();
        List<Appointment<Integer>> appointments = appointmentService.getAppointmentsForPatientByStatus(patientId, status);
        appointments.forEach(System.out::println);
    }
}