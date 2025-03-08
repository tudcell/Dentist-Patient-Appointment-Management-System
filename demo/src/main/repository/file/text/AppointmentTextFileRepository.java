package main.repository.file.text;

import main.domain.Appointment;

public class AppointmentTextFileRepository extends TextFileRepository<Integer, Appointment<Integer>> {

    public AppointmentTextFileRepository(String filename) {
        super(filename);
        super.readFromFile();
    }

    @Override
    protected Appointment<Integer> parseEntity(String line) {
        String[] fields = line.split(",");

        Integer appointmentId = Integer.valueOf(fields[0].trim());
        Integer patientId = Integer.valueOf(fields[1].trim());
        String date = fields[2].trim();
        String status = fields[3].trim();

        Appointment<Integer> appointment = new Appointment<>(appointmentId, patientId, date, status);
        return appointment;
    }
}