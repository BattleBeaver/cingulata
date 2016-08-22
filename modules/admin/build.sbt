lazy val projectName = "admin"

name := projectName

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"

libraryDependencies ++= Seq(jdbc, cache, ws, filters, specs2 % Test)

libraryDependencies += "org.mongodb.scala" %% "mongo-scala-driver" % "1.1.0"

libraryDependencies += "com.github.t3hnar" % "scala-bcrypt_2.11" % "2.5"

//casbah
libraryDependencies += "org.mongodb" %% "casbah" % "3.1.0"

//Testing
//scala mock
libraryDependencies += "org.scalamock" %% "scalamock-scalatest-support" % "3.2.2" % "test"

//Mailer plugin
libraryDependencies += "com.typesafe.play" %% "play-mailer" % "3.0.1"

libraryDependencies += "com.typesafe.akka" %% "akka-remote" % "2.3.4"
