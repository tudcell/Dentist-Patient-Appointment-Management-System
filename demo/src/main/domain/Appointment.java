package main.domain;
import java.io.Serializable;
public class Appointment<ID> implements Identifiable<ID>, Serializable {
    private ID id;
    private ID patientId;
    private String date;
    private String status;

    public Appointment(ID id, ID patientId, String date, String status) {
        this.id = id;
        this.patientId = patientId;
        this.date = date;
        this.status = status;
    }

    @Override
    public ID getId() {
        return id;
    }

    public ID getPatientId() {
        return patientId;
    }

    public String getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setID(ID id) {
        this.id = id;
    }
    @Override
    public String toString() {
        return id +"," + patientId + "," + date + "," + status;
    }
}