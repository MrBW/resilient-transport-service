FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD hystrix-turbine-dashboard-1.0.jar app.jar
RUN sh -c 'touch /app.jar'
ENV JAVA_OPTS="-Xmx512m -Xms256m"
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]