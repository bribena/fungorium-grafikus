#!/bin/sh

javac -d out fungorium/**/*.java
cd out
jar cvfe $1.jar $1.Main $1/*.class
mv $1.jar ../$1.jar
cd ..