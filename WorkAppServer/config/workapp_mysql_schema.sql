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
	country varchar(50) NOT NULL,
	zipcode varchar(50) NOT NULL,
	is_individual bool,
	is_owner bool,
	company_name varchar(100),
	board_ids text,
	is_worker bool,
	profile_pic blob,
	resume blob,
	employment_profile varchar(100),
	education_profile varchar(100),
	rating bigint(50) default 0,
	is_onsiteOK bool,
	is_equityOK bool,
	occupation_id varchar(50) NOT NULL,
	skill_ids text,
	primary key (email)
);

// Creating session table
drop table if exists `session` ;
create table session (
	id varchar(50) NOT NULL,
	user_id varchar(50) NOT NULL,
	token varchar(50) NOT NULL,
	user_agent varchar(50) NOT NULL,
	logintime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	primary key (id, user_id)
);

// Creating board table
drop table if exists `board` ;
create table board (
	id varchar(50) NOT NULL,
	name varchar(100) NOT NULL,
	description text,
	artifact_loc varchar(100) NOT NULL,
	creator_id varchar(50) NOT NULL,
	collaborators_info text,
	work_ids text,
	version bigint(20) default 0,
	status_id varchar(50) NOT NULL,
	is_private bool,
	is_favoritie bool,
	milestone_ids text,
	primary key (id)
);

// Creating work table
drop table if exists `work` ;
create table work (
	id varchar(50) NOT NULL,
	name varchar(100) NOT NULL,
	description text,
	creator_id varchar(50) NOT NULL,
	occupation_id varchar(50) NOT NULL,
	skill_ids text,
	version bigint(20) default 0,
	status_id varchar(50) NOT NULL,
	is_favoritie bool,
	milestone_id varchar(50) NOT NULL,
	deadline TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	primary key (id)
);

// Creating news_feed table
drop table if exists `news_feed` ;
create table news_feed (
	id varchar(50) NOT NULL,
	user_id varchar(50) NOT NULL,
	board_id varchar(50) NOT NULL,
	work_id varchar(50) NOT NULL,
	feed_type_id varchar(50) NOT NULL,
	msg text,
	is_seen bool,
	last_accesstime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	primary key (id)
);

// Creating comment table
drop table if exists `comment` ;
create table comment (
	id varchar(50) NOT NULL,
	board_id varchar(50) NOT NULL,
	work_id varchar(50) NOT NULL,
	creator_id varchar(50) NOT NULL,
	last_edittime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	msg text,
	likes BIGINT(20) NOT NULL,
	dislikes BIGINT(20) NOT NULL,	
	primary key (id)
);

// Creating occupation_catalog table
drop table if exists `occupation_catalog` ;
create table occupation_catalog (
	id varchar(50) NOT NULL,
	name varchar(50) NOT NULL,
	primary key (id)
);

// Creating status table
drop table if exists `status` ;
create table status (
	id varchar(50) NOT NULL,
	name varchar(100) NOT NULL,
	primary key (id)
);

// Creating milestone table
drop table if exists `milestone` ;
create table milestone (
	id varchar(50) NOT NULL,
	board_id varchar(50) NOT NULL,
	name varchar(100) NOT NULL,
	primary key (id)
);

// Creating role table
drop table if exists `role` ;
create table role (
	id varchar(50) NOT NULL,
	name varchar(50) NOT NULL,
	primary key (id)
);

// Creating feed_type table
drop table if exists `feed_type` ;
create table feed_type (
	id varchar(50) NOT NULL,
	name varchar(50) NOT NULL,
	primary key (id)
);

// Creating skill table
drop table if exists `skill` ;
create table skill (
	id varchar(50) NOT NULL,
	occupation_id varchar(50) NOT NULL,
	name varchar(50) NOT NULL,
	primary key (id)
);

// Creating skill_user_index table
drop table if exists `skill_user_index` ;
create table skill_user_index (
	id varchar(50) NOT NULL,
	user_ids text,
	primary key (id)
);

// Creating occupation_user_index table
drop table if exists `occupation_user_index` ;
create table occupation_user_index (
	id varchar(50) NOT NULL,
	user_ids text,
	primary key (id)
);

// Creating user_metric table
drop table if exists `user_metric` ;
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



