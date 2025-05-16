package module3.com.example.dao;

import module3.com.example.model.RoomRental;
import module3.com.example.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomRentalDAO {
    private static final String SELECT_ALL_ROOMS = "SELECT r.*, p.payment_type_name FROM RoomRental r " +
            "JOIN PaymentType p ON r.payment_type_id = p.payment_type_id";
    private static final String INSERT_ROOM = "INSERT INTO RoomRental (room_code, tenant_name, phone_number, " +
            "start_date, payment_type_id, notes) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String DELETE_ROOM = "DELETE FROM RoomRental WHERE room_code = ?";
    private static final String SEARCH_ROOMS = "SELECT r.*, p.payment_type_name FROM RoomRental r " +
            "JOIN PaymentType p ON r.payment_type_id = p.payment_type_id " +
            "WHERE r.room_code LIKE ? OR r.tenant_name LIKE ? OR r.phone_number LIKE ?";
    private static final String SELECT_PAYMENT_TYPES = "SELECT * FROM PaymentType";

    public List<RoomRental> getAllRooms() {
        List<RoomRental> rooms = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_ALL_ROOMS)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                rooms.add(extractRoomFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }

    public boolean insertRoom(RoomRental room) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_ROOM)) {
            ps.setString(1, room.getRoomCode());
            ps.setString(2, room.getTenantName());
            ps.setString(3, room.getPhoneNumber());
            ps.setDate(4, room.getStartDate());
            ps.setInt(5, room.getPaymentTypeId());
            ps.setString(6, room.getNotes());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteRooms(String[] roomCodes) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE_ROOM)) {
            conn.setAutoCommit(false);
            for (String roomCode : roomCodes) {
                ps.setString(1, roomCode);
                ps.addBatch();
            }
            int[] results = ps.executeBatch();
            conn.commit();
            return results.length > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<RoomRental> searchRooms(String searchTerm) {
        List<RoomRental> rooms = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SEARCH_ROOMS)) {
            String searchPattern = "%" + searchTerm + "%";
            ps.setString(1, searchPattern);
            ps.setString(2, searchPattern);
            ps.setString(3, searchPattern);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                rooms.add(extractRoomFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }

    private RoomRental extractRoomFromResultSet(ResultSet rs) throws SQLException {
        return new RoomRental(
            rs.getInt("room_id"),
            rs.getString("room_code"),
            rs.getString("tenant_name"),
            rs.getString("phone_number"),
            rs.getDate("start_date"),
            rs.getInt("payment_type_id"),
            rs.getString("payment_type_name"),
            rs.getString("notes")
        );
    }

    public List<PaymentType> getAllPaymentTypes() {
        List<PaymentType> paymentTypes = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_PAYMENT_TYPES)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                paymentTypes.add(new PaymentType(
                    rs.getInt("payment_type_id"),
                    rs.getString("payment_type_name")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return paymentTypes;
    }

    public static class PaymentType {
        private int id;
        private String name;

        public PaymentType(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() { return id; }
        public String getName() { return name; }
    }
} 