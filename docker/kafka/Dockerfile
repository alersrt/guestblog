FROM confluentinc/cp-kafka

ENV TZ="UTC"

RUN echo "export CLUSTER_ID=$(kafka-storage random-uuid)" >> /etc/confluent/docker/bash-config
