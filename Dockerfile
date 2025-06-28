FROM gradle:8.8-jdk21-alpine as compiler

WORKDIR /code
ENV GRADLE_USER_HOME=/Dependencies

ADD ./settings.gradle /code
ADD ./build.gradle /code
ADD ./src /code/src

RUN gradle build --no-daemon --exclude-task test
RUN mkdir -p /build && \
    cp /code/build/libs/reminders-*.jar /build/reminders.jar && \
    rm -rf /code


FROM bellsoft/liberica-openjre-alpine:21

# Environment variables:
ENV JAVA_OPTS="-Xms128m -Xmx256m -XX:+UseG1GC -XX:MaxGCPauseMillis=150 -XX:InitiatingHeapOccupancyPercent=70" \
    SPRING_PROFILE="prod"

## create a nonroot user and group
RUN addgroup -S spring && adduser -S app -G app

## set the nonroot user as the default user
USER app:app

ENTRYPOINT exec java \
    ${JAVA_OPTS} \
    -Dspring.profiles.active=${SPRING_PROFILE} \
    -jar /app/reminders.jar

COPY --from=compiler /build/ /app
