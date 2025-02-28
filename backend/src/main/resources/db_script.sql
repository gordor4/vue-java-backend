CREATE DATABASE "budget_db"
    WITH OWNER "budget_user"
    ENCODING 'UTF8'
    LC_COLLATE = 'en_US.UTF-8'
    LC_CTYPE = 'en_US.UTF-8'
    TEMPLATE template0;


DROP TABLE IF EXISTS "public"."user";
DROP TABLE IF EXISTS "public"."board";
DROP TABLE IF EXISTS "public"."board_permission";
DROP TABLE IF EXISTS "public"."board_user_permissions";

drop SEQUENCE IF EXISTS user_id_seq;
drop SEQUENCE IF EXISTS board_id_seq;
drop SEQUENCE IF EXISTS board_permission_id_seq;
drop SEQUENCE IF EXISTS avatar_id_seq;
drop SEQUENCE IF EXISTS dashboard_card_id_seq;
drop SEQUENCE IF EXISTS card_content_id_seq;

create SEQUENCE avatar_id_seq;
create SEQUENCE user_id_seq;
create SEQUENCE board_id_seq;
create SEQUENCE board_permission_id_seq;
create SEQUENCE card_content_id_seq;
create SEQUENCE chat_card_content_id_seq;
create SEQUENCE message_id_seq;



create table avatar (
  "id" int4 DEFAULT nextval('avatar_id_seq'::regclass) PRIMARY KEY,
  img_binary TEXT NOT NULL
);

create table "user" (
	"id" int4 DEFAULT nextval('user_id_seq'::regclass) PRIMARY KEY,
	username VARCHAR(20) NOT NULL UNIQUE,
	email VARCHAR(40) NOT NULL UNIQUE,
	password VARCHAR(200) NOT NULL,
	activated boolean default false NOT NULL,
	banned boolean default false NOT NULL,
	creation_date date NOT NULL DEFAULT CURRENT_DATE,
	avatar_id int4 references avatar(id),
	first_name VARCHAR(40),
	last_name VARCHAR(40),
	second_name VARCHAR(40)
);

CREATE INDEX ON "user"(username);

create table "board" (
	"id" int4 DEFAULT nextval('board_id_seq'::regclass) PRIMARY KEY,
	owner_id int4 NOT NULL REFERENCES "user"(id),
  board_name text NOT NULL,
  board_description text,
  is_public boolean NOT NULL DEFAULT false,
  is_public_edit boolean NOT NULL DEFAULT false,
  creation_date date NOT NULL DEFAULT CURRENT_DATE
);

create table board_permission (
  "id" int4 DEFAULT nextval('board_permission_id_seq'::regclass) PRIMARY KEY,
  board_id int4 references board(id),
  user_id int4 references "user"(id),
  permission_read boolean default false,
  permission_edit boolean default false
);

create table dashboard_card (
  "id" int4 DEFAULT nextval('dashboard_card_id_seq'::regclass) PRIMARY KEY,
  board_id int4 references board(id),
  card_name text NOT NULL,
  card_type varchar NOT NULL,
  private boolean NOT NULL default true,

);

create table text_card_content (
   "id" int4 DEFAULT nextval('card_content_id_seq'::regclass) PRIMARY KEY,
   dashboard_id int4 references dashboard_card(id),
   dashboard_text text,
   title text NOT NULL
);

create table chat_card_content (
  "id" int4 DEFAULT nextval('chat_card_content_id_seq'::regclass) PRIMARY KEY,
  dashboard_id int4 references dashboard_card(id),
  chat_name text NOT NULL,
  chat_creator int4 references "user"(id)
);

create table chat_message (
  "id" int4 DEFAULT nextval('message_id_seq'::regclass) PRIMARY KEY,
  author text NOT NULL,
  contents text NOT NULL,
  date timestamp NOT NULL
)