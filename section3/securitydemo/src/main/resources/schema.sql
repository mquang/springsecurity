--Copied from org.springframework.security.core.userdetails.jdbc>users.ddl
--See org.springframework.security.core.userdetails.jdb.JdbcDaoImpl
create table users(username varchar_ignorecase(50) not null primary key,password varchar_ignorecase(500) not null,enabled boolean not null);
create table authorities (username varchar_ignorecase(50) not null,authority varchar_ignorecase(50) not null,constraint fk_authorities_users foreign key(username) references users(username));
create unique index ix_auth_username on authorities (username,authority);

--See sample sql in org.springframework.security.provisioning.JdbcUserDetailsManager
insert into users (username, password, enabled) values ('quangnm3','1234',true);
--See org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl.loadUserByUsername
--User has no authorities will be treated as 'not found'
insert into authorities (username, authority) values ('quangnm3', 'admin');