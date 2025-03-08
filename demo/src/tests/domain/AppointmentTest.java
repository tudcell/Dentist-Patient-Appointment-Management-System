package tests.domain;

import main.domain.Appointment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class AppointmentTest {

    private Appointment<Integer> appointment;

    @BeforeEach
    public void setUp() {
        appointment = new Appointment<>(1, 1, "2023-10-01", "scheduled");
    }

    @Test
    public void testGetId_returnsId(){
        assertEquals(1, appointment.getId());
    }

    @Test
    public void testSetId_positiveId_returnsId(){
        appointment.setID(10);
        assertEquals(10, appointment.getId());
    }

    @Test
    public void testToString_expectedString_returnsStringRepresentation(){
        String expectedString = "1,1,2023-10-01,scheduled";
        assertEquals(expectedString, appointment.toString());
    }

    @Test
    public void testAppointmentConstructor_nullDate_returnsNullDate(){
        Appointment<Integer> nullDateAppointment = new Appointment<>(1, 1, null, "scheduled");
        assertNull(nullDateAppointment.getDate());
    }

    @Test
    public void testConstructor_appointmentStatus_returnsScheduledStatus(){
        assertEquals("scheduled", appointment.getStatus());
    }

    @Test
    public void testSetStatus_differentStatus_shouldChange(){
        appointment.setStatus("cancelled");
        assertEquals("cancelled", appointment.getStatus());
    }
}