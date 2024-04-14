CREATE DATABASE HOSTEL_MANAGEMENT;
USE HOSTEL_MANAGEMENT;
sp_changedbowner admin;


CREATE TABLE ROOM (
    id INTEGER PRIMARY KEY IDENTITY(1,1),
    name TEXT NOT NULL,
    status INTEGER NOT NULL,
    type TEXT NOT NULL,
    price MONEY NOT NULL,
);
CREATE TABLE SERVICE (
    id INTEGER PRIMARY KEY IDENTITY(1,1),
    name TEXT NOT NULL,
    price MONEY NOT NULL
);

CREATE TABLE BILL (
    id INTEGER PRIMARY KEY IDENTITY(1,1),
    room_id INTEGER,
    cus_name TEXT NOT NULL,
    checkIn DATETIME NOT NULL,
    checkOut DATETIME,
    total MONEY DEFAULT 0,
    isPaid INT DEFAULT 0,
    FOREIGN KEY (room_id) REFERENCES ROOM(id)
);

CREATE TABLE BILLDETAIL (
    id INTEGER PRIMARY KEY IDENTITY(1,1),
    bill_id INTEGER,
    service_id INTEGER,
    quantity INT NOT NULL,
    FOREIGN KEY (bill_id) REFERENCES BILL(id),
    FOREIGN KEY (service_id) REFERENCES SERVICE(id)
);

insert into ROOM values 
('SAM 1', 0, 'TWIN', 50),
('SAM 2', 0, 'TWIN', 50);

insert into SERVICE values
('Water', 2),
('Shampoo', 1);

insert into BILL(room_id, cus_name, checkIn) values (1, 'Kaito', '2024-04-14');
insert into BILL(room_id, cus_name, checkIn, isPaid) values (1, 'Kaito2', '2024-04-14', 1);
insert into BILLDETAIL(bill_id, service_id, quantity) values (1, 1, 1);
insert into BILLDETAIL(bill_id, service_id, quantity) values (2, 2, 2);

select * from BILL b join BILLDETAIL bd on b.id = bd.bill_id where b.isPaid=0 and bd.service_id=1;