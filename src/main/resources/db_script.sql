DROP TABLE IF EXISTS "public"."user";
DROP TABLE IF EXISTS "public"."payment";
DROP TABLE IF EXISTS "public"."periodical_payment";
DROP TABLE IF EXISTS "public"."payment_template";

drop SEQUENCE IF EXISTS payment_id_seq;
drop SEQUENCE IF EXISTS template_payment_id_seq;
drop SEQUENCE IF EXISTS payment_template_id_seq;
drop SEQUENCE IF EXISTS period_payment_id_seq;
drop SEQUENCE IF EXISTS user_id_seq;

create SEQUENCE payment_id_seq;
create SEQUENCE template_payment_id_seq;
create SEQUENCE payment_template_id_seq;
create SEQUENCE period_payment_id_seq;
create SEQUENCE user_id_seq;

create table "user" (
	"id" int4 DEFAULT nextval('user_id_seq'::regclass) PRIMARY KEY,
	username VARCHAR(20) NOT NULL,
	password VARCHAR(200) NOT NULL
);

create table payment (
	"id" int4 DEFAULT nextval('payment_id_seq'::regclass) PRIMARY KEY,
	amount DECIMAL NOT NULL,
	description text,
	user_id int4 NOT NULL,
	created_from_template boolean default false NOT NULL
);

create table periodical_payment (
  "id" int4 DEFAULT nextval('period_payment_id_seq'::regclass) PRIMARY KEY,
  "start_date" date NOT NULL,
  "period" bigserial NOT NULL
);

create table payment_template (
  "id" int4 DEFAULT nextval('template_payment_id_seq'::regclass) PRIMARY KEY,
  template_name varchar(30) NOT NULL,
  description text
);

ALTER TABLE payment ADD FOREIGN KEY ("user_id") REFERENCES "public"."user"("id") ON DELETE NO ACTION ON UPDATE NO ACTION;