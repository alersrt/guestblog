FROM postgres:alpine

ENV TZ="UTC"

RUN mkdir -p /u01/gb && chown postgres /u01/gb
COPY *.sh /docker-entrypoint-initdb.d/

#USER postgres

CMD ["postgres", "-c", "max_prepared_transactions=100", "-c", "config_file=/etc/postgresql/postgresql.conf"]


