
name := "getStartedGatling"

version := "0.1"

enablePlugins(GatlingPlugin)

scalaVersion := "2.12.11"

resolvers += ("Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/").withAllowInsecureProtocol(true)


libraryDependencies += "io.gatling.highcharts" % "gatling-charts-highcharts" % "3.3.1" % "test,it"
libraryDependencies += "io.gatling"            % "gatling-test-framework"    % "3.3.1" % "test,it"
libraryDependencies += "org.hibernate" % "hibernate-core" % "4.3.0.CR1"
libraryDependencies += "org.hibernate" % "hibernate-entitymanager" % "4.3.0.CR1"
libraryDependencies += "org.hibernate.javax.persistence" % "hibernate-jpa-2.1-api" % "1.0.0.Draft-16"
libraryDependencies += "com.typesafe.play"            % "play-json_2.12"    % "2.8.1"
libraryDependencies += "org.apache.commons"            % "commons-lang3"    % "3.9"
libraryDependencies += "mysql"            % "mysql-connector-java"    % "8.0.18"
libraryDependencies += "log4j"            % "log4j"    % "1.2.17"
libraryDependencies += "org.json"            % "json"    % "20190722"
libraryDependencies += "uk.co.datumedge"            % "hamcrest-json"    % "0.2"
libraryDependencies += "org.jsoup"            % "jsoup"    % "1.12.1"
libraryDependencies +=  "com.typesafe.play" % "play-json_2.12" % "2.8.1"
libraryDependencies +=  "com.h2database" % "h2" % "1.4.199"
