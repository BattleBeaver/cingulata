lazy val projectName = "auth"

name := projectName

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"

libraryDependencies ++= Seq(
  "com.mohiva" %% "play-silhouette" % "4.0.0-BETA4",
  "com.mohiva" %% "play-silhouette-password-bcrypt" % "4.0.0-BETA4",
  "com.mohiva" %% "play-silhouette-persistence-memory" % "4.0.0-BETA4",
  "org.webjars" %% "webjars-play" % "2.5.0",
  "net.codingwell" %% "scala-guice" % "4.0.1",
  "com.iheart" %% "ficus" % "1.2.0",
  "com.adrianhurt" %% "play-bootstrap" % "1.0-P25-B3",
  "com.mohiva" %% "play-silhouette-testkit" % "4.0.0-BETA4" % "test"
)
