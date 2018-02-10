create database parserdb;

create user 'parseruser'@'localhost' identified by 'parseruser';

grant all on parserdb.* to 'parseruser'@'localhost';

use parserdb;

create table accessLog (
	dateLog datetime not null, 
	ip varchar(25) not null, 
	request varchar(400) not null, 
	status int not null, 
	userAgent varchar(500) not null,
	filename varchar(400) not null
);

create table parseResult (
  ip varchar(25) not null,
  comment varchar(200) not null,
  parseDate datetime not null,
  filename varchar(400) not null
);
