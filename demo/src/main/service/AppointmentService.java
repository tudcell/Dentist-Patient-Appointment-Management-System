package main.service;

import main.domain.Appointment;
import main.domain.Patient;
import main.filters.FilterAppointmentsByStatus;
import main.filters.FilterAppointmentsByDate;
import main.repository.IRepository;
import main.repository.memory.InMemoryFilteredRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class AppointmentService {
    private IRepository<Integer, Appointment<Integer>> appointmentRepository;
    private IRepository<Integer, Patient<Integer>> patientRepository;

    public AppointmentService(IRepository<Integer, Appointment<Integer>> appointmentRepository, IRepository<Integer, Patient<Integer>> patientRepository) {
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
    }

    public Integer createAppointment(Integer appointmentId, Integer patientId, String date, String status) {
        if(appointmentRepository.findById(appointmentId).isPresent()) {
            throw new IllegalArgumentException("Appointment already exists");
        }

        if(patientRepository.findById(patientId).isEmpty()) {
            throw new IllegalArgumentException("Patient ID " + patientId + " not found");
        }
        Appointment<Integer> newAppointment = new Appointment<>(appointmentId, patientId, date, status);
        return appointmentRepository.add(newAppointment);
    }

    public Iterable<Appointment<Integer>> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public Optional<Appointment<Integer>> getAppointmentById(Integer idToViewBy) {
        return appointmentRepository.findById(idToViewBy);
    }

    public void cancelAppointment(Integer idToBeCancelled) {
        Optional<Appointment<Integer>> appointmentToBeCancelled = appointmentRepository.findById(idToBeCancelled);
        if(appointmentToBeCancelled.isEmpty()){
            throw new IllegalArgumentException("Appointment with ID " + idToBeCancelled + " not found");
        }
        Appointment<Integer> appointment = appointmentToBeCancelled.get();
        appointment.setStatus("cancelled");
        appointmentRepository.modify(appointment);
    }

    public void finishAppointment(Integer idToBeFinished) {
        Optional<Appointment<Integer>> appointmentToBeFinished = appointmentRepository.findById(idToBeFinished);
        if(appointmentToBeFinished.isEmpty()){
            throw new IllegalArgumentException("Appointment with ID " + idToBeFinished + " not found");
        }
        Appointment<Integer> appointment = appointmentToBeFinished.get();
        appointment.setStatus("finished");
        appointmentRepository.modify(appointment);
    }

    public void deleteAppointment(Integer idToBeDeleted) {
        if(appointmentRepository.findById(idToBeDeleted).isEmpty()){
            throw new IllegalArgumentException("Appointment with ID " + idToBeDeleted + " not found");
        }
        appointmentRepository.delete(idToBeDeleted);
    }

    public Iterable<Appointment<Integer>> filterByStatus(String statusToFilterBy) {
        FilterAppointmentsByStatus<Integer> statusFilter = new FilterAppointmentsByStatus<>(statusToFilterBy);

        InMemoryFilteredRepository<Integer, Appointment<Integer>> filteredRepository = new InMemoryFilteredRepository<>(appointmentRepository, statusFilter);
        return filteredRepository.findAll();
    }

    public Iterable<Appointment<Integer>> filterByDate(String dateToFilterBy) {
        FilterAppointmentsByDate<Integer> dateFilter = new FilterAppointmentsByDate<>(dateToFilterBy);
        InMemoryFilteredRepository<Integer, Appointment<Integer>> filteredRepository = new InMemoryFilteredRepository<>(appointmentRepository, dateFilter);
        return filteredRepository.findAll();
    }

    public void updateAppointment(Appointment<Integer> newAppointment){
        appointmentRepository.modify(newAppointment);
    }

    public List<Appointment<Integer>> getAppointmentsForPatient(Integer patientId) {
        return StreamSupport.stream(appointmentRepository.findAll().spliterator(), false)
                .filter(appointment -> appointment.getPatientId().equals(patientId))
                .collect(Collectors.toList());
    }


    public List<Appointment<Integer>> getAppointmentsByDate(String date) {
        return StreamSupport.stream(appointmentRepository.findAll().spliterator(), false)
                .filter(appointment -> appointment.getDate().equals(date))
                .collect(Collectors.toList());
    }


    public List<Appointment<Integer>> getAppointmentsByStatus(String status) {
        return StreamSupport.stream(appointmentRepository.findAll().spliterator(), false)
                .filter(appointment -> appointment.getStatus().equals(status))
                .collect(Collectors.toList());
    }


    public List<Appointment<Integer>> getAppointmentsForPatientByDate(Integer patientId, String date) {
        return StreamSupport.stream(appointmentRepository.findAll().spliterator(), false)
                .filter(appointment -> appointment.getPatientId().equals(patientId) && appointment.getDate().equals(date))
                .collect(Collectors.toList());
    }


    public List<Appointment<Integer>> getAppointmentsForPatientByStatus(Integer patientId, String status) {
        return StreamSupport.stream(appointmentRepository.findAll().spliterator(), false)
                .filter(appointment -> appointment.getPatientId().equals(patientId) && appointment.getStatus().equals(status))
                .collect(Collectors.toList());
    }
}