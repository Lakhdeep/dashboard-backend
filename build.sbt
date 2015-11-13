name := """play-java"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  "org.mongodb.morphia" % "morphia" % "1.0.1",
  "junit" % "junit" % "4.11",
  "com.google.inject" % "guice" % "4.0",
  "com.redmart.api.commons" % "api-commons" % "3.1.2" exclude("org.slf4j", "slf4j-log4j12")
)

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator

resolvers += "maven-repo" at "https://github.com/Redmart/maven-repo/raw/master/"

fork in run := true