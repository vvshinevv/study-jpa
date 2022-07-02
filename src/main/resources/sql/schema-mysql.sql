create database if not exists study character set utf8mb4 collate utf8mb4_general_ci;

use study;

create table if not exists member (
    member_id   bigint not null auto_increment,
    member_name varchar(100),
    primary key (member_id)
);

create table if not exists point (
    point_id  bigint not null auto_increment,
    amount    int    not null,
    member_id bigint not null,
    foreign key (member_id) references member (member_id) on update cascade,
    primary key (point_id)
);
