--SQL querys
--1)
select ip from accessLog
where
dateLog >= '2017-01-01 13:00:00'
and dateLog <= '2017-01-01 14:00:00'
Group by ip
Having count(ip) > 100


--2)
select * from accessLog
where
ip = '192.168.12.4'