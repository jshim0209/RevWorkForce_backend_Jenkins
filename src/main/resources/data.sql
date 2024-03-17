DROP TABLE IF EXISTS leave_type;
DROP TABLE IF EXISTS leave_request;
DROP TABLE IF EXISTS notifications;
DROP TABLE IF EXISTS account;

CREATE TABLE IF NOT EXISTS account (
	account_id serial PRIMARY KEY,
	first_name varchar(64),
	last_name varchar(64),
	email varchar(64),
	password bytea,
	phone_number char(13),
	address varchar(128),
	account_type varchar(8) check (account_type in ('employee', 'manager', 'admin')),
	managerId int,
	birthday date,
	first_workday date
);

CREATE TABLE IF NOT EXISTS password_reset_request (
    request_id SERIAL PRIMARY KEY,
    email varchar(64)
);

CREATE TABLE IF NOT EXISTS leave_type (
    type_name varchar(255) PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS leave_request (
    id SERIAL PRIMARY KEY,
    account_id integer NOT NULL,
    start_date date NOT NULL,
    end_date date NOT NULL,
    leave_type varchar(255) NOT NULL,
    leave_status integer DEFAULT 0,
    FOREIGN KEY (leave_type) REFERENCES leave_type(type_name)
);

CREATE TABLE IF NOT EXISTS notifications (
    account_id integer NOT NULL,
    description varchar(255) NOT NULL,
    create_date timestamp NOT NULL DEFAULT now(),
    link varchar(255),
    PRIMARY KEY (description, create_date)
);

-- Constants

INSERT INTO leave_type (type_name) VALUES ('PTO');
INSERT INTO leave_type (type_name) VALUES ('Vacation');
INSERT INTO leave_type (type_name) VALUES ('Volunteer Service');
INSERT INTO leave_type (type_name) VALUES ('Sick');
INSERT INTO leave_type (type_name) VALUES ('Unpaid');

create table if not exists leave (
    id serial primary key,
    accountid int,
    type varchar(64),
    hours int,
    foreign key (accountid) references account(account_id)
);

create table if not exists holiday (
    id serial primary key,
    name varchar(64),
    start_date date,
    end_date date
);

-- Testing data

insert into holiday (name, start_date, end_date) values
('Christmas','0001-12-25','0001-12-25'),
('Mandatory Go-Home Period','0001-06-01','0001-06-10'),
('Easter','0001-03-31','0001-03-31'),
('Memorial Day Weekend','0001-05-25','0001-05-27'),
('New Year''s Day','0001-01-01','0001-01-01');

insert into leave (accountid, type, hours) values
(1,'PTO',100),
(1,'Vacation',2),
(1,'Sick',66),
(2,'PTO',55),
(2,'Vacation',23);

INSERT INTO leave_request (id, account_id, start_date, end_date, leave_type, leave_status) VALUES
    (999, 2, '2023-11-02', '2023-11-15', 'PTO', 1),
    (998, 2, '2024-01-14', '2024-01-17', 'Sick', 1),
    (997, 3, '2024-02-25', '2024-02-26', 'Vacation', -1),
    (996, 3, '2024-02-26', '2024-02-26', 'Unpaid', 0),
    (995, 1, '2022-09-10', '2022-10-25', 'Unpaid', 1),
    (994, 3, '2024-03-14', '2024-03-16', 'Unpaid', 0),
    (993, 3, '2023-12-12', '2023-12-27', 'Vacation', 1);

INSERT INTO performance_review (employee_id, deliverables, achievements, areas_of_improvement, goals, targets, deadline, weightage)
VALUES
(1, 'This is my first deliverables!', 'This is my first achievements!', 'This is my first areas of improvements!', 'This is my first goals', 'This is my first targets', '90 Days', 3),
(2, 'This is my first deliverables!', 'This is my first achievements!', 'This is my first areas of improvements!', 'This is my first goals', 'This is my first targets', '180 Days', 4),
(2, 'This is my second deliverables!', 'This is my second achievements!', 'This is my second areas of improvements!', 'This is my second goals', 'This is my second targets', '90 Days', 3),
(2, 'This is my third deliverables!', 'This is my third achievements!', 'This is my third areas of improvements!', 'This is my third goals', 'This is my third targets', '360 Days', 5);


INSERT INTO notifications (account_id, description) VALUES
    (1, 'John Smith has approved your leave request for 2024/02/21'),
    (1, 'John Smith has rejected your leave request for 2024/03/01'),
    (1, 'Jane Doe has updated your performance review for Y2023'),
    (1, 'John Workforce has approved your leave request for 2023/01/01');
