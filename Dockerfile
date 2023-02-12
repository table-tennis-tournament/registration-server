# we are extending everything from tomcat:8.0 image ...
FROM bitnami/tomcat:8.0

MAINTAINER Dane
# COPY path-to-your-application-war path-to-webapps-in-docker-tomcat

COPY target/ttcborussia.war /bitnami/tomcat/data/


