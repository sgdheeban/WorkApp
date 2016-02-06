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
	create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
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
	token varchar(50) NOT NULL,
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

// Inserting values into occupation_catalog table
insert into `occupation_catalog` values('0', 'Personal');
insert into `occupation_catalog` values('1', 'Brainstorming');
insert into `occupation_catalog` values('2', 'Meeting');
insert into `occupation_catalog` values('3', 'Event');
insert into `occupation_catalog` values('4', 'Software Engineering');
insert into `occupation_catalog` values('5', 'Financial Auditing');
insert into `occupation_catalog` values('6', 'Legal Service');
insert into `occupation_catalog` values('7', 'Investment');
insert into `occupation_catalog` values('8', 'Business');
insert into `occupation_catalog` values('9', 'Management');
insert into `occupation_catalog` values('10', 'Marketing');
insert into `occupation_catalog` values('11', 'Sales');
insert into `occupation_catalog` values('12', 'Others');
insert into `occupation_catalog` values('13', 'Software Operations');
insert into `occupation_catalog` values('14', 'Do-It-Yourself');
insert into `occupation_catalog` values('15', 'Dancing');
insert into `occupation_catalog` values('16', 'Singing');
insert into `occupation_catalog` values('17', 'Quilling');
insert into `occupation_catalog` values('18', 'Photography');
insert into `occupation_catalog` values('19', 'Painting');
insert into `occupation_catalog` values('20', 'Drilling');
insert into `occupation_catalog` values('21', 'Plumbing');
insert into `occupation_catalog` values('22', 'Pilot');
insert into `occupation_catalog` values('23', 'Driving');
insert into `occupation_catalog` values('24', 'Dining');
insert into `occupation_catalog` values('25', 'School Home Work');
insert into `occupation_catalog` values('26', 'Teaching');

// Creating skill table
drop table if exists `skill` ;
create table skill (
	id varchar(50) NOT NULL,
	occupation_id varchar(50) NOT NULL,
	name varchar(50) NOT NULL,
	primary key (id,occupation_id)
);

// Inserting values into skill table
insert into `skill` values('0', '4', 'Backend');
insert into `skill` values('1', '4', 'Frontend');
insert into `skill` values('2', '4', 'Scalability');
insert into `skill` values('3', '4', 'High Performance');
insert into `skill` values('4', '4', 'Java');
insert into `skill` values('5', '4', 'C++');
insert into `skill` values('6', '4', 'C');
insert into `skill` values('7', '4', 'Web App');
insert into `skill` values('8', '4', 'Android App');
insert into `skill` values('9', '4', 'iOS App');
insert into `skill` values('10', '4', 'JavaScript');
insert into `skill` values('11', '4', 'NodeJS');
insert into `skill` values('12', '4', 'SQL');
insert into `skill` values('13', '4', 'Big Data');
insert into `skill` values('14', '4', 'Algorithms');
insert into `skill` values('15', '4', 'Virtual Reality');
insert into `skill` values('16', '4', 'Virtual Assistant');
insert into `skill` values('17', '4', 'Scripting');
insert into `skill` values('0', '13', 'Monitoring & Alert');
insert into `skill` values('1', '13', 'Production Support');
insert into `skill` values('2', '13', 'Documentation');

// Creating milestone table
drop table if exists `milestone` ;
create table milestone (
	id varchar(50) NOT NULL,
	occupation_id varchar(50) NOT NULL,
	name varchar(50) NOT NULL,
	expiry_date TIMESTAMP,
	primary key (id,occupation_id)
);

// Inserting values into milestone table
insert into `milestone` (id,occupation_id,name) values('0', '4', 'Design');
insert into `milestone` (id,occupation_id,name) values('1', '4', 'Implementation');
insert into `milestone` (id,occupation_id,name) values('2', '4', 'Testing');
insert into `milestone` (id,occupation_id,name) values('3', '4', 'Release');
insert into `milestone` (id,occupation_id,name) values('4', '4', 'Bug Fixes');
insert into `milestone` (id,occupation_id,name) values('0', '13', 'Monitoring');
insert into `milestone` (id,occupation_id,name) values('1', '13', 'P0 Support');
insert into `milestone` (id,occupation_id,name) values('2', '13', 'P1 Support');
insert into `milestone` (id,occupation_id,name) values('3', '13', 'P2 Support');
insert into `milestone` (id,occupation_id,name) values('4', '13', 'P3 Support');

// Creating status table
drop table if exists `status` ;
create table status (
	id varchar(50) NOT NULL,
	name varchar(100) NOT NULL,
	primary key (id)
);

// Inserting values into status table
insert into `status` values('0', 'todo');
insert into `status` values('1', 'inprogress');
insert into `status` values('2', 'done');

// Creating role table
drop table if exists `role` ;
create table role (
	id varchar(50) NOT NULL,
	name varchar(50) NOT NULL,
	primary key (id)
);

// Inserting values into role table
insert into `role` values('0', 'owner');
insert into `role` values('1', 'admin');
insert into `role` values('2', 'editor');
insert into `role` values('3', 'reader');

// Creating board_milestone table
drop table if exists `board_milestone` ;
create table board_milestone (
	id varchar(50) NOT NULL,
	board_id varchar(50) NOT NULL,
	milestone_id varchar(50) NOT NULL,
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
	is_expired bool,
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
	assignee_id varchar(50) NOT NULL,
	last_editor_id varchar(50) NOT NULL,
	last_edittime TIMESTAMP,
	version bigint(20) default 0,
	placement_inboard bigint(20) default 0,
	status_id varchar(50) NOT NULL,
	board_milestone_id varchar(50) NOT NULL,
	deadline TIMESTAMP,
	primary key (id)
);

// Creating talent_recommendation table
drop table if exists `talent_recommendation` ;
create table talent_recommendation (
	id varchar(50) NOT NULL,
	is_expired bool,
	expiry_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	work_id varchar(50) NOT NULL,
	user_id varchar(50) NOT NULL,
	rank bigint(20) default 0,
	primary key (work_id, user_id)
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



