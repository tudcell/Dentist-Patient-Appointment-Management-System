package tests.repository.file;

import main.domain.Appointment;
import main.repository.file.text.AppointmentTextFileRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TextFileAppointmentRepositoryTest {
    private static final String filename = "test_appointments.txt";
    private AppointmentTextFileRepository repository;

    @BeforeEach
    public void setUp() {
        repository = new AppointmentTextFileRepository(filename);
    }

    @AfterEach
    public void tearDown() {
        File file = new File(filename);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void testAdd_newAppointment_true(){
        Appointment<Integer> appointment = new Appointment<>(1, 1, "2023-10-01", "scheduled");
        repository.add(appointment);
        List<Appointment<Integer>> appointments = (List<Appointment<Integer>>) repository.findAll();
        assertEquals(appointment.getDate(), appointments.get(0).getDate());
    }

    @Test
    public void testDelete_appointment_true(){
        Appointment<Integer> appointment = new Appointment<>(1, 1, "2023-10-01", "scheduled");
        repository.add(appointment);
        repository.delete(appointment.getId());
        List<Appointment<Integer>> appointments = (List<Appointment<Integer>>) repository.findAll();
        assertTrue(appointments.isEmpty(), "Repo should be empty!");
    }

    @Test
    public void testModify_modifyingTheAppointment_true(){
        Appointment<Integer> appointment = new Appointment<>(1, 1, "2023-10-01", "scheduled");
        repository.add(appointment);

        appointment.setStatus("cancelled");
        repository.modify(appointment);

        List<Appointment<Integer>> appointments = (List<Appointment<Integer>>) repository.findAll();
        assertEquals("cancelled", appointments.get(0).getStatus());
    }
}