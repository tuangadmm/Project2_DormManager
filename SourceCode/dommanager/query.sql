create database if not exists dormmanager;

drop table if exists users;
create table users (
	id int primary key auto_increment,
    username varchar(20) unique not null,
    password varchar(50) not null,
    deleted int not null default 0
);
insert into users (username, password) value ("admin", "admin");

drop table if exists user_detail;
create table user_detail (
	user_id int not null,
    full_name varchar(50) not null,
    email varchar(50) unique not null,
    phone varchar(11) not null unique,
    role varchar(5) not null default "Staff",
    address varchar(50) not null,
    joined_date timestamp default current_timestamp    
) ;
insert into user_detail value (1, "Tuan", "admin@dormmanager.com", "0123456789", "Admin", "th", current_timestamp());

drop table if exists students;
create table students (
	id int auto_increment primary key,
    room_id int not null,
    full_name varchar(50) not null,
    gender varchar(7) not null,
    phone varchar(11) not null unique,
    email varchar(50) not null unique,
    address varchar(50) not null,
    joined_date timestamp default current_timestamp,
    deleted int not null default 0
);

drop table if exists rooms ;
create table rooms (
	id int auto_increment primary key,
    name varchar(5) unique not null,
    capacity int not null default 8,
    occupied int not null default 0,
    deleted int not null default 0
);

drop table if exists bills;
create table bills(
	id int auto_increment primary key,
    water int not null default 0,
    electricity int not null default 0,
    time varchar(10),
    room_id int,
	payment_status varchar(10) default 'Not paid',
	deleted int not null default 0
);

-- trigger
delimiter |
-- increase room occupied on insert student 
create trigger inc_room after insert on students 
	for each row
		update rooms set occupied = occupied + 1 where id = new.room_id;|
delimiter ;

delimiter |
-- decrease room occupied on insert student 
create trigger dec_room after update on students
	for each row
		update rooms set occupied = (select count(*) from students where deleted = 0 and room_id = room_id) where id = new.room_id;| 
delimiter ;
        
        
