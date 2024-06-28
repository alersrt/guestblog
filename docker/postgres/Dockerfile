FROM postgres:alpine

ENV TZ="UTC"

CMD ["postgres", "-c", "max_prepared_transactions=100", "-c", "config_file=/etc/postgresql/postgresql.conf"]


