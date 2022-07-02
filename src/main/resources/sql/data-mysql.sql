use study;

SET @@foreign_key_checks = 0;
truncate table member;
truncate table point;
SET @@foreign_key_checks = 1;

insert into member (member_name) values ('테스터1');
insert into member (member_name) values ('테스터2');
