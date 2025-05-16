DROP DATABASE IF EXISTS RentalRoom;

CREATE DATABASE IF NOT EXISTS RentalRoom;
USE RentalRoom;

CREATE TABLE PaymentType (
    payment_type_id INT PRIMARY KEY AUTO_INCREMENT,
    payment_type_name VARCHAR(20) NOT NULL
);

CREATE TABLE RoomRental (
    room_id INT PRIMARY KEY AUTO_INCREMENT,
    room_code VARCHAR(10) NOT NULL UNIQUE,
    tenant_name VARCHAR(50) NOT NULL,
    phone_number VARCHAR(10) NOT NULL,
    start_date DATE NOT NULL,
    payment_type_id INT NOT NULL,
    notes VARCHAR(200),
    FOREIGN KEY (payment_type_id) REFERENCES PaymentType(payment_type_id)
);

INSERT INTO PaymentType (payment_type_name) VALUES 
('Theo tháng'),
('Theo quý'),
('Theo năm');

INSERT INTO RoomRental (room_code, tenant_name, phone_number, start_date, payment_type_id, notes) VALUES 
('PT-001', 'Nguyễn Thành Đạt', '0123456123', '2020-10-10', 1, 'Phòng có điều hòa'),
('PT-002', 'Nguyễn Minh Dũng', '0123456456', '2020-10-10', 2, NULL),
('PT-003', 'Trần Kiên Cường', '0123456789', '2020-10-10', 3, 'Phòng có điều hòa'),
('PT-004', 'Nguyễn Quyền linh', '0123456666', '2020-10-10', 1, 'Phòng có điều hòa');