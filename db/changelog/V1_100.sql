create schema if not exists gbsm authorization gb_user;
grant usage on schema gbsm to gb_user_rw;
alter user gb_user set default_tablespace = gb_tbs;
alter user gb_user set search_path = gbsm,public;
alter user gb_user_rw set default_tablespace = gb_tbs;
alter user gb_user_rw set search_path = gbsm,public;
