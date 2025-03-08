package tests.repository.file;

import main.domain.Patient;
import main.repository.file.text.PatientTextFileRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TextFilePatientRepositoryTest {
    private static final String filename = "test_patients.txt";
    private PatientTextFileRepository repository;

    @BeforeEach
    public void setUp() {
        repository = new PatientTextFileRepository(filename);
    }

    @AfterEach
    public void tearDown() {
        File file = new File(filename);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void testAdd_addANewPatient_true(){
        Patient<Integer> patient = new Patient<>(1, "John Doe", 30);
        repository.add(patient);
        List<Patient<Integer>> patients = (List<Patient<Integer>>) repository.findAll();
        assertEquals(1, patients.size());
    }

    @Test
    public void testDelete_deleteAPatient_true(){
        Patient<Integer> patient = new Patient<>(1, "John Doe", 30);
        repository.add(patient);
        repository.delete(patient.getId());
        List<Patient<Integer>> patients = (List<Patient<Integer>>) repository.findAll();
        assertTrue(patients.isEmpty(), "Repo should be empty!");
    }

    @Test
    public void testModify_updateAPatientInRepository_true(){
        Patient<Integer> patient = new Patient<>(1, "John Doe", 30);
        repository.add(patient);
        patient.setName("Jane Doe");
        patient.setAge(25);
        repository.modify(patient);

        List<Patient<Integer>> patients = (List<Patient<Integer>>) repository.findAll();
        assertEquals("Jane Doe", patients.get(0).getName());
    }

    @Test
    public void testEnsureFileExists_fileExists_fileCreated(){
        File file = new File(filename);
        file.delete();

        repository = new PatientTextFileRepository(filename);
        assertTrue(file.exists(), "File should be created!");
    }

    @Test
    public void testEnsureFileExists_FileDoesNotExist_FileCreated() {
        File file = new File(filename);
        file.delete();

        repository = new PatientTextFileRepository(filename);

        assertTrue(file.exists(), "The file should be created if it doesn't exist.");
    }
}