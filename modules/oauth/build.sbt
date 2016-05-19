lazy val projectName = "oauth"

name := projectName

libraryDependencies += "com.nulab-inc" %% "play2-oauth2-provider" % "0.17.0"

//casbah
libraryDependencies += "org.mongodb" %% "casbah" % "3.1.0"
