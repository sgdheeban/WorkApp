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
	profile_pic blob,
	country varchar(50) NOT NULL,
	zipcode varchar(50) NOT NULL,
	is_individual bool,
	is_owner bool, 
	occupation_id varchar(50) NOT NULL,
	company_name varchar(100),
	is_worker bool,
	resume blob,
	overall_rating bigint(50) default 0,
	is_onsiteOK bool,
	is_equityOK bool,
	primary key (email)
);

// Creating user_experience table
drop table if exists `user_experience` ;
create table user_experience (
	id varchar(50) NOT NULL,
	user_id varchar(50) NOT NULL,
	institute_name varchar(50) NOT NULL,
	position_title varchar(100) NOT NULL,
	employment_description text NOT NULL,
	starttime TIMESTAMP,
	endtime TIMESTAMP,
	recommendation_1 text,
	recommendation_2 text,
	recommendation_3 text,
	project_name_1 varchar(50),
	project_name_2 varchar(50),
	project_name_3 varchar(50),
	project_summary_1 text,
	project_summary_2 text,
	project_summary_3 text,
	project_artifact_loc1 varchar(100),
	project_artifact_loc2 varchar(100),
	project_artifact_loc3 varchar(100),
	award_name_1 varchar(50),
	award_name_2 varchar(50),
	award_name_3 varchar(50),
	award_summary_1 text,
	award_summary_2 text,
	award_summary_3 text,
	primary key (id)
);

// Creating user_rating table
drop table if exists `user_rating` ;
create table user_rating (
	id varchar(50) NOT NULL,
	user_id varchar(50) NOT NULL,
	project_id varchar(50) NOT NULL,
	is_worker bool NOT NULL,
	overall_rating bigint(50) default 0,
	communication_rating bigint(50) default 0,
	payment_rating bigint(50) default 0,
	quality_rating bigint(50) default 0,
	deadline_rating bigint(50) default 0,
	professionalism_rating bigint(50) default 0,
	primary key (user_id,project_id,is_worker)
);

// Creating user_session table
drop table if exists `user_session` ;
create table user_session (
	id varchar(50) NOT NULL,
	user_id varchar(50) NOT NULL,
	user_agent varchar(50) NOT NULL,
	logintime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
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

// Creating skill table
drop table if exists `skill` ;
create table skill (
	id varchar(50) NOT NULL,
	occupation_id varchar(50) NOT NULL,
	name varchar(50) NOT NULL,
	primary key (id)
);

// Creating role table
drop table if exists `role` ;
create table role (
	id varchar(50) NOT NULL,
	name varchar(50) NOT NULL,
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

// Creating feed_type table
drop table if exists `feed_type` ;
create table feed_type (
	id varchar(50) NOT NULL,
	name varchar(50) NOT NULL,
	primary key (id)
);

// Creating skill_user_index table
drop table if exists `skill_user_index` ;
create table skill_user_index (
	id varchar(50) NOT NULL,
	skill_id varchar(50) NOT NULL,
	user_id varchar(50) NOT NULL,
	primary key (skill_id, user_id)
);

// Creating occupation_user_index table
drop table if exists `occupation_user_index` ;
create table occupation_user_index (
	id varchar(50) NOT NULL,
	occupation_id varchar(50) NOT NULL,
	user_id varchar(50) NOT NULL,
	primary key (occupation_id,user_id)
);

// Creating skill_work_index table
drop table if exists `skill_work_index` ;
create table skill_work_index (
	id varchar(50) NOT NULL,
	skill_id varchar(50) NOT NULL,
	work_id varchar(50) NOT NULL,
	primary key (skill_id, work_id)
);

// Creating occupation_work_index table
drop table if exists `occupation_work_index` ;
create table occupation_work_index (
	id varchar(50) NOT NULL,
	occupation_id varchar(50) NOT NULL,
	work_id varchar(50) NOT NULL,
	primary key (occupation_id,work_id)
);

// Creating board_collaborator table
drop table if exists `board_collaborator_index` ;
create table board_collaborator (
	id varchar(50) NOT NULL,
	board_id varchar(50) NOT NULL,
	user_id varchar(50) NOT NULL,
	role_id varchar(50) NOT NULL,
	is_favorite bool,
	primary key (board_id,user_id)
);

// Creating work_favorite table
drop table if exists `work_favorite_index` ;
create table work_favorite (
	id varchar(50) NOT NULL,
	user_id varchar(50) NOT NULL,
	work_id varchar(50) NOT NULL,
	primary key (user_id,work_id)
);

// Creating board table
drop table if exists `board` ;
create table board (
	id varchar(50) NOT NULL,
	creator_id varchar(50) NOT NULL,
	name varchar(100) NOT NULL,
	description text,
	artifact_loc varchar(100) NOT NULL,
	version bigint(20) default 0,
	status_id varchar(50) NOT NULL,
	is_private bool,
	primary key (id)
);

// Creating work table
drop table if exists `work` ;
create table work (
	id varchar(50) NOT NULL,
	board_id varchar(50) NOT NULL,
	name varchar(100) NOT NULL,
	description text,
	creator_id varchar(50) NOT NULL,
	createtime TIMESTAMP,
	last_editor_id varchar(50) NOT NULL,
	last_edittime TIMESTAMP,
	version bigint(20) default 0,
	status_id varchar(50) NOT NULL,
	milestone_id varchar(50) NOT NULL,
	deadline TIMESTAMP,
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



