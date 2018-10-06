#!/bin/bash

if [[ ! -d out ]]; then
    mkdir out
fi

cp -r src/* out/
find out -name "*.java" | xargs rm

javac -d out -cp ./lib/postgres.jar:./src ./src/*/*.java
