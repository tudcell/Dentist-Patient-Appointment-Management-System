// File: src/main/repository/RepositoryDentist.java
package main.repository;

import main.domain.Appointment;
import main.domain.Patient;
import main.repository.database.PatientDBRepo;
import main.repository.file.binary.AppointmentBinaryFileRepository;
import main.repository.file.binary.PatientBinaryFileRepository;
import main.repository.file.text.AppointmentTextFileRepository;
import main.repository.file.text.PatientTextFileRepository;
import main.repository.memory.AppointmentRepositoryInMemory;
import main.repository.memory.PatientRepositoryInMemory;
import main.repository.database.AppointmentDBRepo;

public class RepositoryDentist {
    public static IRepository<Integer, Appointment<Integer>> createAppointmentRepository(String repositoryType, String fileNameOrUrl) {
        return switch (repositoryType.toLowerCase()) {
            case "binary" -> new AppointmentBinaryFileRepository<>(fileNameOrUrl);
            case "text" -> new AppointmentTextFileRepository(fileNameOrUrl);
            case "database" -> new AppointmentDBRepo(fileNameOrUrl);
            default -> new AppointmentRepositoryInMemory();
        };
    }

    public static IRepository<Integer, Patient<Integer>> createPatientRepository(String repositoryType, String fileNameOrUrl) {
        return switch (repositoryType.toLowerCase()) {
            case "binary" -> new PatientBinaryFileRepository<>(fileNameOrUrl);
            case "text" -> new PatientTextFileRepository(fileNameOrUrl);
            case "database" -> new PatientDBRepo(fileNameOrUrl);
            default -> new PatientRepositoryInMemory();
        };
    }
}