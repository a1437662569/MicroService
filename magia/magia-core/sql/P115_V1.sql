/** 用户信息表-t_account */
create table "public"."t_account"(
id serial primary key,
email varchar(1024) ,
password varchar(1024) ,
other_pwd varchar(1024) ,
user_id bigint,
dns	 varchar(1024),
parental_pwd varchar(1024),
playlist_url varchar (1024),
mac varchar(1024),
auth_invalid_time  timestamp(0) without time zone ,
creater varchar(32) NOT NULL,
create_time  timestamp(0) without time zone NOT NULL,
updater varchar(32),
update_time  timestamp(0) without time zone
);

COMMENT ON COLUMN "public"."t_account"."email" IS '邮箱账号';
COMMENT ON COLUMN "public"."t_account"."password" IS '账号密码，密文， MD5（other_pwd+csmagiaplaylist）';
COMMENT ON COLUMN "public"."t_account"."other_pwd" IS '三方应用的账号密码（iptv smaters），明文';
COMMENT ON COLUMN "public"."t_account"."user_id" IS '三方应用账号，用户唯一ID，自增长，起始值为80000';
COMMENT ON COLUMN "public"."t_account"."dns" IS 'dns地址';
COMMENT ON COLUMN "public"."t_account"."parental_pwd" IS '父母锁密码';
COMMENT ON COLUMN "public"."t_account"."playlist_url" IS 'playlist文件地址,生成方式：http://magia.com/md5(userId+csmagiaplaylist)';
COMMENT ON COLUMN "public"."t_account"."mac" IS '用户填写的，smater iptv账号';
COMMENT ON COLUMN "public"."t_account"."auth_invalid_time" IS '授权到期时间';
COMMENT ON COLUMN "public"."t_account"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."t_account"."update_time" IS '更新时间';
COMMENT ON COLUMN "public"."t_account"."creater" IS '创建用户';
COMMENT ON COLUMN "public"."t_account"."updater" IS '更新用户';

-- 创建需要的索引
CREATE INDEX idx_account_create_time ON t_account (create_time);
CREATE INDEX idx_account_update_time ON t_account (update_time);
CREATE INDEX idx_account_user_id ON "public"."t_account" USING btree (user_id);
CREATE UNIQUE INDEX idx_account_email ON t_account (email);
alter table t_account OWNER TO magia;
-- 创建一个sequence
CREATE SEQUENCE user_id_seq
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;

-- 修改user_id_seq的起始值
alter sequence user_id_seq restart with 80000;
alter sequence user_id_seq  increment by 1 ;

-- 将该user_id_seq与t_account中的user_id字段关联
alter table t_account alter column user_id set default nextval('user_id_seq');
GRANT USAGE, SELECT ON SEQUENCE user_id_seq TO magia;



