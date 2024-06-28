create extension pg_stat_statements;

create user gb_user password 'gb_user';
create user gb_user_rw password 'gb_user_rw';

alter database gbdb owner to gb_user;

grant connect on database gbdb to gb_user_rw;
revoke connect on database gbdb from public;

create schema if not exists gbsm authorization gb_user;

grant usage on schema gbsm to gb_user_rw;
alter user gb_user set search_path = gbsm,public;
alter user gb_user_rw set search_path = gbsm,public;
