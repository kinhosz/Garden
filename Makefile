ifeq ($(OS),Windows_NT)
	include scripts/win.mk
else
	echo Sistema nao suportado
endif

include scripts/utils.mk

BIN := bin
LIB := lib
GARDEN := com/garden

# libraries
JCUDA := $(LIB)/JCuda-All-10.1.0

JAR_FILES := $(wildcard $(JCUDA)/*.jar)

CLASSPATH := $(call list_to_string, $(eval ),;,$(JAR_FILES))

SRC_MAIN := $(GARDEN)/Space.java
CLASS_MAIN := $(GARDEN)/Space

SRC_CUDA := $(GARDEN)/Cuda.java
CLASS_CUDA := Cuda

all: compile start
cuda: compile_cuda start_cuda

compile:
	@javac -d $(BIN) $(SRC_MAIN)

start:
	@java -cp $(BIN) $(CLASS_MAIN)

compile_cuda:
	@javac -cp $(CLASSPATH) -d $(BIN) $(SRC_CUDA)

start_cuda:
	@java -cp $(CLASSPATH);$(BIN) $(CLASS_CUDA)

clean:
	@del /Q /S *.class >NUL 2>&1
	@rd /S /Q bin >NUL 2>&1
