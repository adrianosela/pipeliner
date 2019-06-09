guardduty:
	mvn compile exec:java -Dexec.mainClass=com.adrianosela.pipeliner.pipelines.guardduty.GuardDuty

wordcount:
	mvn compile exec:java -Dexec.mainClass=com.adrianosela.pipeliner.pipelines.wordcount.WordCount

fmt:
	mvn spotless:apply
