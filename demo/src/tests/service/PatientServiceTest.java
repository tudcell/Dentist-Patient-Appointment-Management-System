package tests.service;

import main.domain.Patient;
import main.repository.memory.InMemoryRepository;
import main.service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class PatientServiceTest {
    private PatientService patientService;
    private InMemoryRepository<Integer, Patient<Integer>> patientRepository;

    @BeforeEach
    void setUp() {
        patientRepository = new InMemoryRepository<>();
        patientService = new PatientService(patientRepository);
    }

    @Test
    void addPatient_ShouldAddPatient_newPatient() {
        Integer patientId = patientService.createPatient(1, "John Doe", 30);
        assertEquals(1, patientId);
    }

    @Test
    void getAllPatients_ShouldReturnAllPatients_newPatient() {
        patientService.createPatient(1, "John Doe", 30);
        patientService.createPatient(2, "Jane Doe", 25);

        Iterable<Patient<Integer>> result = patientService.getAllPatients();
        List<Patient<Integer>> patientList = new ArrayList<>();
        result.forEach(patientList::add);

        assertEquals(2, patientList.size());
    }

    @Test
    void getAllPatients_ShouldReturnEmptyList_WhenNoPatients() {
        Iterable<Patient<Integer>> result = patientService.getAllPatients();
        assertFalse(result.iterator().hasNext(), "getAllPatients should return an empty iterable if there are no patients");
    }

    @Test
    void getPatientById_ShouldReturnPatient_WhenExists() {
        Integer patientId = patientService.createPatient(1, "John Doe", 30);
        Optional<Patient<Integer>> result = patientService.getPatientById(patientId);
        assertTrue(result.isPresent());
        assertEquals("John Doe", result.get().getName());
    }

    @Test
    void getPatientById_ShouldReturnEmpty_WhenPatientDoesNotExist() {
        Optional<Patient<Integer>> result = patientService.getPatientById(1);
        assertFalse(result.isPresent(), "getPatientById should return an empty Optional if the patient does not exist");
    }

    @Test
    void updatePatient_ShouldUpdatePatient_WhenValid() {
        Integer patientId = patientService.createPatient(1, "John Doe", 30);
        Patient<Integer> updatedPatient = new Patient<>(patientId, "Jane Doe", 35);
        patientService.updatePatient(updatedPatient);
        assertEquals(35, patientRepository.findById(patientId).get().getAge());
    }

    @Test
    void deletePatient_ShouldDeletePatient_WhenExists() {
        Integer patientId = patientService.createPatient(1, "John Doe", 30);
        patientService.deletePatient(patientId);
        assertFalse(patientRepository.findById(patientId).isPresent());
    }

    @Test
    void deletePatient_ShouldThrowException_WhenPatientDoesNotExist() {
        assertThrows(IllegalArgumentException.class, () -> patientService.deletePatient(1));
    }

    @Test
    void filterByName_ShouldReturnPatients_WithFilteringName() {
        String name = "John Doe";
        patientService.createPatient(1, name, 30);

        Iterable<Patient<Integer>> result = patientService.filterPatientsByName(name);
        result.forEach(p -> assertEquals(name, p.getName()));
    }

    @Test
    void filterByAge_ShouldReturnPatients_WithFilteringAge() {
        int age = 30;
        patientService.createPatient(1, "John Doe", age);
        patientService.createPatient(2, "Jane Doe", 25);

        Iterable<Patient<Integer>> result = patientService.filterPatientsByAge(age);
        result.forEach(p -> assertEquals(age, p.getAge()));
    }
}
