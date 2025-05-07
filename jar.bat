javac -d out fungorium/*/*.java
javac -d out fungorium/*.java
cd out
jar cvfe fungorium.jar fungorium.Main fungorium/*.class fungorium/*/*.class
move fungorium.jar ../fungorium.jar
cd ..