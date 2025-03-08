package main.filters;

import main.domain.Patient;

public class FilterPatientsByName<ID> implements Filter<Patient<ID>> {
    private String name;

    public FilterPatientsByName(String name) {
        this.name = name;
    }

    @Override
    public boolean accept(Patient<ID> item) {
        return item.getName().equalsIgnoreCase(name);
    }
}