package main.filters;

import main.domain.Appointment;

public class FilterAppointmentsByDate<ID> implements Filter<Appointment<ID>> {
    private String date;

    public FilterAppointmentsByDate(String date) {
        this.date = date;
    }

    @Override
    public boolean accept(Appointment<ID> item) {
        return item.getDate().equals(date);
    }
}