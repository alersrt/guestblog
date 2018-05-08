#
# Stage 'dist' creates project distribution.
#
# https://hub.docker.com/_/maven

FROM maven:alpine AS dist
USER root
COPY / /app
WORKDIR /app
RUN mvn -Duser.home=/app package

#
# Stage 'runtime' creates final Docker image to use in runtime.
#
# https://hub.docker.com/_/tomcat
FROM tomcat:alpine AS runtime
COPY --from=dist /app/target/guestblog.war /usr/local/tomcat/webapps/
EXPOSE 8080 8080
