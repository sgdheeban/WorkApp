create database `workapp`;
use workapp;
grant all privileges on workapp.* to 'user'@'%' identified by 'user';

create table customer (
	id bigint,
	email text,
	mobile bigint,
	password text,
	firstname text,
	middlename text,
	lastname text,
	primary key (id, email, mobile, firstname, lastname)
);

create table developer (
	id bigint,
	email text,
	mobile bigint,
	password text,
	firstname text,
	middlename text,
	lastname text,
	primary key (id, email, mobile, firstname, lastname)
);

