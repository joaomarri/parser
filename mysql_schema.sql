create database parser;

create table accessLog (
	dateLog datetime not null, 
	ip varchar(20) not null, 
	request varchar(500) not null, 
	status int not null, 
	userAgent varchar(500) not null
);

create table parseResult (
  ip varchar(20) not null,
  comment varchar(200) not null,
  parseDate datetime not null
);
