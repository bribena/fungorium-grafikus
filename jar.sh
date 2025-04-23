#!/bin/sh

javac $1/*.java
jar cvfe $1.jar $1.Main $1/*.class