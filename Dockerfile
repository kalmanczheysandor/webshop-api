FROM adoptopenjdk:11-jre-hotspot
RUN mkdir /opt/app
COPY target/webshop-admin-1.0-SNAPSHOT.jar /opt/app/webshop.jar
CMD ["java", "-jar", "/opt/app/webshop.jar"]