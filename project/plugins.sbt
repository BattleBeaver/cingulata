logLevel := Level.Warn

resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

resolvers += Resolver.url("bintray-kipsigman-sbt-plugins", url("http://dl.bintray.com/kipsigman/sbt-plugins"))(Resolver.ivyStylePatterns)

// The Play plugin
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.4.6")

addSbtPlugin("kipsigman" % "sbt-elastic-beanstalk" % "0.1.2")

// To get build info in the app
addSbtPlugin("com.eed3si9n" % "sbt-buildinfo" % "0.5.0")
