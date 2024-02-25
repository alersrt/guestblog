#
# Stage 'dist' creates project distribution.
#
# https://hub.docker.com/_/maven

FROM maven:3-openjdk-17 AS dist
USER root
RUN mkdir -p /app
COPY / /app/
WORKDIR /app
RUN mvn -Dmaven.test.skip -Duser.home=/app package

#
# Stage 'runtime' creates final Docker image to use in runtime.
#
# https://hub.docker.com/_/tomcat
FROM openjdk:latest AS runtime
COPY --from=dist /app/target/guestblog.jar /opt/guestblog/
COPY --from=dist /app/src/main/opentelemetry/opentelemetry-javaagent-2.1.0.jar /opt/opentelemetry-javaagent.jar
EXPOSE 8080 8080

CMD exec java -javaagent:/opt/opentelemetry-javaagent.jar ${JAVA_CMD_ARGS} -jar /opt/guestblog/guestblog.jar
