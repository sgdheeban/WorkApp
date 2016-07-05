drop database if exists `testdb`;
create database `testdb`;
use testdb;
//test-comment

//Creating user table
drop table if exists user;
create table user (
	name varchar(255),	
	age int
);

--test-dash
select * from user;

//Creating kvdb Database
drop database if exists `kvdb`;
create database `kvdb`;
use kvdb;

//Creating kv_table
drop table if exists kv_table;
create table kv_table (
	key_str varchar(255),	
	value_str varchar(255),
	last_accessed_time timestamp,
	PRIMARY KEY (key_str)
);