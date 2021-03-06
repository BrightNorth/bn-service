(defproject {{name}} "0.1.0-SNAPSHOT"

  :description "FIXME: write description"

  :url "http://example.com/FIXME"

  :dependencies [[ch.qos.logback/logback-core "1.1.3"]
                 [ch.qos.logback/logback-classic "1.1.3"]
                 [cheshire "5.4.0"]
                 [clj-http "1.1.2"]
                 [clj-time "0.9.0"]
                 [compojure "1.3.4"]
                 [conf-er "1.0.1"]
                 [korma "0.4.0"]
                 [liberator "0.12.2"]
                 [mysql/mysql-connector-java "5.1.35"]
                 [org.clojure/clojure "1.6.0"]
                 [org.clojure/tools.logging "0.3.1"]
                 [org.eclipse.jetty/jetty-server "7.6.8.v20121106"] ;; don't upgrade past v7 until https://github.com/ring-clojure/ring/issues/183
                 [org.eclipse.jetty/jetty-servlet "7.6.8.v20121106"] ;; don't upgrade past v7 until https://github.com/ring-clojure/ring/issues/183
                 [org.flywaydb/flyway-core "3.2.1"]
                 [org.slf4j/slf4j-api "1.7.12"]
                 [ring/ring-jetty-adapter "1.3.2"]
                 [ring/ring-json "0.3.1"]
                 [ring-cors "0.1.6"]]

  :plugins [[lein-midje "3.1.3"]
            [lein-release "1.1.3"]
            [lein-ring "0.9.3"]
            [brightnorth/uberjar-deploy "1.0.1"]]

  :ring {:handler {{name}}.core/handler
         :init {{name}}.core/post-init!
         :port    4000}

  :jvm-opts ["-Dconfig=application.conf"]

  :main {{name}}.core

  :aot [{{name}}.core]

  :profiles {:test {:jvm-opts       ["-Dconfig=application-test.conf"]
                    :resource-paths ["test-resources"]}
             :dev  {:dependencies [[ring-mock "0.1.5"]
                                   [midje "1.6.3"]
                                   [ring/ring-devel "1.3.2"]]}}

  :aliases {"test" ["do" "clean" ["with-profile" "+test" "midje"]]})
