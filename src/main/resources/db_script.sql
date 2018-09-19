create SEQUENCE budget_id_seq;

create table budget(
	"id" int4 DEFAULT nextval('budget_id_seq'::regclass) PRIMARY KEY,
	amount DECIMAL
);