FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD address-service-1.0.jar app.jar
RUN sh -c 'touch /app.jar'
ENV JAVA_OPTS="-Xmx256m -Xms128m"
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]