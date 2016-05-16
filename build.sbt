lazy val projectName = "cingulata"

name := projectName

name in Universal := "cingulata"

lazy val commonSettings = Seq(
  organization := "org.cingulata",
  version := "0.1.0",
  scalaVersion := "2.11.7",
  routesGenerator := InjectedRoutesGenerator
)

lazy val cingulata = (project in file(".")).aggregate(admin).dependsOn(admin).settings(commonSettings: _*).enablePlugins(PlayScala)

lazy val admin = (project in file("modules/admin")).settings(commonSettings: _*).enablePlugins(PlayScala)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"

libraryDependencies ++= Seq(jdbc, cache, ws, filters, specs2 % Test)

libraryDependencies += "org.mongodb.scala" %% "mongo-scala-driver" % "1.1.0"

libraryDependencies += "com.github.t3hnar" % "scala-bcrypt_2.11" % "2.5"

libraryDependencies ++= Seq(
    "jp.t2v" %% "play2-auth"        % "0.14.2",
    "jp.t2v" %% "play2-auth-social" % "0.14.2",
    "jp.t2v" %% "play2-auth-test"   % "0.14.2" % "test",
    play.sbt.Play.autoImport.cache
  )

//casbah
libraryDependencies += "org.mongodb" %% "casbah" % "3.1.0"

//Testing
//scala mock
libraryDependencies += "org.scalamock" %% "scalamock-scalatest-support" % "3.2.2" % "test"

//Mailer plugin
libraryDependencies += "com.typesafe.play" %% "play-mailer" % "3.0.1"


//heroku config
herokuAppName in Compile := projectName

unmanagedResourceDirectories in Test <+= baseDirectory(_ / "target/web/public/test")
