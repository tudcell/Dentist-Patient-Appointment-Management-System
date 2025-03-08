// src/main/service/PatientService.java
package main.service;

import main.domain.Patient;
import main.filters.FilterPatientsByName;
import main.filters.FilterPatientsByAge;
import main.repository.IRepository;
import main.repository.memory.InMemoryFilteredRepository;

import java.util.Optional;

public class PatientService {
    private final IRepository<Integer, Patient<Integer>> patientRepository;

    public PatientService(IRepository<Integer, Patient<Integer>> patientRepository) {
        this.patientRepository = patientRepository;
    }

    public Integer createPatient(Integer id, String name, int age) {
        Patient<Integer> newPatient = new Patient<>(id, name, age);
        patientRepository.add(newPatient);
        return newPatient.getId();
    }

    public Iterable<Patient<Integer>> getAllPatients() {
        return patientRepository.findAll();
    }

    public Optional<Patient<Integer>> getPatientById(Integer idToViewBy) {
        return patientRepository.findById(idToViewBy);
    }

    public void updatePatient(Patient<Integer> newPatient) {
        patientRepository.modify(newPatient);
    }

    public void deletePatient(Integer idToBeDeleted) {
        if (patientRepository.findById(idToBeDeleted).isEmpty()) {
            throw new IllegalArgumentException("Patient with ID " + idToBeDeleted + " not found");
        }
        patientRepository.delete(idToBeDeleted);
    }

    public Iterable<Patient<Integer>> filterPatientsByName(String nameToFilterBy) {
        FilterPatientsByName<Integer> nameFilter = new FilterPatientsByName<>(nameToFilterBy);
        InMemoryFilteredRepository<Integer, Patient<Integer>> filteredRepository = new InMemoryFilteredRepository<>(patientRepository, nameFilter);
        return filteredRepository.findAll();
    }

    public Iterable<Patient<Integer>> filterPatientsByAge(int ageToFilterBy) {
        FilterPatientsByAge<Integer> ageFilter = new FilterPatientsByAge<>(ageToFilterBy);
        InMemoryFilteredRepository<Integer, Patient<Integer>> filteredRepository = new InMemoryFilteredRepository<>(patientRepository, ageFilter);
        return filteredRepository.findAll();
    }
}