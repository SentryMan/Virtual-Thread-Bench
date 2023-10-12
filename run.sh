#!/usr/bin/env bash

mvn clean package -Dmaven.test.skip=true 

java  -jar -Dloom=false --enable-preview ./target/loomy-0.0.1-SNAPSHOT.jar
