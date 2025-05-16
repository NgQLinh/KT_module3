<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Quản lý thuê phòng trọ</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .search-box {
            margin: 20px 0;
        }
        .action-buttons {
            margin: 20px 0;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1 class="mt-4 mb-4">Quản lý thuê phòng trọ</h1>

        <!-- Search Box -->
        <div class="search-box">
            <form action="${pageContext.request.contextPath}/rooms/search" method="get" class="row g-3">
                <div class="col-auto">
                    <input type="text" name="search" class="form-control" placeholder="Tìm kiếm..." value="${searchTerm}">
                </div>
                <div class="col-auto">
                    <button type="submit" class="btn btn-primary">Tìm kiếm</button>
                </div>
            </form>
        </div>

        <!-- Action Buttons -->
        <div class="action-buttons">
            <button type="button" class="btn btn-success" data-bs-toggle="modal" data-bs-target="#addRoomModal">
                Tạo mới
            </button>
            <button type="button" class="btn btn-danger" onclick="deleteSelectedRooms()">Xóa</button>
        </div>

        <!-- Rooms Table -->
        <form id="deleteForm" action="${pageContext.request.contextPath}/rooms/delete" method="post">
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th><input type="checkbox" onclick="toggleAll(this)"></th>
                        <th>Mã phòng trọ</th>
                        <th>Tên người thuê trọ</th>
                        <th>Số điện thoại</th>
                        <th>Ngày bắt đầu thuê</th>
                        <th>Hình thức thanh toán</th>
                        <th>Ghi chú</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="room" items="${rooms}">
                        <tr>
                            <td><input type="checkbox" name="selectedRooms" value="${room.roomCode}"></td>
                            <td>${room.roomCode}</td>
                            <td>${room.tenantName}</td>
                            <td>${room.phoneNumber}</td>
                            <td>${room.startDate}</td>
                            <td>${room.paymentTypeName}</td>
                            <td>${room.notes}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </form>

        <!-- Add Room Modal -->
        <div class="modal fade" id="addRoomModal" tabindex="-1">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Thêm phòng trọ mới</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                    </div>
                    <form action="${pageContext.request.contextPath}/rooms/add" method="post" onsubmit="return validateForm()">
                        <div class="modal-body">
                            <div class="mb-3">
                                <label class="form-label">Mã phòng trọ</label>
                                <input type="text" class="form-control" name="roomCode" required pattern="PT-[0-9]{3}" 
                                       title="Mã phòng phải có định dạng PT-XXX (X là số)">
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Tên người thuê trọ</label>
                                <input type="text" class="form-control" name="tenantName" required 
                                       minlength="5" maxlength="50">
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Số điện thoại</label>
                                <input type="text" class="form-control" name="phoneNumber" required 
                                       pattern="[0-9]{10}" title="Số điện thoại phải có 10 chữ số">
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Ngày bắt đầu thuê</label>
                                <input type="date" class="form-control" name="startDate" required>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Hình thức thanh toán</label>
                                <select class="form-select" name="paymentType" required>
                                    <c:forEach var="type" items="${paymentTypes}">
                                        <option value="${type.id}">${type.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Ghi chú</label>
                                <textarea class="form-control" name="notes" maxlength="200"></textarea>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                            <button type="submit" class="btn btn-primary">Tạo mới</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        function toggleAll(source) {
            const checkboxes = document.getElementsByName('selectedRooms');
            for (let checkbox of checkboxes) {
                checkbox.checked = source.checked;
            }
        }

        function deleteSelectedRooms() {
            const checkboxes = document.getElementsByName('selectedRooms');
            let selected = false;
            for (let checkbox of checkboxes) {
                if (checkbox.checked) {
                    selected = true;
                    break;
                }
            }
            
            if (!selected) {
                alert('Vui lòng chọn ít nhất một phòng để xóa');
                return;
            }

            if (confirm('Bạn có muốn xóa thông tin thuê trọ đã chọn hay không?')) {
                document.getElementById('deleteForm').submit();
            }
        }

        function validateForm() {
            const startDate = document.getElementsByName('startDate')[0].value;
            const today = new Date();
            const inputDate = new Date(startDate);
            
            if (inputDate > today) {
                alert('Ngày bắt đầu thuê không được lớn hơn ngày hiện tại');
                return false;
            }
            return true;
        }
    </script>
</body>
</html> 