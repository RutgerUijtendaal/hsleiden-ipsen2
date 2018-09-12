#!/bin/bash

# copy directory structure from ./src/ to ./out/ incase its not there already
cd ./src/
find ./ -type d > ../.dirs.tmp
cd ../out/
xargs mkdir -p < ../.dirs.tmp
cd ../
unlink .dirs.tmp
# compile program in ./src/ and output to ./out/
javac -d ./out/ -cp ./jaybird-full-3.0.5.jar:./mariadb-java-client-2.3.0.jar:./postgresql-42.2.5.jar:./src/ ./src/*.java
