name := "proyectX"

version := "1.0"

lazy val `proyectx` = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq( jdbc , cache , ws   , specs2 % Test )

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"

libraryDependencies ++= Seq("org.scalaj" % "scalaj-http_2.11" % "2.3.0",
//  "com.fasterxml.jackson.module" % "jackson-module-scala_2.10" % "2.8.3",
  "com.couchbase.client" % "java-client" % "2.2.6",
  "io.reactivex" % "rxscala_2.11" % "0.26.1")

