#
# Stage 'dist' creates project distribution.
#
# https://hub.docker.com/_/maven

FROM maven:3-openjdk-17 AS dist
USER root
COPY / /app
WORKDIR /app
RUN mvn -Dmaven.test.skip -Duser.home=/app package
RUN curl --request GET -sL \
  --url 'https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/latest/download/opentelemetry-javaagent.jar' \
  --output './opentelemetry-javaagent.jar'

#
# Stage 'runtime' creates final Docker image to use in runtime.
#
# https://hub.docker.com/_/tomcat
FROM openjdk:latest AS runtime
COPY --from=dist /app/target/guestblog.jar /opt/guestblog/
COPY --from=dist /app/opentelemetry-javaagent.jar /opt/opentelemetry-javaagent.jar
EXPOSE 8080 8080
ENV SERVICE_NAME=guestblog
ENV ZIPKIN_ENDPOINT=http://zipkin:9411/api/v2/spans
CMD exec java -javaagent:/opt/opentelemetry-javaagent.jar \
              -Dotel.instrumentation.jdbc-datasource.enabled=true \
              -Dotel.traces.exporter=zipkin \
              -Dotel.exporter.zipkin.endpoint=$ZIPKIN_ENDPOINT \
              -Dotel.service.name=$SERVICE_NAME \
              $JAVA_CMD_ARGS -jar /opt/guestblog/guestblog.jar
