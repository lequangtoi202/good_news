FROM eclipse-temurin:17

LABEL mentainer="toiquangle@gmail.com"

WORKDIR /app

COPY target/good_news-0.0.1-SNAPSHOT.war /app/good_news-0.0.1-SNAPSHOT.war

CMD ["java", "-jar", "good_news-0.0.1-SNAPSHOT.war"]