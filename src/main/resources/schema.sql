create sequence if not exists account_id_seq increment by 1;
create sequence if not exists file_id_seq increment by 1;
create sequence if not exists message_id_seq increment by 1;
create sequence if not exists passport_id_seq increment by 1;

create table if not exists account
(
    id          integer             not null default nextval('account_id_seq'),
    authorities character varying[],
    email       varchar(255) unique not null,
    name        text,
    avatar      text,
    constraint account_id_pk primary key (id)
);

create table if not exists passport
(
    id         integer      not null unique default nextval('passport_id_seq'),
    account_id integer      not null,
    type       varchar(255) not null,
    hash       varchar(255),
    created    timestamp    not null,
    updated    timestamp    not null,
    constraint passport_id primary key (id),
    constraint passport_account_id_fk foreign key (account_id)
        references account (id)
        on delete cascade
);

create table if not exists file
(
    id       integer             not null default nextval('file_id_seq'),
    blob     bytea,
    filename varchar(255) unique not null,
    mime     varchar(255),
    constraint file_id_pk primary key (id)
);

create table if not exists message
(
    id        integer not null default nextval('message_id_seq'),
    created   timestamp,
    updated   timestamp,
    text      text,
    title     text,
    author_id bigint,
    file_id   bigint,
    constraint message_id_pk primary key (id),
    constraint message_author_id_fk foreign key (author_id) references account (id),
    constraint message_file_id_fk foreign key (file_id) references file (id)
);

create table if not exists persistent_logins
(
    username  varchar(64) not null,
    series    varchar(64) primary key,
    token     varchar(64) not null,
    last_used timestamp   not null
);