lazy val projectName = "auth"

name := projectName

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"

libraryDependencies ++= Seq(
  "com.mohiva" % "play-silhouette_2.11" % "4.0.0-BETA4",
  "com.mohiva" % "play-silhouette-password-bcrypt_2.11" % "4.0.0-BETA4",
  "com.mohiva" % "play-silhouette-persistence-memory_2.11" % "4.0.0-BETA4",
  "org.webjars" %% "webjars-play" % "2.5.0",
  "com.iheart" %% "ficus" % "1.2.0",
  "com.adrianhurt" %% "play-bootstrap" % "1.0-P25-B3",
  "com.mohiva" % "play-silhouette-testkit_2.11" % "4.0.0-BETA4"
)
