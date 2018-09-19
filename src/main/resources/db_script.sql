DROP TABLE IF EXISTS "public"."user";
DROP TABLE IF EXISTS "public"."consumption";

drop SEQUENCE IF EXISTS budget_id_seq;
drop SEQUENCE IF EXISTS user_id_seq;

create SEQUENCE budget_id_seq;
create SEQUENCE user_id_seq;

create table "user" (
	"id" int4 DEFAULT nextval('user_id_seq'::regclass) PRIMARY KEY,
	username nvarchar(20) NOT NULL,
	password nvarchar(200) NOT NULL
);

create table consumption (
	"id" int4 DEFAULT nextval('budget_id_seq'::regclass) PRIMARY KEY,
	amount DECIMAL NOT NULL,
	description text,
	user_id int4 NOT NULL
);

ALTER TABLE consumption ADD FOREIGN KEY ("user_id") REFERENCES "public"."user"("id") ON DELETE NO ACTION ON UPDATE NO ACTION;