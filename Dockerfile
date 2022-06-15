FROM openjdk:8u212-jdk-slim
ENV APP_FILE configuration-service.jar
ENV APP_HOME /opt
EXPOSE 8080
COPY target/$APP_FILE $APP_HOME/
WORKDIR $APP_HOME
ENTRYPOINT ["sh", "-c"]
CMD ["exec java -jar $APP_FILE"]