package main.repository.file.binary;

import main.domain.Appointment;

public class AppointmentBinaryFileRepository<Integer> extends BinaryFileRepository<Integer, Appointment<Integer>> {
    public AppointmentBinaryFileRepository(String filename) {
        super(filename);
    }
}