package tests.domain;

import main.domain.Patient;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class PatientTest {

    @Test
    public void testPatientConstructorAndGetName_patientName_returnsName(){
        Patient<Integer> patient = new Patient<>(1, "John Doe", 30);
        assertEquals("John Doe", patient.getName());
    }

    @Test
    public void testPatientConstructorAndGetAge_patientAge_returnsAge(){
        Patient<Integer> patient = new Patient<>(1, "John Doe", 30);
        assertEquals(30, patient.getAge());
    }

    @Test
    public void testPatientConstructor_patientId_returnsId(){
        Patient<Integer> patient = new Patient<>(1, "John Doe", 30);
        assertEquals(1, patient.getId());
    }

    @Test
    public void testPatientConstructorAndSetName_newName_returnsNewName(){
        Patient<Integer> patient = new Patient<>(1, "John Doe", 30);
        patient.setName("Jane Doe");
        assertEquals("Jane Doe", patient.getName());
    }

    @Test
    public void testPatientConstructorAndSetAge_newAge_returnsNewAge(){
        Patient<Integer> patient = new Patient<>(1, "John Doe", 30);
        patient.setAge(35);
        assertEquals(35, patient.getAge());
    }

    @Test
    public void testPatientConstructorAndGetName_emptyName_returnsEmptyName(){
        Patient<Integer> patient = new Patient<>(1, "", 30);
        assertEquals("", patient.getName());
    }

    @Test
    public void testPatientConstructorAndGetId_nullID_returnsNullId(){
        Patient<Integer> patient = new Patient<>(null, "John Doe", 30);
        assertNull(patient.getId());
    }
}