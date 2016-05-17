import com.typesafe.sbt.packager.docker._

lazy val projectName = "cingulata"

name := projectName

name in Universal := "cingulata"

lazy val commonSettings = Seq(
  organization := "org.cingulata",
  version := "0.1.0",
  scalaVersion := "2.11.7",
  routesGenerator := InjectedRoutesGenerator
)

lazy val cingulata = (project in file(".")).aggregate(oauth).dependsOn(oauth).aggregate(admin).dependsOn(admin).settings(commonSettings: _*).enablePlugins(PlayScala,ElasticBeanstalkPlugin,BuildInfoPlugin)

lazy val admin = (project in file("modules/admin")).settings(commonSettings: _*).enablePlugins(PlayScala)

lazy val oauth = (project in file("modules/oauth")).settings(commonSettings: _*).enablePlugins(PlayScala)

libraryDependencies ++= Seq(jdbc, cache, ws, filters, specs2 % Test)

libraryDependencies += "org.mongodb.scala" %% "mongo-scala-driver" % "1.1.0"

//casbah
libraryDependencies += "org.mongodb" %% "casbah" % "3.1.0"

//Testing
//scala mock
libraryDependencies += "org.scalamock" %% "scalamock-scalatest-support" % "3.2.2" % "test"

//Mailer plugin
libraryDependencies += "com.typesafe.play" %% "play-mailer" % "3.0.1"

unmanagedResourceDirectories in Test <+= baseDirectory(_ / "target/web/public/test")

// Docker/Elastic Beanstalk
maintainer in Docker := "Johnny Utah <johnny.utah@fbi.gov>"

dockerExposedPorts := Seq(9000)

dockerBaseImage := "java:latest"
