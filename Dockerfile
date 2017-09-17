FROM openjdk:8-jre

RUN mkdir /app

COPY version.info /app/
COPY build/libs/${ARTIFACT_NAME} /app
COPY entrypoint.sh /app

EXPOSE 8080

WORKDIR /app
RUN chmod +x entrypoint.sh
ENTRYPOINT ["./entrypoint.sh"]
CMD ["run"]