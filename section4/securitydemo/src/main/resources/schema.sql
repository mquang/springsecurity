create table IF NOT EXISTS employee(
	id int auto_increment,
	email varchar_ignorecase(50) not null,
	pwd varchar_ignorecase(500) not null,
	role varchar(45) not null
);

insert into employee (email, pwd, role) values ('quangnm2@bidv.com.vn', '123123', 'admin');
insert into employee (email, pwd, role) values ('anhdtn7@bidv.com.vn', '123123', 'admin');
