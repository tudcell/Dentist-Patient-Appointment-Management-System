package tests.filters.AppointmentFiltersTest;

import main.domain.Appointment;
import main.filters.FilterAppointmentsByStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FilterAppointmentsByStatusTest {

    private FilterAppointmentsByStatus<Integer> filter;
    private String filterStatus;

    @BeforeEach
    public void setUp() {
        filterStatus = "cancelled";
        filter = new FilterAppointmentsByStatus<>(filterStatus);
    }

    @Test
    public void testAccept_matchingStatus_accepted(){
        Appointment<Integer> appointment = new Appointment<>(1, 1, "2023-10-01", "cancelled");
        boolean filterResult = filter.accept(appointment);
        assertTrue(filterResult, "Appointments with matching status should be accepted!");
    }

    @Test
    public void testAccept_matchingStatusCaseInsensitive_accepted(){
        Appointment<Integer> appointment = new Appointment<>(1, 1, "2023-10-01", "Cancelled");
        boolean filterResult = filter.accept(appointment);
        assertTrue(filterResult, "Appointments with matching status, case insensitive, should be accepted!");
    }

    @Test
    public void testAccept_differentStatus_notAccepted(){
        Appointment<Integer> appointment = new Appointment<>(1, 1, "2023-10-01", "finished");
        boolean filterResult = filter.accept(appointment);
        assertFalse(filterResult, "Appointments with different status should not be accepted!");
    }

    @Test
    public void testAccept_emptyAppointmentStatusEmptyFilterStatus_accepted(){
        FilterAppointmentsByStatus<Integer> filterWithEmptyStatus = new FilterAppointmentsByStatus<>("");
        Appointment<Integer> appointment = new Appointment<>(1, 1, "2023-10-01", "");
        boolean filterResult = filterWithEmptyStatus.accept(appointment);
        assertTrue(filterResult, "Appointments with empty status should be accepted because the filter status is also empty!");
    }

    @Test
    public void testAccept_nullFilterStatus_notAccepted(){
        FilterAppointmentsByStatus<Integer> filterWithNullStatus = new FilterAppointmentsByStatus<>(null);
        Appointment<Integer> appointment = new Appointment<>(1, 1, "2023-10-01", "cancelled");
        boolean filterResult = filterWithNullStatus.accept(appointment);
        assertFalse(filterResult, "Filter should not accept appointment because the filter status is null!");
    }
}