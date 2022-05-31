#!/usr/bin/env bash

mvn clean package -Dmaven.test.skip=true 

java  -jar -Dloom=true --enable-preview --add-modules jdk.incubator.concurrent -Xdebug -Dconfig.env=local -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005 -Djdk.tracePinnedThreads=full ./target/loomy-0.0.1-SNAPSHOT.jar
