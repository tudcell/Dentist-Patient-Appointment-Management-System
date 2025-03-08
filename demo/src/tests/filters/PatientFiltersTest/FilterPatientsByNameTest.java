package tests.filters.PatientFiltersTest;

import main.domain.Patient;
import main.filters.FilterPatientsByName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FilterPatientsByNameTest {

    private FilterPatientsByName<Integer> filter;

    @BeforeEach
    public void setUp() {
        filter = new FilterPatientsByName<>("John Doe");
    }

    @Test
    public void testAccept_matchingName_accepted() {
        Patient<Integer> patient = new Patient<>(1, "John Doe", 30);
        boolean filterResult = filter.accept(patient);
        assertTrue(filterResult, "Patients with matching name should be accepted!");
    }

    @Test
    public void testAccept_matchingNameCaseInsensitive_accepted() {
        Patient<Integer> patient = new Patient<>(1, "john doe", 30);
        boolean filterResult = filter.accept(patient);
        assertTrue(filterResult, "Patients with matching name, case insensitive, should be accepted!");
    }

    @Test
    public void testAccept_differentName_notAccepted() {
        Patient<Integer> patient = new Patient<>(1, "Jane Doe", 30);
        boolean filterResult = filter.accept(patient);
        assertFalse(filterResult, "Patients with different names should not be accepted!");
    }

    @Test
    public void testAccept_emptyPatientNameEmptyFilterName_accepted() {
        String filterName = " ";
        FilterPatientsByName<Integer> filterWithEmptyName = new FilterPatientsByName<>(filterName);
        Patient<Integer> patient = new Patient<>(1, " ", 30);
        boolean filterResult = filterWithEmptyName.accept(patient);
        assertTrue(filterResult, "Patients with empty names matching empty filter name should be accepted!");
    }

    @Test
    public void testAccept_nullFilterName_notAccepted() {
        String filterName = null;
        FilterPatientsByName<Integer> filterWithNullName = new FilterPatientsByName<>(filterName);
        Patient<Integer> patient = new Patient<>(1, "John Doe", 30);
        boolean filterResult = filterWithNullName.accept(patient);
        assertFalse(filterResult, "Filter should not accept the patient because the filter name is null!");
    }
}