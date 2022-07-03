CREATE DATABASE IF NOT EXISTS tripledb CHARACTER set utf8mb4 COLLATE utf8mb4_unicode_ci;

DROP TABLE IF EXISTS reviewphoto;
DROP TABLE IF EXISTS review;
DROP TABLE IF EXISTS place;
DROP TABLE IF EXISTS pointhistory;
DROP TABLE IF EXISTS user;


/* CREATE TABLES */;
create table place (
	placeId varchar(36) not null,
	address VARCHAR(36),
	primary key (placeId)
	) default CHARSET=utf8mb4;

create table pointhistory (
	pointId bigint not null auto_increment,
	createdDate datetime(6),
	action varchar(255),
	reviewId varchar(36),
	type varchar(255),
	userId VARCHAR(36),
	primary key (pointId)
	) default CHARSET=utf8mb4;

create table review (
	reviewId varchar(36) not null,
	content varchar(255),
	point int default 0,
	placeId VARCHAR(36),
	userId VARCHAR(36),
	primary key (reviewId)
	) default CHARSET=utf8mb4;

create table reviewphoto (
	attachedPhotoId varchar(36) not null,
	reviewId VARCHAR(36),
	primary key (attachedPhotoId)
	) default CHARSET=utf8mb4;

create table user (
	userId varchar(36) not null,
	points integer,
	primary key (userId)
	) default CHARSET=utf8mb4;


/* CREATE INDEX */;
create index idx_place on place (placeId);
create index idx_point on pointhistory (pointId);
create index idx_review on review (reviewId);
create index idx_user on user (userId);


/* ADD FK */;
alter table pointhistory add constraint fk_userId_history foreign key (userId) references user (userId);
alter table review add constraint fk_placeId_review foreign key (placeId) references place (placeId);
alter table review add constraint fk_userId_review foreign key (userId) references user (userId);
alter table reviewphoto add constraint fk_reviewId_photo foreign key (reviewId) references review (reviewId);


/* INITIALIZE DATA */;
INSERT INTO user (userId) VALUES ('3ede0ef2-92b7-4817-a5f3-0c575361f745');
INSERT INTO user (userId) VALUES ('05f5af06-fafa-11ec-a809-3c7c3fc20bf9');
INSERT INTO user (userId) VALUES ('46b805d8-fafa-11ec-a809-3c7c3fc20bf9');


INSERT INTO place (placeId) VALUES ('261a0a74-fafa-11ec-a809-3c7c3fc20bf9');
INSERT INTO place (placeId) VALUES ('2e4baf1c-5acb-4efb-a1af-eddada31b00f');
