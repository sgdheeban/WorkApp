// Creating WorkApp Database and Tables
drop database if exists `workapp` ;
create database `workapp`;
use workapp;
grant all privileges on workapp.* to 'user'@'%' identified by 'user';

// Creating user table
drop table if exists `user` ;
create table user (
	id varchar(50) NOT NULL,
	email varchar(100) NOT NULL,
	password varchar(100) NOT NULL,
	phone varchar(20),
	first_name varchar(50) NOT NULL,
	last_name varchar(50) NOT NULL,
	is_customer bool,
	is_individual bool,
	employer_name varchar(50),
	occupation varchar(50),
	skills text,
	is_owner bool,
	primary key (email)
);
// End of WorkApp Database and Tables creation

// insert into user (id, email, password, first_name, last_name) values (123, 'sgdheeban@gmail.com', 'sachin10', 'dheeban','sg');
