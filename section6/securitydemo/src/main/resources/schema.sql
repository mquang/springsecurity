create table IF NOT EXISTS employee(
	id int auto_increment,
	email varchar_ignorecase(50) not null,
	pwd varchar_ignorecase(500) not null,
	role varchar(45) not null
);

insert into employee (email, pwd, role) values ('quangnm2@bidv.com.vn', '$2a$12$AboCk9DsLU.yRpCXFMRPcekHwDv8CtzkWGHLRMNeMQxGW3BYG/Y9a', 'admin');
insert into employee (email, pwd, role) values ('anhdtn7@bidv.com.vn', '$2a$10$JGpO/mMpb5kGoL09Bu0.Fetkz1IYnYv5qCE0fzL0Ige/M4pGvcUja', 'admin');
