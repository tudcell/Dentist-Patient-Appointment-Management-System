package main.repository.file.binary;

import main.domain.Patient;

public class PatientBinaryFileRepository<Integer> extends BinaryFileRepository<Integer, Patient<Integer>> {
    public PatientBinaryFileRepository(String filename) {
        super(filename);
    }
}