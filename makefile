# You must have a JDK installed to have access to javac and java commands
JAVAC=javac
JAVA=java
JUNIT_LIB=lib/junit-4.13.2.jar
HAMCREST_LIB=lib/hamcrest-core-1.3.jar
SRC=.
CLASSPATH=$(SRC):$(JUNIT_LIB):$(HAMCREST_LIB)

# Classes to compile
CLASSES=$(SRC)/Main.java $(SRC)/BigIntCalculation.java $(SRC)/BigIntCalculationTest.java

# Target to compile all classes
compile:
	$(JAVAC) -cp $(CLASSPATH) $(CLASSES)
	@echo "Main class compiled! Run with \"java Main\""

# Target to run the main class with given input
test-run: compile
	$(JAVA) -cp $(CLASSPATH) Main < input.txt

# Target to run tests
test: compile
	$(JAVA) -cp $(CLASSPATH) org.junit.runner.JUnitCore BigIntCalculationTest

# Clean the project
clean:
	rm -f $(SRC)/*.class
