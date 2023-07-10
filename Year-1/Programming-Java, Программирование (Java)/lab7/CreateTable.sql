CREATE TABLE organizations(id serial,name text NOT NULL,coordinates_id integer NOT NULL,creationdate date NOT NULL,annualturnover double precision NOT NULL,employeescount bigint,type text,address_id integer,user_id integer NOT NULL,PRIMARY KEY (id));

CREATE TABLE address(id serial,street text,zipcode text,PRIMARY KEY (id));

CREATE TABLE coordinates(id serial,x integer NOT NULL,y double precision NOT NULL,PRIMARY KEY (id));

CREATE TABLE users(id serial,login text NOT NULL,password text NOT NULL,PRIMARY KEY (id));