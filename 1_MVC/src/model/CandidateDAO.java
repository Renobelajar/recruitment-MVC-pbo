package model;

import config.Connector;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CandidateDAO implements CandidateInterface {
    private Connection connection;

    public CandidateDAO() {
        connection = Connector.getConnection();
    }

    @Override
    public void insert(Candidate candidate) {
        String sql = "INSERT INTO recruit (nama, path, writing, coding, interview, score, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            if (connection != null) {
                connection.setAutoCommit(true);
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, candidate.getName());
                    statement.setString(2, candidate.getPath());
                    statement.setDouble(3, candidate.getWriting());
                    statement.setDouble(4, candidate.getCoding());
                    statement.setDouble(5, candidate.getInterview());
                    statement.setDouble(6, candidate.getScore());
                    statement.setString(7, candidate.getStatus());
                    statement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            System.err.println("Gagal Insert: " + e.getMessage());
        }
    }

    @Override
    public void update(Candidate candidate) {
        // PERBAIKAN: Menyesuaikan parameter WHERE nama=? (bukan WHERE name=?)
        String sql = "UPDATE recruit SET path=?, writing=?, coding=?, interview=?, score=?, status=? WHERE nama=?";
        try {
            if (connection != null) {
                connection.setAutoCommit(true);
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, candidate.getPath());
                    statement.setDouble(2, candidate.getWriting());
                    statement.setDouble(3, candidate.getCoding());
                    statement.setDouble(4, candidate.getInterview());
                    statement.setDouble(5, candidate.getScore());
                    statement.setString(6, candidate.getStatus());
                    statement.setString(7, candidate.getName()); // Mengisi WHERE nama
                    statement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            System.err.println("Gagal Update: " + e.getMessage());
        }
    }

    @Override
    public void delete(String name) {
        // PERBAIKAN: Menyesuaikan WHERE nama=?
        String sql = "DELETE FROM recruit WHERE nama=?";
        try {
            if (connection != null) {
                connection.setAutoCommit(true);
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, name);
                    statement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            System.err.println("Gagal Delete: " + e.getMessage());
        }
    }

    @Override
    public List<Candidate> getAll() {
        List<Candidate> list = new ArrayList<>();
        String sql = "SELECT * FROM recruit";
        try {
            if (connection != null) {
                try (Statement statement = connection.createStatement();
                     ResultSet rs = statement.executeQuery(sql)) {
                    while (rs.next()) {
                        // PERBAIKAN: Mengambil data string dari kolom 'nama' (bukan 'name')
                        Candidate candidate = new Candidate(
                            rs.getString("nama"), 
                            rs.getString("path"),
                            rs.getDouble("writing"),
                            rs.getDouble("coding"),
                            rs.getDouble("interview"),
                            rs.getDouble("score"),
                            rs.getString("status")
                        );
                        list.add(candidate);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Gagal Mengambil Data: " + e.getMessage());
        }
        return list;
    }
}