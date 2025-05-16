package module3.com.example.model;

import java.sql.Date;

public class RoomRental {
    private int roomId;
    private String roomCode;
    private String tenantName;
    private String phoneNumber;
    private Date startDate;
    private int paymentTypeId;
    private String paymentTypeName;
    private String notes;

    public RoomRental() {
    }

    public RoomRental(int roomId, String roomCode, String tenantName, String phoneNumber, 
                     Date startDate, int paymentTypeId, String paymentTypeName, String notes) {
        this.roomId = roomId;
        this.roomCode = roomCode;
        this.tenantName = tenantName;
        this.phoneNumber = phoneNumber;
        this.startDate = startDate;
        this.paymentTypeId = paymentTypeId;
        this.paymentTypeName = paymentTypeName;
        this.notes = notes;
    }

    // Getters and Setters
    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public int getPaymentTypeId() {
        return paymentTypeId;
    }

    public void setPaymentTypeId(int paymentTypeId) {
        this.paymentTypeId = paymentTypeId;
    }

    public String getPaymentTypeName() {
        return paymentTypeName;
    }

    public void setPaymentTypeName(String paymentTypeName) {
        this.paymentTypeName = paymentTypeName;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
} 