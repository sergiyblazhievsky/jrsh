#!/bin/bash
cd ../..
mvn clean install
java -jar target/jrsh-1.0-SNAPSHOT-jar-with-dependencies.jar superuser%superuser@http://localhost:8080/jasperserver-pro
