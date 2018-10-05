#!/bin/bash

if [[ ! -d out ]]; then
    mkdir out
fi

javac -d out -cp ./lib/postgres.jar:./src ./src/*/*.java
