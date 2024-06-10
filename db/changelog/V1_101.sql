create table if not exists gbsm.account
(
    id          uuid                     not null unique,
    create_date timestamp with time zone not null,
    update_date timestamp with time zone not null,
    authorities character varying[],
    email       varchar(255)             not null unique,
    name        text,
    avatar      text,
    constraint account_id_pk primary key (id)
);

create table if not exists gbsm.passport
(
    id          uuid                     not null unique,
    create_date timestamp with time zone not null,
    update_date timestamp with time zone not null,
    account_id  uuid                     not null,
    type        varchar(255)             not null,
    hash        varchar(255),
    constraint passport_id primary key (id),
    constraint passport_account_id_fk foreign key (account_id)
        references gbsm.account (id)
        on delete cascade
);

create table if not exists gbsm.file
(
    id          uuid                     not null unique,
    create_date timestamp with time zone not null,
    update_date timestamp with time zone not null,
    blob        bytea,
    filename    varchar(255)             not null unique,
    mime        varchar(255),
    constraint file_id_pk primary key (id)
);

create table if not exists gbsm.message
(
    id          uuid                     not null unique,
    create_date timestamp with time zone not null,
    update_date timestamp with time zone not null,
    text        text,
    title       text,
    author_id   uuid,
    file_id     uuid,
    constraint message_id_pk primary key (id),
    constraint message_author_id_fk foreign key (author_id) references gbsm.account (id),
    constraint message_file_id_fk foreign key (file_id) references gbsm.file (id)
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
        insert into gbsm.account(id, create_date, update_date, email, authorities)
        values (admin_id, current_timestamp, current_timestamp, 'admin@test.dev', '{ADMIN}'),
               (user_id, current_timestamp, current_timestamp, 'user@test.dev', '{USER}')
        on conflict do nothing;

        insert into gbsm.passport(id, create_date, update_date, account_id, type, hash)
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
