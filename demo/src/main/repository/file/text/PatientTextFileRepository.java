package main.repository.file.text;

import main.domain.Patient;

public class PatientTextFileRepository extends TextFileRepository<Integer, Patient<Integer>> {

    public PatientTextFileRepository(String filename) {
        super(filename);
        super.readFromFile();
    }

    @Override
    protected Patient<Integer> parseEntity(String line) {
        String[] fields = line.split(",");

        Integer patientId = Integer.valueOf(fields[0].trim());
        String name = fields[1].trim();
        int age = Integer.parseInt(fields[2].trim());

        Patient<Integer> patient = new Patient<>(patientId, name, age);
        return patient;
    }
}