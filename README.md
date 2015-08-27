# bn-service

A Leiningen template for creating Bright North's standard API services

Includes a stack of useful Clojure libraries and patterns:

 * [Compojure](https://github.com/weavejester/compojure) routing
 * A sensible default set of ring middleware
 * Setup for both lein ring server and uberjar invocation
 * [Korma](http://sqlkorma.com) for SQL manipulation
 * [Midje](http://github.com/marick/Midje) for testing
 * [conf-er](https://github.com/TouchType/conf-er) for configuring the app (db details etc)

And some wrappers round useful Java libraries:

 * [ThymeLeaf](http://www.thymeleaf.org/) integration for templating
 * [Flyway](http://flywaydb.org/) database migrations


## Usage

First grab the template and install it (it's not on clojars ... yet)

      git clone https://github.com/BrightNorth/bn-service
      cd bn-service
      lein install

Then edit your ''~/.lein/profiles.clj'' to include the plugin

     {:user {:plugins [[bn-service/lein-template "0.1.0-SNAPSHOT"]]}}

Then you can create a project using the template as follows:

     cd <my-working-directory>
     lein new bn-service <project-name>
     lein ring server

## Todo
Create default Midje tests

## License

Copyright © 2013 Bright North (http://www.brightnorth.co.uk)

Distributed under the Eclipse Public License, the same as Clojure.
