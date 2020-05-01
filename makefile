# 200778769
# amsalea3

compile: bin
	javac -cp src -d bin src/*.java

run:
	java -cp bin ExpressionTest

bonus:
	java -cp bin SimplificationDemo

bin:
	mkdir bin