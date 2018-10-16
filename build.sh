#!/bin/bash

if [[ ! -d out ]]; then
    mkdir out
fi

cp -r src/* out/
find out -name "*.java" | xargs rm

javac -d out -cp lib/commons-io-2.6.jar:lib/commons-logging-1.2.jar:lib/httpclient-4.5.6.jar:lib/httpcore-4.4.10.jar:lib/httpmime-4.5.6.jar:lib/javax.mail.jar:lib/postgres.jar:src/ ./src/*/*.java

