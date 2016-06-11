logLevel := Level.Warn

resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

// The Play plugin
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.5.3")

addSbtPlugin("com.heroku" % "sbt-heroku" % "1.0.0")
