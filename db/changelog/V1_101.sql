create table if not exists gbsm.account
(
    gid         uuid                     not null unique,
    create_date timestamp with time zone not null,
    update_date timestamp with time zone not null,
    authorities varchar(255)[],
    email       varchar(255)             not null unique,
    name        text,
    avatar      text,
    constraint account_gid_pk primary key (gid)
);

create table if not exists gbsm.passport
(
    gid         uuid                     not null unique,
    create_date timestamp with time zone not null,
    update_date timestamp with time zone not null,
    account_gid uuid                     not null,
    type        varchar(255)             not null,
    hash        varchar(255),
    constraint passport_gid_pk primary key (gid),
    constraint passport_account_gid_fk foreign key (account_gid)
        references gbsm.account (gid)
        on delete cascade
);

create table if not exists gbsm.file
(
    gid         uuid                     not null unique,
    create_date timestamp with time zone not null,
    update_date timestamp with time zone not null,
    blob        bytea,
    filename    varchar(255)             not null unique,
    mime        varchar(255),
    constraint file_gid_pk primary key (gid)
);

create table if not exists gbsm.message
(
    gid         uuid                     not null unique,
    create_date timestamp with time zone not null,
    update_date timestamp with time zone not null,
    text        text,
    title       text,
    author_gid  uuid,
    file_gid    uuid,
    constraint message_gid_pk primary key (gid),
    constraint message_author_gid_fk foreign key (author_gid) references gbsm.account (gid),
    constraint message_file_gid_fk foreign key (file_gid) references gbsm.file (gid)
);

create table if not exists gbsm.persistent_logins
(
    username  varchar(64)              not null,
    series    varchar(64) primary key,
    token     varchar(64)              not null,
    last_used timestamp with time zone not null
);

do
$$
    declare
        admin_id uuid := gen_random_uuid();
        user_id  uuid := gen_random_uuid();
    begin
        insert into gbsm.account(gid, create_date, update_date, email, authorities)
        values (admin_id, current_timestamp, current_timestamp, 'admin@test.dev', '{ADMIN}'),
               (user_id, current_timestamp, current_timestamp, 'user@test.dev', '{USER}')
        on conflict do nothing;

        insert into gbsm.passport(gid, create_date, update_date, account_gid, type, hash)
        values (gen_random_uuid(),
                current_timestamp,
                current_timestamp,
                admin_id,
                'PASSWORD',
                '$2a$10$qtLc3DFvFW1RzPcteRaJVOz4XeVaxZKjBlESHmJGXvLLmjg9qym3q'),
               (gen_random_uuid(),
                current_timestamp,
                current_timestamp,
                user_id,
                'PASSWORD',
                '$2a$10$RS2m6YmakNRsCCBri2UVh.OOzjPKcb8czCvgYuthrwv3PO4H8eAbW')
        on conflict do nothing;
    end
$$;
