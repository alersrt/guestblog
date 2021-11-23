create sequence if not exists account_id_seq;
create sequence if not exists file_id_seq;
create sequence if not exists message_id_seq;

create table if not exists account
(
    id          bigint              not null default nextval('account_id_seq')
        constraint account_id_pk primary key,
    authorities character varying[],
    username    varchar(255) unique not null,
    email       varchar(255) unique,
    password    varchar(255)        not null
);

create table if not exists file
(
    id       bigint              not null default nextval('file_id_seq')
        constraint file_id_pk primary key,
    blob     bytea,
    filename varchar(255) unique not null,
    mime     varchar(255)
);

create table if not exists message
(
    id         bigint not null default nextval('message_id_seq')
        constraint message_id_pk primary key,
    created_at timestamp,
    updated_at timestamp,
    text       varchar(255),
    title      varchar(255),
    author_id  bigint
        constraint message_author_id_fk references account (id),
    file_id    bigint
        constraint message_file_id_fk references file (id)
);

create table if not exists persistent_logins
(
    username  varchar(64) not null,
    series    varchar(64) primary key,
    token     varchar(64) not null,
    last_used timestamp   not null
);