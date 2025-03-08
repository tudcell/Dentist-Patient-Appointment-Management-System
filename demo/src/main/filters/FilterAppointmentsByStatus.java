package main.filters;

import main.domain.Appointment;

public class FilterAppointmentsByStatus<ID> implements Filter<Appointment<ID>> {
    private String status;

    public FilterAppointmentsByStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean accept(Appointment<ID> item) {
        return item.getStatus().equalsIgnoreCase(status);
    }
}