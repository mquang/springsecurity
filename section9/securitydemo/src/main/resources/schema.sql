create table IF NOT EXISTS employee(
	employee_id int auto_increment,
	name varchar(100) NOT NULL,
	email varchar_ignorecase(50) not null,
	pwd varchar_ignorecase(500) not null,
	role varchar(45) not null
);

insert into employee (email, name, pwd, role) values ('quangnm2@bidv.com.vn', 'Nguyễn Minh Quang', '$2a$12$AboCk9DsLU.yRpCXFMRPcekHwDv8CtzkWGHLRMNeMQxGW3BYG/Y9a', 'admin');
insert into employee (email, name, pwd, role) values ('anhdtn7@bidv.com.vn', 'Đỗ Thị Ngọc Ánh', '$2a$10$JGpO/mMpb5kGoL09Bu0.Fetkz1IYnYv5qCE0fzL0Ige/M4pGvcUja', 'user');

CREATE TABLE IF NOT EXISTS accounts (
  employee_id int NOT NULL,
  account_number int NOT NULL,
  account_type varchar(100) NOT NULL,
  branch_address varchar(200) NOT NULL,
  create_dt date DEFAULT NULL,
  PRIMARY KEY (account_number),
  foreign key (employee_id) references employee(employee_id)
);

INSERT INTO accounts (employee_id, account_number, account_type, branch_address, create_dt)
 VALUES (1, 1865764534, 'Savings', '120 So Giao dich 1', CURRENT_DATE());
 
 CREATE TABLE IF NOT EXISTS  account_transactions (
  transaction_id varchar(200) NOT NULL,
  account_number int NOT NULL,
  employee_id int NOT NULL,
  transaction_dt date NOT NULL,
  transaction_summary varchar(200) NOT NULL,
  transaction_type varchar(100) NOT NULL,
  transaction_amt int NOT NULL,
  closing_balance int NOT NULL,
  create_dt date DEFAULT NULL,
  PRIMARY KEY (transaction_id),
  foreign key (account_number) references accounts(account_number),
  foreign key (employee_id) references employee(employee_id)
);

INSERT INTO account_transactions (transaction_id, account_number, employee_id, transaction_dt, transaction_summary, transaction_type,transaction_amt, closing_balance, create_dt)  
VALUES (RANDOM_UUID(), 1865764534, 1, (DATEADD(DAY, -7, CURRENT_DATE)), 'Coffee Shop', 'Withdrawal', 30, 34500, (DATEADD(DAY, -7, CURRENT_DATE)));

INSERT INTO account_transactions (transaction_id, account_number, employee_id, transaction_dt, transaction_summary, transaction_type,transaction_amt, closing_balance, create_dt)  
VALUES (RANDOM_UUID(), 1865764534, 1, (DATEADD(DAY, -6, CURRENT_DATE)), 'Uber', 'Withdrawal', 100, 34400, (DATEADD(DAY, -6, CURRENT_DATE)));

INSERT INTO account_transactions (transaction_id, account_number, employee_id, transaction_dt, transaction_summary, transaction_type,transaction_amt, closing_balance, create_dt)  
VALUES (RANDOM_UUID(), 1865764534, 1, (DATEADD(DAY, -5, CURRENT_DATE)), 'OnlineTransfer', 'Deposit', 500, 34900, (DATEADD(DAY, -5, CURRENT_DATE)));

INSERT INTO account_transactions (transaction_id, account_number, employee_id, transaction_dt, transaction_summary, transaction_type,transaction_amt, closing_balance, create_dt)  
VALUES (RANDOM_UUID(), 1865764534, 1, (DATEADD(DAY, -4, CURRENT_DATE)), 'Amazon.com', 'Withdrawal', 600, 34300, (DATEADD(DAY, -4, CURRENT_DATE)));

INSERT INTO account_transactions (transaction_id, account_number, employee_id, transaction_dt, transaction_summary, transaction_type,transaction_amt, closing_balance, create_dt)  
VALUES (RANDOM_UUID(), 1865764534, 1, (DATEADD(DAY, -3, CURRENT_DATE)), 'Shopee', 'Withdrawal', 100, 34200, (DATEADD(DAY, -3, CURRENT_DATE)));


