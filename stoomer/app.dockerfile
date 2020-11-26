# application server
FROM tomcat:9.0.40-jdk8
# Application to publish
COPY target/stoomer.war /usr/local/tomcat/webapps/stoomer.war
EXPOSE 8080
