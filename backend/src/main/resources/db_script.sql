drop DATABASE IF EXISTS budget_db;
CREATE DATABASE budget_db

DROP TABLE IF EXISTS "public"."user";

drop SEQUENCE IF EXISTS user_id_seq;
create SEQUENCE user_id_seq;

create table "user" (
	"id" int4 DEFAULT nextval('user_id_seq'::regclass) PRIMARY KEY,
	username VARCHAR(20) NOT NULL,
	email VARCHAR(40) NOT NULL,
	password VARCHAR(200) NOT NULL,
	activated boolean default false NOT NULL,
	banned boolean default false NOT NULL
	avatarBytes TEXT,
	firstName VARCHAR(40),
	lastName VARCHAR(40),
	secondName VARCHAR(40)
);

CREATE INDEX ON user (username);