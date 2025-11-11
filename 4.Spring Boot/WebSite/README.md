# web_site
````
mvn spring-boot:run

mvn clean package

java -jar target/SpringBoot_website.jar

````

# Запуск проекту
````
http://localhost:8081/
````

# Run Docker java
````
docker build -t springboot_website-java .
docker run -it --rm -p 6892:8081 --name springboot_website-java_container springboot_website-java
docker run -d --restart=always -p 6892:8081 --name springboot_website-java_container springboot_website-java
docker run -d --restart=always -v d:/volumes/springboot_website-java/images:/app/images -p 6892:8081 --name springboot_website-java_container springboot_website-java
docker ps -a
docker stop springboot_website-java_container
docker rm springboot_website-java_container
docker rmi springboot_website-java
docker login
docker tag springboot_website-java:latest jacobs51/springboot_website-java:latest
docker push jacobs51/springboot_website-java:latest
````