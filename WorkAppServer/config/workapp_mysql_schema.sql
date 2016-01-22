// Creating WorkApp DB and Tables

drop database if exists `workapp` ;
create database `workapp`;
use workapp;
grant all privileges on workapp.* to 'user'@'%' identified by 'user';

drop table if exists `user` ;
create table user (
	id bigint,
	email varchar(11),
	primary key (id, email)
);


