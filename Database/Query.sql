drop  database if exists dormmanager;

create database  dormmanager;

use dormmanager;

create table `users` (
  `id` int not null auto_increment,
  `username` varchar(20) not null,
  `password` varchar(50) not null,
  `deleted` int not null default '0',
  primary key (`id`),
  unique key `username` (`username`)
) engine=InnoDB  default charset=utf8mb4 collate=utf8mb4_0900_ai_ci;

insert into users (username, password) value ("admin", "admin");

create table `user_detail` (
  `user_id` int not null,
  `full_name` varchar(50) not null,
  `email` varchar(50) not null,
  `phone` varchar(11) not null,
  `role` varchar(5) not null default 'Staff',
  `address` varchar(50) not null,
  `joined_date` timestamp null default current_timestamp,
  primary key (`user_id`),
  unique key `email` (`email`),
  unique key `phone` (`phone`),
  constraint `user_detail` foreign key (`user_id`) references `users` (`id`)
) engine=InnoDB default charset=utf8mb4 collate=utf8mb4_0900_ai_ci;

insert into user_detail value (1, "root", "root@dormmanager.com", "0123456789", "Admin", "th", current_timestamp());

create table `rooms` (
  `id` int not null auto_increment,
  `name` varchar(5) not null,
  `capacity` int not null default '8',
  `occupied` int not null default '0',
  `deleted` int not null default '0',
  primary key (`id`),
  unique key `name` (`name`)
) engine=InnoDB default charset=utf8mb4 collate=utf8mb4_0900_ai_ci;

create table `students` (
  `id` int not null auto_increment,
  `room_id` int not null,
  `full_name` varchar(50) not null,
  `gender` varchar(7) not null,
  `phone` varchar(11) not null,
  `email` varchar(50) not null,
  `address` varchar(50) not null,
  `joined_date` timestamp null default current_timestamp,
  `deleted` int not null default '0',
  primary key (`id`),
  unique key `phone` (`phone`),
  unique key `email` (`email`),
  key `student_room_idx` (`room_id`),
  constraint `student_room` foreign key (`room_id`) references `rooms` (`id`)
) engine=InnoDB default charset=utf8mb4 collate=utf8mb4_0900_ai_ci;

create table `bills` (
  `id` int not null auto_increment,
  `water` int not null default '0',
  `electricity` int not null default '0',
  `time` varchar(10) default null,
  `room_id` int default null,
  `payment_status` varchar(10) default 'Not paid',
  `deleted` int not null default '0',
  primary key (`id`),
  key `bill_room_idx` (`room_id`),
  constraint `bill_room` foreign key (`room_id`) references `rooms` (`id`)
) engine=InnoDB default charset=utf8mb4 collate=utf8mb4_0900_ai_ci;

create table `announcements` (
  `id` int not null auto_increment,
  `sender` int not null,
  `to_room` int not null,
  `subject` varchar(50) not null,
  `message` text not null,
  `sent_date` timestamp null default current_timestamp,
  primary key (`id`),
  key `users_ann_idx` (`sender`),
  constraint `users_ann` foreign key (`sender`) references `users` (`id`)
) engine=InnoDB default charset=utf8mb4 collate=utf8mb4_0900_ai_ci;

create table `feedback` (
  `id` int not null auto_increment,
  `sender` int not null,
  `subject` varchar(50) not null,
  `message` text not null,
  `sent_date` timestamp null default current_timestamp,
  `status` varchar(15) not null default 'Not replied',
  primary key (`id`),
  key `feedback_student_idx` (`sender`),
  constraint `feedback_student` foreign key (`sender`) references `students` (`id`)
) engine=InnoDB default charset=utf8mb4 collate=utf8mb4_0900_ai_ci;

create table `feedback_reply` (
  `id` int not null auto_increment,
  `feedback_id` int not null,
  `replier` int not null,
  `reciever` int not null,
  `message` text not null,
  `sent_date` timestamp null default current_timestamp,
  primary key (`id`),
  key `reply_user_idx` (`replier`),
  key `reply_student_idx` (`reciever`),
  constraint `reply_student` foreign key (`reciever`) references `students` (`id`),
  constraint `reply_user` foreign key (`replier`) references `users` (`id`)
) engine=InnoDB default charset=utf8mb4 collate=utf8mb4_0900_ai_ci;

-- trigger
-- increase room occupied on insert student 
delimiter |
create trigger inc_room after insert on students 
	for each row
		update rooms set occupied = occupied + 1 where id = new.room_id;|
delimiter ;

-- decrease room occupied on insert student 
delimiter |
create trigger dec_room after update on students
	for each row
		update rooms set occupied = (select count(*) from students where deleted = 0 and room_id = room_id) where id = new.room_id;| 
delimiter ;

-- update feedback status to 'Replied' when insert to feedback_reply
delimiter |
create trigger updt_feed after insert on feedback_reply
	for each row
		update feedback set status = "Replied" where id = new.feedback_id;| 
delimiter ;

-- add announcement about bill when insert new bill
delimiter |
create trigger alert_bill after insert on bills
	for each row
		insert into announcements (sender, to_room, subject, message) value (1, new.room_id, "New bill", "Bill this month is available, please check at dashborad and process the payment as soon as possible\nFrom: system");| 
delimiter ;

-- add announcement about bill when bill is paid
delimiter |
create trigger complete_bill after update on bills
	for each row
		if new.payment_status = 'Paid' then 
			insert into announcements (sender, to_room, subject, message) value (1, new.room_id, "Bill paid", "Bill this month has been paid, thanks for using our service\nFrom: system");
		end if;| 
delimiter ;


        
        
