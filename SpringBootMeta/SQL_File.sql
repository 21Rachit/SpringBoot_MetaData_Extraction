use shubham;


create table metatable(
id int not null auto_increment primary key,
tableName varchar(1000),
columnName varchar(1000),
isAuto varchar(1000),
columnTypeName varchar(1000),
columnType varchar(1000)

);

CREATE TABLE `emp99` (
  `id` int not null primary key auto_increment,
  `item_name` varchar(45) ,
  `item_image` varchar(45) ,
  `item_category` varchar(45) ,
  `item_quantity` varchar(45) ,
  `item_unit` varchar(45) ,
  `item_unit_price` varchar(45),
  `item_status` varchar(45),
  `user_id` int
);

CREATE TABLE `user99` (
  `id` int not null primary key auto_increment,
  `salutation` varchar(45) ,
  `first_name` varchar(45) ,
  `last_name` varchar(45) ,
  `gender` varchar(45) ,
  `mobile` varchar(45) ,
  `email` varchar(45),
  `address` varchar(45),
  `role` varchar(45),
  `status` varchar(45) 
);


CREATE TABLE `student99` (
  `id` int not null primary key auto_increment,
  `first_name` varchar(45) ,
  `last_name` varchar(45) ,
  `email` varchar(45)
);


select * from metatable;
select * from contact;
select * from emp99;
drop table metatable;



