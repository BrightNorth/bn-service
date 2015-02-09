# {{name}}

FIXME

Clojure (1.6.0) application, using Compojure and Liberator for web service routing.

## Prerequisites

* JDK 1.7
* MySQL 
* Leiningen

Create the DB as follows:

```
mysql -uroot -p <enter> <enter>
create database {{sanitized}};
grant all on {{sanitized}}.* to '{{sanitized}}'@'localhost' identified by '{{sanitized}}';
flush privileges;
```

## Running

To start a web server for the application, run:

    lein ring server

or open a REPL

    lein repl

and init the application manually:

   (start-server)

(this saves you a ref to the Jetty server that you can use to `(stop-server)` and `(start-server)`

## Testing

For unit testing we are using [midje](https://github.com/marick/Midje). To execute unit tests run:

    lein test

## Deployment and releases

You can release the application with:

    lein release

## Configuration

Configuration options are now loaded from a configuration file. For dev this is set to pickup `application.conf` in the root dorectory.

If you run it with jar you need to provide path to configuration file using `DConfig option`:

    java -Dconfig=application.conf -jar target/{{name}}-0.1.0-SNAPSHOT-standalone.jar

