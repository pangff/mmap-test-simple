FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENV JAVA_OPTS="\
-Djava.security.policy=/vagrant/tools.policy \
-Dcom.sun.management.jmxremote \
-Dcom.sun.management.jmxremote.rmi.port=8888 \
-Dcom.sun.management.jmxremote.port=8888 \
-Dcom.sun.management.jmxremote.ssl=false \
-Dcom.sun.management.jmxremote.authenticate=false \
-Djava.rmi.server.hostname=${HOSTNAME}"
ENTRYPOINT java ${JAVA_OPTS} -jar /app.jar
