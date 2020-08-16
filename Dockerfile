FROM openjdk:8-jdk-alpine
RUN addgroup -S apps && adduser -S userapp -G apps
USER userapp:apps
ARG DEPENDENCY=build/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-cp","app:app/lib/*","com.ashrapov.Application"]