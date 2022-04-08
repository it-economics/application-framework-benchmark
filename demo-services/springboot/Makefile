compile:
	./gradlew clean assemble

dockerBuild:
	./gradlew clean bootJar
	docker build -t afb-springboot .

run:
	docker run -p 8080:8080 afb-springboot
