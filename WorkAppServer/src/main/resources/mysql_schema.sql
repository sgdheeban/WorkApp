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