javac -d out %1/*.java
cd out
jar cvfe %1.jar %1.Main %1/*.class
move %1.jar ../%1.jar
cd ..