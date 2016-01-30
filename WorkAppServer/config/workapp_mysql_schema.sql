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

// Creating session table
create table session (
	id varchar(50) NOT NULL,
	user_id varchar(50) NOT NULL,
	token varchar(50) NOT NULL,
	user_agent varchar(50) NOT NULL,
	logintime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	primary key (id, user_id)
);

// Creating user_metrics table
create table user_metrics (
	id varchar(50) NOT NULL,
	user_id varchar(50) NOT NULL,
	primary key (id, user_id)
);

// End of WorkApp Database and Tables creation



