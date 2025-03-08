package main.repository.database;

import main.domain.Patient;
import main.repository.IRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PatientDBRepo implements IRepository<Integer, Patient<Integer>> {
    private final String url;

    public PatientDBRepo(String url) {
        this.url = url;
    }

    @Override
    public Integer add(Patient<Integer> patient) {
        String sql = "INSERT INTO patients (id, name, age) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, patient.getId());
            pstmt.setString(2, patient.getName());
            pstmt.setInt(3, patient.getAge());

            pstmt.executeUpdate();
            return patient.getId();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Iterable<Patient<Integer>> findAll() {
        List<Patient<Integer>> patients = new ArrayList<>();
        String sql = "SELECT * FROM patients";
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Patient<Integer> patient = new Patient<>(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age")
                );
                patients.add(patient);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patients;
    }

    @Override
    public Optional<Patient<Integer>> findById(Integer id) {
        String sql = "SELECT * FROM patients WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Patient<Integer> patient = new Patient<>(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age")
                );
                return Optional.of(patient);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void modify(Patient<Integer> updatedPatient) {
        String sql = "UPDATE patients SET name = ?, age = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, updatedPatient.getName());
            pstmt.setInt(2, updatedPatient.getAge());
            pstmt.setInt(3, updatedPatient.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM patients WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}