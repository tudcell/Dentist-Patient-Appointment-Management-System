package tests.filters.PatientFiltersTest;

import main.domain.Patient;
import main.filters.FilterPatientsByAge;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FilterPatientsByAgeTest {

    private FilterPatientsByAge<Integer> filter;
    private int age;

    @BeforeEach
    public void setUp(){
        age = 15;
        filter = new FilterPatientsByAge<>(age);
    }

    @Test
    public void testAccept_matchingAge_accepted(){
        Patient<Integer> patient = new Patient<>(1, "John Doe", age);
        boolean filterResult = filter.accept(patient);
        assertTrue(filterResult, "Patients with matching age should be accepted!");
    }

    @Test
    public void testAccept_differentAge_notAccepted(){
        Patient<Integer> patient = new Patient<>(1, "John Doe", 20);
        boolean filterResult = filter.accept(patient);
        assertFalse(filterResult, "Patients with different age should not be accepted!");
    }

    @Test
    public void testAccept_negativeAge_notAccepted(){
        Patient<Integer> patient = new Patient<>(1, "John Doe", -5);
        boolean filterResult = filter.accept(patient);
        assertFalse(filterResult, "Patients with negative age should not be accepted!");
    }

    @Test
    public void testAccept_zeroAge_notAccepted(){
        Patient<Integer> patient = new Patient<>(1, "John Doe", 0);
        boolean filterResult = filter.accept(patient);
        assertFalse(filterResult, "Patients with zero age should not be accepted!");
    }
}