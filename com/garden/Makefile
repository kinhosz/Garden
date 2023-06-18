LIB := lib

JCUDA := $(wildcard $(LIB)/JCuda-All-10.1.0/*.jar)

define join_with_semicolon
$(subst $(eval) ,;,$(foreach file,$1,$(file)))
endef

CLASSPATH := .;$(call join_with_semicolon,$(JCUDA))

all:
	javac Space.java

start:
	java Space

cuda:
	javac -cp "$(CLASSPATH)" Cuda.java

run:
	java Cuda

porra:
	javac -cp ".;lib/JCuda-All-10.1.0/jcuda-10.1.0.jar;lib/JCuda-All-10.1.0/jcuda-natives-10.1.0-windows-x86_64.jar" Cuda.java

carai:
	java -cp ".;lib/JCuda-All-10.1.0/jcuda-10.1.0.jar;lib/JCuda-All-10.1.0/jcuda-natives-10.1.0-windows-x86_64.jar" Cuda]

clean:
	del /s /q *.class
