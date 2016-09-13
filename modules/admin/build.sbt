lazy val projectName = "admin"

name := projectName

libraryDependencies ++= Seq(ws, specs2 % Test)

libraryDependencies += "org.mongodb.scala" %% "mongo-scala-driver" % "1.1.0"

//casbah
libraryDependencies += "org.mongodb" %% "casbah" % "3.1.0"

//Testing
//scala mock
libraryDependencies += "org.scalamock" %% "scalamock-scalatest-support" % "3.2.2" % "test"

//Mailer plugin
libraryDependencies += "com.typesafe.play" %% "play-mailer" % "3.0.1"

libraryDependencies += "com.typesafe.akka" %% "akka-remote" % "2.3.4"
