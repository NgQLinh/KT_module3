package module3.com.example.servlet;

import module3.com.example.dao.RoomRentalDAO;
import module3.com.example.model.RoomRental;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

@WebServlet("/rooms/*")
public class RoomRentalServlet extends HttpServlet {
    private RoomRentalDAO roomRentalDAO;

    @Override
    public void init() {
        roomRentalDAO = new RoomRentalDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getPathInfo();
        if (action == null) {
            action = "/";
        }

        switch (action) {
            case "/search":
                searchRooms(request, response);
                break;
            default:
                listRooms(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getPathInfo();
        if (action == null) {
            action = "/";
        }

        switch (action) {
            case "/add":
                addRoom(request, response);
                break;
            case "/delete":
                deleteRooms(request, response);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/rooms");
                break;
        }
    }

    private void listRooms(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        List<RoomRental> rooms = roomRentalDAO.getAllRooms();
        List<RoomRentalDAO.PaymentType> paymentTypes = roomRentalDAO.getAllPaymentTypes();
        request.setAttribute("rooms", rooms);
        request.setAttribute("paymentTypes", paymentTypes);
        request.getRequestDispatcher("/WEB-INF/views/rooms.jsp").forward(request, response);
    }

    private void searchRooms(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String searchTerm = request.getParameter("search");
        List<RoomRental> rooms = roomRentalDAO.searchRooms(searchTerm);
        List<RoomRentalDAO.PaymentType> paymentTypes = roomRentalDAO.getAllPaymentTypes();
        request.setAttribute("rooms", rooms);
        request.setAttribute("paymentTypes", paymentTypes);
        request.setAttribute("searchTerm", searchTerm);
        request.getRequestDispatcher("/WEB-INF/views/rooms.jsp").forward(request, response);
    }

    private void addRoom(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        String roomCode = request.getParameter("roomCode");
        String tenantName = request.getParameter("tenantName");
        String phoneNumber = request.getParameter("phoneNumber");
        String startDate = request.getParameter("startDate");
        int paymentTypeId = Integer.parseInt(request.getParameter("paymentType"));
        String notes = request.getParameter("notes");

        RoomRental room = new RoomRental();
        room.setRoomCode(roomCode);
        room.setTenantName(tenantName);
        room.setPhoneNumber(phoneNumber);
        room.setStartDate(Date.valueOf(startDate));
        room.setPaymentTypeId(paymentTypeId);
        room.setNotes(notes);

        roomRentalDAO.insertRoom(room);
        response.sendRedirect(request.getContextPath() + "/rooms");
    }

    private void deleteRooms(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        String[] roomCodes = request.getParameterValues("selectedRooms");
        if (roomCodes != null && roomCodes.length > 0) {
            roomRentalDAO.deleteRooms(roomCodes);
        }
        response.sendRedirect(request.getContextPath() + "/rooms");
    }
} 