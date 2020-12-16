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


select * from user;

create table user_info
(id varchar(50) not null primary key,
status tinyint(1),
statusMessage varchar(100),
loginTime varchar(50),
logoutTime varchar(50),
foreign key (id) references user (id) on delete cascade);