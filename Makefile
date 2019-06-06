run:
	mvn compile exec:java -Dexec.mainClass=com.adrianosela.pipeliner.entrypoint.Entrypoint

fmt:
	mvn spotless:apply
