package tests.filters.AppointmentFiltersTest;

import main.domain.Appointment;
import main.filters.FilterAppointmentsByDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FilterAppointmentsByDateTest {

    private FilterAppointmentsByDate<Integer> filter;
    private String appointmentDate;

    @BeforeEach
    public void setUp() {
        appointmentDate = "2023-10-01";
        filter = new FilterAppointmentsByDate<>(appointmentDate);
    }

    @Test
    public void testAccept_matchingAppointmentDate_accepted(){
        Appointment<Integer> appointment = new Appointment<>(1, 1, "2023-10-01", "scheduled");
        boolean filterResult = filter.accept(appointment);
        assertTrue(filterResult, "Appointments with matching date should be accepted!");
    }

    @Test
    public void testAccept_differentAppointmentDate_notAccepted(){
        Appointment<Integer> appointment = new Appointment<>(1, 1, "2023-10-02", "scheduled");
        boolean filterResult = filter.accept(appointment);
        assertFalse(filterResult, "Appointments with different date should not be accepted!");
    }

    @Test
    public void testAccept_matchingAppointmentDateCaseInsensitive_accepted(){
        Appointment<Integer> appointment = new Appointment<>(1, 1, "2023-10-01", "scheduled");
        boolean filterResult = filter.accept(appointment);
        assertTrue(filterResult, "Appointments with matching date, case insensitive, should be accepted!");
    }

    @Test
    public void testAccept_nullFilterAppointmentDate_notAccepted(){
        FilterAppointmentsByDate<Integer> filterWithNullDate = new FilterAppointmentsByDate<>(null);
        Appointment<Integer> appointment = new Appointment<>(1, 1, "2023-10-01", "scheduled");
        boolean filterResult = filterWithNullDate.accept(appointment);
        assertFalse(filterResult, "Appointment should not be accepted because the filter date is null!");
    }

    @Test
    public void testAccept_partiallyMatchingAppointmentDate_notAccepted(){
        Appointment<Integer> appointment = new Appointment<>(1, 1, "2023-10-01T10:00:00", "scheduled");
        boolean filterResult = filter.accept(appointment);
        assertFalse(filterResult, "Appointments with partially matching date should not be accepted!");
    }
}