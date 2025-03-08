package GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import main.domain.Patient;
import main.domain.Appointment;
import main.service.PatientService;
import main.service.AppointmentService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class PatientAppController {
    @FXML
    private ListView<Patient<Integer>> patientListView;
    @FXML
    private ListView<Appointment<Integer>> appointmentListView;

    private PatientService patientService;
    private AppointmentService appointmentService;

    public void initialize(PatientService patientService, AppointmentService appointmentService) {
        this.patientService = patientService;
        this.appointmentService = appointmentService;
        loadPatients();
        loadAppointments();
    }

    private void loadPatients() {
        List<Patient<Integer>> patients = StreamSupport.stream(patientService.getAllPatients().spliterator(), false)
                .collect(Collectors.toList());
        patientListView.getItems().setAll(patients);
    }

    private void loadAppointments() {
        List<Appointment<Integer>> appointments = StreamSupport.stream(appointmentService.getAllAppointments().spliterator(), false)
                .collect(Collectors.toList());
        appointmentListView.getItems().setAll(appointments);
    }

    @FXML
    private void handleAddPatient() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Patient");
        dialog.setHeaderText("Enter patient details");

        dialog.setContentText("ID:");
        Optional<String> idResult = dialog.showAndWait();
        if (idResult.isEmpty()) return;
        Integer id;
        try {
            id = Integer.parseInt(idResult.get());
        } catch (NumberFormatException e) {
            showAlert("Invalid ID", "ID must be a number.");
            return;
        }

        dialog.setContentText("Name:");
        Optional<String> nameResult = dialog.showAndWait();
        if (nameResult.isEmpty()) return;
        String name = nameResult.get();

        dialog.setContentText("Age:");
        Optional<String> ageResult = dialog.showAndWait();
        if (ageResult.isEmpty()) return;
        int age;
        try {
            age = Integer.parseInt(ageResult.get());
        } catch (NumberFormatException e) {
            showAlert("Invalid Age", "Age must be a number.");
            return;
        }

        patientService.createPatient(id, name, age);
        loadPatients();
    }

    @FXML
    private void handleUpdatePatient() {
        Patient<Integer> selectedPatient = patientListView.getSelectionModel().getSelectedItem();
        if (selectedPatient == null) {
            showAlert("No Selection", "Please select a patient in the list.");
            return;
        }

        TextInputDialog dialog = new TextInputDialog(selectedPatient.getName());
        dialog.setTitle("Update Patient");
        dialog.setHeaderText("Update patient details");

        dialog.setContentText("Name:");
        Optional<String> nameResult = dialog.showAndWait();
        if (nameResult.isEmpty()) return;
        String name = nameResult.get();

        dialog.setContentText("Age:");
        Optional<String> ageResult = dialog.showAndWait();
        if (ageResult.isEmpty()) return;
        int age;
        try {
            age = Integer.parseInt(ageResult.get());
        } catch (NumberFormatException e) {
            showAlert("Invalid Age", "Age must be a number.");
            return;
        }

        Patient<Integer> updatedPatient = new Patient<>(selectedPatient.getId(), name, age);
        patientService.updatePatient(updatedPatient);
        loadPatients();
    }

    @FXML
    private void handleDeletePatient() {
        Patient<Integer> selectedPatient = patientListView.getSelectionModel().getSelectedItem();
        if (selectedPatient == null) {
            showAlert("No Selection", "Please select a patient in the list.");
            return;
        }

        patientService.deletePatient(selectedPatient.getId());
        loadPatients();
    }

    @FXML
    private void handleAddAppointment() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Appointment");
        dialog.setHeaderText("Enter appointment details");

        dialog.setContentText("ID:");
        Optional<String> idResult = dialog.showAndWait();
        if (idResult.isEmpty()) return;
        Integer id;
        try {
            id = Integer.parseInt(idResult.get());
        } catch (NumberFormatException e) {
            showAlert("Invalid ID", "ID must be a number.");
            return;
        }

        dialog.setContentText("Patient ID:");
        Optional<String> patientIdResult = dialog.showAndWait();
        if (patientIdResult.isEmpty()) return;
        Integer patientId;
        try {
            patientId = Integer.parseInt(patientIdResult.get());
        } catch (NumberFormatException e) {
            showAlert("Invalid Patient ID", "Patient ID must be a number.");
            return;
        }

        dialog.setContentText("Date (YYYY-MM-DD):");
        Optional<String> dateResult = dialog.showAndWait();
        if (dateResult.isEmpty()) return;
        String date = dateResult.get();

        dialog.setContentText("Status:");
        Optional<String> statusResult = dialog.showAndWait();
        if (statusResult.isEmpty()) return;
        String status = statusResult.get();
        if (!status.equals("scheduled") && !status.equals("cancelled") && !status.equals("finished")) {
            showAlert("Invalid Status", "The status must be one of the following: scheduled, cancelled, or finished.");
            return;
        }

        appointmentService.createAppointment(id, patientId, date, status);
        loadAppointments();
    }

    @FXML
    private void handleUpdateAppointment() {
        Appointment<Integer> selectedAppointment = appointmentListView.getSelectionModel().getSelectedItem();
        if (selectedAppointment == null) {
            showAlert("No Selection", "Please select an appointment in the list.");
            return;
        }

        TextInputDialog dialog = new TextInputDialog(selectedAppointment.getDate());
        dialog.setTitle("Update Appointment");
        dialog.setHeaderText("Update appointment details");

        dialog.setContentText("Date (YYYY-MM-DD):");
        Optional<String> dateResult = dialog.showAndWait();
        if (dateResult.isEmpty()) return;
        String date = dateResult.get();

        dialog.setContentText("Status (scheduled/cancelled/finished):");
        Optional<String> statusResult = dialog.showAndWait();
        if (statusResult.isEmpty()) return;
        String status = statusResult.get();
        if (!status.equals("scheduled") && !status.equals("cancelled") && !status.equals("finished")) {
            showAlert("Invalid Status", "The status must be one of the following: scheduled, cancelled, or finished.");
            return;
        }

        dialog.setContentText("Patient ID:");
        Optional<String> patientIdResult = dialog.showAndWait();
        if (patientIdResult.isEmpty()) return;
        Integer patientId;
        try {
            patientId = Integer.parseInt(patientIdResult.get());
        } catch (NumberFormatException e) {
            showAlert("Invalid Patient ID", "Patient ID must be a number.");
            return;
        }

        Appointment<Integer> updatedAppointment = new Appointment<>(selectedAppointment.getId(), patientId, date, status);
        appointmentService.updateAppointment(updatedAppointment);
        loadAppointments();
    }

    @FXML
    private void handleDeleteAppointment() {
        Appointment<Integer> selectedAppointment = appointmentListView.getSelectionModel().getSelectedItem();
        if (selectedAppointment == null) {
            showAlert("No Selection", "Please select an appointment in the list.");
            return;
        }

        appointmentService.deleteAppointment(selectedAppointment.getId());
        loadAppointments();
    }

    @FXML
    private void handleFilterByStatus() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Filter Appointments by Status");
        dialog.setHeaderText("Enter status to filter by (scheduled/cancelled/finished):");

        Optional<String> statusResult = dialog.showAndWait();
        if (statusResult.isEmpty()) return;
        String status = statusResult.get();
        if (!status.equals("scheduled") && !status.equals("cancelled") && !status.equals("finished")) {
            showAlert("Invalid Status", "The status must be one of the following: scheduled, cancelled, or finished.");
            return;
        }

        List<Appointment<Integer>> filteredAppointments = StreamSupport.stream(appointmentService.filterByStatus(status).spliterator(), false)
                .collect(Collectors.toList());
        appointmentListView.getItems().setAll(filteredAppointments);
    }

    @FXML
    private void handleFilterByDate() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Filter Appointments by Date");
        dialog.setHeaderText("Enter date to filter by (YYYY-MM-DD):");

        Optional<String> dateResult = dialog.showAndWait();
        if (dateResult.isEmpty()) return;
        String date = dateResult.get();

        List<Appointment<Integer>> filteredAppointments = StreamSupport.stream(appointmentService.filterByDate(date).spliterator(), false)
                .collect(Collectors.toList());
        appointmentListView.getItems().setAll(filteredAppointments);
    }

    @FXML
    private void handleGetAppointmentsForPatient() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Get Appointments for Patient");
        dialog.setHeaderText("Enter patient ID:");

        Optional<String> patientIdResult = dialog.showAndWait();
        if (patientIdResult.isEmpty()) return;
        Integer patientId;
        try {
            patientId = Integer.parseInt(patientIdResult.get());
        } catch (NumberFormatException e) {
            showAlert("Invalid Patient ID", "Patient ID must be a number.");
            return;
        }

        List<Appointment<Integer>> appointments = appointmentService.getAppointmentsForPatient(patientId);
        appointmentListView.getItems().setAll(appointments);
    }

    @FXML
    private void handleGetAppointmentsByDate() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Get Appointments by Date");
        dialog.setHeaderText("Enter date (YYYY-MM-DD):");

        Optional<String> dateResult = dialog.showAndWait();
        if (dateResult.isEmpty()) return;
        String date = dateResult.get();

        List<Appointment<Integer>> appointments = appointmentService.getAppointmentsByDate(date);
        appointmentListView.getItems().setAll(appointments);
    }

    @FXML
    private void handleGetAppointmentsByStatus() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Get Appointments by Status");
        dialog.setHeaderText("Enter status (scheduled/cancelled/finished):");

        Optional<String> statusResult = dialog.showAndWait();
        if (statusResult.isEmpty()) return;
        String status = statusResult.get();
        if (!status.equals("scheduled") && !status.equals("cancelled") && !status.equals("finished")) {
            showAlert("Invalid Status", "The status must be one of the following: scheduled, cancelled, or finished.");
            return;
        }

        List<Appointment<Integer>> appointments = appointmentService.getAppointmentsByStatus(status);
        appointmentListView.getItems().setAll(appointments);
    }

    @FXML
    private void handleGetAppointmentsForPatientByDate() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Get Appointments for Patient by Date");
        dialog.setHeaderText("Enter patient ID:");

        Optional<String> patientIdResult = dialog.showAndWait();
        if (patientIdResult.isEmpty()) return;
        Integer patientId;
        try {
            patientId = Integer.parseInt(patientIdResult.get());
        } catch (NumberFormatException e) {
            showAlert("Invalid Patient ID", "Patient ID must be a number.");
            return;
        }

        dialog.setContentText("Enter date (YYYY-MM-DD):");
        Optional<String> dateResult = dialog.showAndWait();
        if (dateResult.isEmpty()) return;
        String date = dateResult.get();

        List<Appointment<Integer>> appointments = appointmentService.getAppointmentsForPatientByDate(patientId, date);
        appointmentListView.getItems().setAll(appointments);
    }

    @FXML
    private void handleGetAppointmentsForPatientByStatus() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Get Appointments for Patient by Status");
        dialog.setHeaderText("Enter patient ID:");

        Optional<String> patientIdResult = dialog.showAndWait();
        if (patientIdResult.isEmpty()) return;
        Integer patientId;
        try {
            patientId = Integer.parseInt(patientIdResult.get());
        } catch (NumberFormatException e) {
            showAlert("Invalid Patient ID", "Patient ID must be a number.");
            return;
        }

        dialog.setContentText("Enter status (scheduled/cancelled/finished):");
        Optional<String> statusResult = dialog.showAndWait();
        if (statusResult.isEmpty()) return;
        String status = statusResult.get();
        if (!status.equals("scheduled") && !status.equals("cancelled") && !status.equals("finished")) {
            showAlert("Invalid Status", "The status must be one of the following: scheduled, cancelled, or finished.");
            return;
        }

        List<Appointment<Integer>> appointments = appointmentService.getAppointmentsForPatientByStatus(patientId, status);
        appointmentListView.getItems().setAll(appointments);
    }

    @FXML
    private void handleViewAllAppointments() {
        List<Appointment<Integer>> appointments = StreamSupport.stream(appointmentService.getAllAppointments().spliterator(), false)
                .collect(Collectors.toList());
        appointmentListView.getItems().setAll(appointments);
    }

    @FXML
    private void handleViewAllPatients() {
        List<Patient<Integer>> patients = StreamSupport.stream(patientService.getAllPatients().spliterator(), false)
                .collect(Collectors.toList());
        patientListView.getItems().setAll(patients);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleFilterPatientsByName() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Filter Patients by Name");
        dialog.setHeaderText("Enter name to filter by:");

        Optional<String> nameResult = dialog.showAndWait();
        if (nameResult.isEmpty()) return;
        String name = nameResult.get();

        List<Patient<Integer>> filteredPatients = StreamSupport.stream(patientService.filterPatientsByName(name).spliterator(), false)
                .collect(Collectors.toList());
        patientListView.getItems().setAll(filteredPatients);
    }

    @FXML
    private void handleFilterPatientsByAge() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Filter Patients by Age");
        dialog.setHeaderText("Enter age to filter by:");

        Optional<String> ageResult = dialog.showAndWait();
        if (ageResult.isEmpty()) return;
        int age;
        try {
            age = Integer.parseInt(ageResult.get());
        } catch (NumberFormatException e) {
            showAlert("Invalid Age", "Age must be a number.");
            return;
        }

        List<Patient<Integer>> filteredPatients = StreamSupport.stream(patientService.filterPatientsByAge(age).spliterator(), false)
                .collect(Collectors.toList());
        patientListView.getItems().setAll(filteredPatients);
    }
}