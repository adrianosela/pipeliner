gk:
	mvn compile exec:java -Dexec.mainClass=com.mozilla.secops.secopsGatekeeperPOC.pipelines.guardduty.GuardDuty

fmt:
	mvn spotless:apply
