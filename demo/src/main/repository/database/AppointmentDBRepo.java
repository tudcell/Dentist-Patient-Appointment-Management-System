package main.repository.database;

import main.domain.Appointment;
import main.repository.IRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AppointmentDBRepo implements IRepository<Integer, Appointment<Integer>> {
    private final String url;

    public AppointmentDBRepo(String url) {
        this.url = url;
    }

    @Override
    public Integer add(Appointment<Integer> appointment) {
        String sql = "INSERT INTO appointments (id, patientId, date, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, appointment.getId());
            pstmt.setInt(2, appointment.getPatientId());
            pstmt.setString(3, appointment.getDate());
            pstmt.setString(4, appointment.getStatus());

            pstmt.executeUpdate();
            return appointment.getId();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Iterable<Appointment<Integer>> findAll() {
        List<Appointment<Integer>> appointments = new ArrayList<>();
        String sql = "SELECT * FROM appointments";
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Appointment<Integer> appointment = new Appointment<>(
                        rs.getInt("id"),
                        rs.getInt("patientId"),
                        rs.getString("date"),
                        rs.getString("status")
                );
                appointments.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }

    @Override
    public Optional<Appointment<Integer>> findById(Integer id) {
        String sql = "SELECT * FROM appointments WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Appointment<Integer> appointment = new Appointment<>(
                        rs.getInt("id"),
                        rs.getInt("patientId"),
                        rs.getString("date"),
                        rs.getString("status")
                );
                return Optional.of(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void modify(Appointment<Integer> updatedAppointment) {
        String sql = "UPDATE appointments SET patientId = ?, date = ?, status = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, updatedAppointment.getPatientId());
            pstmt.setString(2, updatedAppointment.getDate());
            pstmt.setString(3, updatedAppointment.getStatus());
            pstmt.setInt(4, updatedAppointment.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM appointments WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}