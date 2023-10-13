FROM eclipse-temurin:17

WORKDIR /app

COPY target/good_news-0.0.1-SNAPSHOT.war /app/good_news-0.0.1-SNAPSHOT.war
EXPOSE 8083
CMD ["java", "-jar", "good_news-0.0.1-SNAPSHOT.war"]