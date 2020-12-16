create database messenger;
use messenger;
create table user
(id varchar(50) not null primary key,
password varchar(50) not null,
nickName varchar(50) not null unique key,
name varchar(50) not null,
email varchar(100) not null,
birth varchar(50) not null,
phoneNumber varchar(50),
homepage varchar(100),
additional varchar(200),
friend varchar(500));