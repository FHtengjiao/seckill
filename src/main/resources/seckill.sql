CREATE DATABASE IF NOT EXISTS seckill；
USE seckill;
CREATE TABLE IF NOT EXISTS user_info(
id int primary auto_increment,
name varchar(45) NOT NULL DEFAULT '',
gender TINYINT NOT NULL DEFAULT 0,
telephone VARCHAR(11) NOT NULL DEFAULT '',
register_mode VARCHAR(45) NOT NULL
);

CREATE TABLE IF NOT EXISTS user_password(
id int primary auto_increment,
password varchar(128) NOT NULL DEFAULT '',
user_id int not null DEFAULT 0
);