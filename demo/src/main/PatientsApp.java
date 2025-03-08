package main;

import main.domain.Patient;
import main.domain.Appointment;
import main.repository.IRepository;
import main.repository.RepositoryDentist;
import main.service.PatientService;
import main.service.AppointmentService;
import main.ui.PatientApp;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PatientsApp {
    public static void main(String[] args) {
        Properties properties = new Properties();

        try (FileInputStream input = new FileInputStream("settings.properties")) {
            properties.load(input);
        } catch (IOException e) {
            System.err.println("Error loading properties file: " + e.getMessage());
            return;
        }

        String repositoryType = properties.getProperty("Repository", "inmemory");
        String patientsFileOrUrl = properties.getProperty("Patients", "patients.txt");
        String appointmentsFileOrUrl = properties.getProperty("Appointments", "appointments.txt");

        if (repositoryType.equalsIgnoreCase("database")) {
            patientsFileOrUrl = properties.getProperty("Location", "jdbc:sqlite:/Users/tudorsabau/IdeaProjects/a5-2024-tudcell/demo/src/data/dentists.db");
            appointmentsFileOrUrl = patientsFileOrUrl;
        }

        IRepository<Integer, Patient<Integer>> patientRepository = RepositoryDentist.createPatientRepository(repositoryType, patientsFileOrUrl);
        IRepository<Integer, Appointment<Integer>> appointmentRepository = RepositoryDentist.createAppointmentRepository(repositoryType, appointmentsFileOrUrl);

        PatientService patientService = new PatientService(patientRepository);
        AppointmentService appointmentService = new AppointmentService(appointmentRepository, patientRepository);

        if (repositoryType.equalsIgnoreCase("inmemory")) {
            patientService.createPatient(1, "John Doe", 30);
            patientService.createPatient( 2,"Jane Smith", 25);
            patientService.createPatient(3, "Alice Johnson", 40);
            patientService.createPatient(4, "Bob Brown", 35);
            patientService.createPatient( 5,"Charlie Davis", 50);

            appointmentService.createAppointment(1, 1, "2023-10-01", "scheduled");
            appointmentService.createAppointment(2, 2, "2023-10-02", "cancelled");
            appointmentService.createAppointment(3, 3, "2023-10-03", "finished");
            appointmentService.createAppointment(4, 4, "2023-10-04", "scheduled");
            appointmentService.createAppointment(5, 5, "2023-10-05", "finished");
        }

        PatientApp patientApp = new PatientApp(patientService, appointmentService);
        patientApp.start();
    }
}