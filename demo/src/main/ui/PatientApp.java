package main.ui;

import java.util.Scanner;

import main.service.PatientService;
import main.service.AppointmentService;

public class PatientApp {
    private PatientUI patientUI;
    private AppointmentUI appointmentUI;
    private Scanner scanner = new Scanner(System.in);

    private static final int ENTER_PATIENT_MENU = 1;
    private static final int ENTER_APPOINTMENT_MENU = 2;
    private static final int EXIT_MAIN_MENU = 3;

    public PatientApp(PatientService patientService, AppointmentService appointmentService) {
        this.patientUI = new PatientUI(patientService);
        this.appointmentUI = new AppointmentUI(appointmentService);
    }

    public void start() {
        while (true) {
            System.out.println("\nPatient Management System:");
            System.out.println("1. Enter patient menu");
            System.out.println("2. Enter appointment menu");
            System.out.println("3. Exit");
            System.out.println(" ");
            System.out.println("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine();
            switch (option) {
                case ENTER_PATIENT_MENU:
                    patientUI.managePatients();
                    break;
                case ENTER_APPOINTMENT_MENU:
                    appointmentUI.manageAppointments();
                    break;
                case EXIT_MAIN_MENU:
                    System.out.println("Exit!");
                    return;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }
}