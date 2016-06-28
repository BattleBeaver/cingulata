lazy val projectName = "cingulata"

name := projectName

name in Universal := projectName

herokuAppName in Compile := projectName

lazy val commonSettings = Seq(
  organization := "org.cingulata",
  version := "0.1.0-M-0",
  scalaVersion := "2.11.7",
  routesGenerator := InjectedRoutesGenerator
)

lazy val cingulata = (project in file("."))
              .aggregate(stats).dependsOn(stats)
              .aggregate(admin).dependsOn(admin)
              .enablePlugins(PlayScala)
              .settings(commonSettings: _*)

lazy val common = (project in file("modules/common")).settings(commonSettings: _*).enablePlugins(PlayScala)

lazy val admin = (project in file("modules/admin")).aggregate(common).dependsOn(common).settings(commonSettings: _*).enablePlugins(PlayScala)

lazy val stats = (project in file("modules/stats")).settings(commonSettings: _*).enablePlugins(PlayScala)


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
