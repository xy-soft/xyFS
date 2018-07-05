CREATE TABLE "app" (
	`id`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	`appkey`	varchar,
	`available`	varchar,
	`code`	varchar,
	`name`	varchar
)

CREATE TABLE "dailyreport" (
	`id`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	`curdate`	varchar,
	`files`	integer NOT NULL,
	`groups`	integer NOT NULL,
	`spaces`	integer NOT NULL
)

CREATE TABLE diskfile (fileid varchar not null, appid integer not null, dfs_group_name varchar, download_num integer not null, download_pwd varchar, expiration_date timestamp, extra1 varchar, extra2 varchar, extra3 varchar, extra4 varchar, extra5 varchar, file_ext varchar, file_flag varchar, file_name varchar, file_size numeric, file_url varchar, filesource varchar, form_id varchar, group_id varchar, group_name varchar, ispublic varchar, upload_date timestamp, upload_user varchar, primary key (fileid))

CREATE TABLE diskgroup (group_id varchar not null, administrator varchar, create_date timestamp, extra1 varchar, extra2 varchar, extra3 varchar, extra4 varchar, extra5 varchar, father_group_id varchar, group_cn_name varchar, group_creator varchar, group_flag varchar, group_name varchar, group_type varchar, is_public varchar, max_num integer not null, primary key (group_id))

CREATE TABLE diskuser (user_name varchar not null, app_id integer not null, email varchar, extra1 varchar, extra2 varchar, extra3 varchar, extra4 varchar, extra5 varchar, first_date timestamp, is_admin varchar, last_date timestamp, max_space numeric, password varchar, phone varchar, used_space numeric, user_cn_name varchar, user_flag varchar, primary key (user_name))

CREATE TABLE "groupuser" (
	`id`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	`group_id`	varchar,
	`user_name`	varchar
)

CREATE TABLE "history" (
	`id`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	`extra1`	varchar,
	`extra2`	varchar,
	`extra3`	varchar,
	`extra4`	varchar,
	`extra5`	varchar,
	`file_name`	varchar,
	`group_name`	varchar,
	`operation`	varchar,
	`time`	timestamp,
	`user_name`	varchar
)