CREATE TABLE IF NOT EXISTS notice_details (
  notice_id int NOT NULL AUTO_INCREMENT,
  notice_summary varchar(200) NOT NULL,
  notice_details varchar(500) NOT NULL,
  notic_beg_dt date NOT NULL,
  notic_end_dt date DEFAULT NULL,
  create_dt date DEFAULT NULL,
  update_dt date DEFAULT NULL,
  PRIMARY KEY (notice_id)
);

INSERT INTO notice_details ( notice_summary, notice_details, notic_beg_dt, notic_end_dt, create_dt, update_dt)
VALUES ('Home Loan Interest rates reduced', 'Home loan interest rates are reduced as per the goverment guidelines. The updated rates will be effective immediately',
(DATEADD(DAY, -30, CURRENT_DATE)), (DATEADD(DAY, 30, CURRENT_DATE)), CURRENT_DATE(), null);

INSERT INTO notice_details ( notice_summary, notice_details, notic_beg_dt, notic_end_dt, create_dt, update_dt)
VALUES ('Net Banking Offers', 'Customers who will opt for Internet banking while opening a saving account will get a $50 amazon voucher',
(DATEADD(DAY, -30, CURRENT_DATE)), (DATEADD(DAY, 30, CURRENT_DATE)), CURRENT_DATE(), null);

INSERT INTO notice_details ( notice_summary, notice_details, notic_beg_dt, notic_end_dt, create_dt, update_dt)
VALUES ('Mobile App Downtime', 'The mobile application of the EazyBank will be down from 2AM-5AM on 12/05/2020 due to maintenance activities',
(DATEADD(DAY, -30, CURRENT_DATE)), (DATEADD(DAY, 30, CURRENT_DATE)), CURRENT_DATE(), null);

INSERT INTO notice_details ( notice_summary, notice_details, notic_beg_dt, notic_end_dt, create_dt, update_dt)
VALUES ('E Auction notice', 'There will be a e-auction on 12/08/2020 on the Bank website for all the stubborn arrears.Interested parties can participate in the e-auction',
(DATEADD(DAY, -30, CURRENT_DATE)), (DATEADD(DAY, 30, CURRENT_DATE)), CURRENT_DATE(), null);

INSERT INTO notice_details ( notice_summary, notice_details, notic_beg_dt, notic_end_dt, create_dt, update_dt)
VALUES ('Launch of Millennia Cards', 'Millennia Credit Cards are launched for the premium customers of EazyBank. With these cards, you will get 5% cashback for each purchase',
(DATEADD(DAY, -30, CURRENT_DATE)), (DATEADD(DAY, 30, CURRENT_DATE)), CURRENT_DATE(), null);

INSERT INTO notice_details ( notice_summary, notice_details, notic_beg_dt, notic_end_dt, create_dt, update_dt)
VALUES ('COVID-19 Insurance', 'EazyBank launched an insurance policy which will cover COVID-19 expenses. Please reach out to the branch for more details',
(DATEADD(DAY, -30, CURRENT_DATE)), (DATEADD(DAY, 30, CURRENT_DATE)), CURRENT_DATE(), null);

CREATE TABLE IF NOT EXISTS contact_messages (
  contact_id varchar(50) NOT NULL,
  contact_name varchar(50) NOT NULL,
  contact_email varchar(100) NOT NULL,
  subject varchar(500) NOT NULL,
  message varchar(2000) NOT NULL,
  create_dt date DEFAULT NULL,
  PRIMARY KEY (contact_id)
);

CREATE TABLE IF NOT EXISTS authorities (
  id int NOT NULL AUTO_INCREMENT,
  employee_id int NOT NULL,
  name varchar(50) NOT NULL,
  PRIMARY KEY (id),
  foreign key (employee_id) references employee(employee_id)
);

INSERT INTO authorities (employee_id, name) VALUES (1, 'VIEWACCOUNT');
INSERT INTO authorities (employee_id, name) VALUES (1, 'VIEWCARDS');
INSERT INTO authorities (employee_id, name) VALUES (1, 'VIEWBALANCE');

DELETE FROM authorities;

--When defining a role, its name should start with the ROLE_ prefix. This prefix specifies the difference between a role and an authority.
INSERT INTO authorities (employee_id, name) VALUES (1, 'ROLE_USER');
INSERT INTO authorities (employee_id, name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO authorities (employee_id, name) VALUES (2, 'ROLE_ADMIN');