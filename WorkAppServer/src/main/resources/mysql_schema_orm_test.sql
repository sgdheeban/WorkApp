// Creating database and tables for testing ORM Layer

drop database if exists `persist`;
create database `persist`;
use persist;
grant all privileges on persist.* to 'persist'@'%' identified by 'persist';

//Creating simple Table
drop table if exists simple;
create table simple (
	id int auto_increment,
	string_col varchar(255),
	int_col int,
	primary key (id)
);

//Creating numeric_types Table
drop table if exists numeric_types;
create table numeric_types (
	bit_col bit,
	tinyint_col tinyint,
	boolean_col boolean,
	smallint_col smallint,
	mediumint_col mediumint,
	int_col	int,
	bigint_col bigint,
	float_col float,
	double_col double,
	decimal_col decimal
);

//Creating datetime_types Table
drop table if exists datetime_types;
create table datetime_types (
	date_col date,
	datetime_col datetime,
	time_col time,
	year2_col year,
	year4_col year(4)
);

//Creating string_types Table
drop table if exists string_types;
create table string_types (
	char_col char(255),
	varchar_col varchar(255),
	tinytext_col tinytext,
	text_col text,
	mediumtext_col mediumtext,
	longtext_col longtext,
	enum_col enum(' ','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'),
	set_col set(' ','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z')
);

//Creating binary_types Table
drop table if exists binary_types;
create table binary_types (
	binary_col binary(255),
	varbinary_col varbinary(255),
	tinyblob_col tinyblob,
	blob_col blob,
	mediumblob_col mediumblob,
	longblob_col longblob
);

//Creating all_types Table
drop table if exists all_types;
create table all_types (
	id int auto_increment,
	bit_col bit,
	tinyint_col tinyint,
	boolean_col boolean,
	smallint_col smallint,
	mediumint_col mediumint,
	int_col	int,
	bigint_col bigint,
	float_col float,
	double_col double,
	decimal_col decimal,
	date_col date,
	datetime_col datetime,
	timestamp_col timestamp,
	time_col time,
	year2_col year,
	year4_col year(4),
	char_col char(255),
	varchar_col varchar(255),
	tinytext_col tinytext,
	text_col text,
	mediumtext_col mediumtext,
	longtext_col longtext,
	enum_col enum('a','b','c'),
	set_col set('a','b','c'),
	binary_col binary(255),
	varbinary_col varbinary(255),
	tinyblob_col tinyblob,
	blob_col blob,
	mediumblob_col mediumblob,
	longblob_col longblob,
	primary key (id)
);

// End of database and table creation for testing ORM Layer