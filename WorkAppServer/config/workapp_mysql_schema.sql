// Creating WorkApp Database and Tables
drop database if exists `workapp` ;
create database `workapp`;
use workapp;
grant all privileges on workapp.* to 'user'@'%' identified by 'user';

// Creating user table
drop table if exists `user` ;
create table user (
	id varchar(50) NOT NULL,
	service_key varchar(50) NOT NULL,
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

// Creating news_feed table
create table news_feed (
	id varchar(50) NOT NULL,
	user_id varchar(50) NOT NULL,
	project_id varchar(50) NOT NULL,
	task_id varchar(50) NOT NULL,
	status_msg text,
	is_seen bool,
	last_accesstime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	primary key (id)
);

// Creating project table
create table project (
	id varchar(50) NOT NULL,
	user_id varchar(50) NOT NULL,
	feed_id varchar(50) NOT NULL,
	feed_msg text,
	is_seen bool,
	primary key (user_id, feed_id)
);

// Creating task table
create table task (
	id varchar(50) NOT NULL,
	user_id varchar(50) NOT NULL,
	feed_id varchar(50) NOT NULL,
	feed_msg text,
	is_seen bool,
	primary key (user_id, feed_id)
);


// Creating user_metric table
create table user_metric (
	id varchar(50) NOT NULL,
	user_id varchar(50) NOT NULL,
	session_id varchar(50) NOT NULL,
	logintime TIMESTAMP NOT NULL,
	logout TIMESTAMP NOT NULL,
	duration BIGINT(20) NOT NULL,
	primary key (id, user_id, session_id)
);

// End of WorkApp Database and Tables creation



