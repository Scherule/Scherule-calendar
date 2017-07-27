FROM openjdk:8-jre

ARG ARTIFACT_NAME
ARG MAIN_CLASS_NAME
ARG MAIN_VERTICLE_NAME

ENV ARTIFACT_NAME=${ARTIFACT_NAME}
ENV MAIN_CLASS_NAME=${MAIN_CLASS_NAME}
ENV MAIN_VERTICLE_NAME=${MAIN_VERTICLE_NAME}

RUN mkdir /app

COPY build/libs/${ARTIFACT_NAME} /app
COPY entrypoint.sh /app

WORKDIR /app
RUN chmod +x entrypoint.sh
ENTRYPOINT ["./entrypoint.sh"]
CMD ["run", "$MAIN_VERTICLE_NAME", "--launcher-class=$MAIN_CLASS_NAME"]