#!/usr/bin/env bash
set -e

mkdir -p /u01/gb

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    create extension pg_stat_statements;
    create user gb_user password 'gb_user';
    create user gb_user_rw password 'gb_user_rw';
    create tablespace gb_tbs owner gb_user location '/u01/gb';
    create database gbdb owner gb_user tablespace gb_tbs;
    grant connect on database gbdb to gb_user_rw;
    revoke connect on database gbdb from public;
EOSQL
