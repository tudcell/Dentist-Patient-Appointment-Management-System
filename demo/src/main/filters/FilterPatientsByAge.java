package main.filters;

import main.domain.Patient;

public class FilterPatientsByAge<ID> implements Filter<Patient<ID>> {
    private int age;

    public FilterPatientsByAge(int age) {
        this.age = age;
    }

    @Override
    public boolean accept(Patient<ID> item) {
        return item.getAge() == age;
    }
}