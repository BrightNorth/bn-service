(defproject {{name}} "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [compojure "1.1.8"]                 
                 [ring/ring-jetty-adapter "1.3.0"]
                 [liberator "0.12.0"]
                 [cheshire "5.3.1"]
                 [clj-http "1.0.0"]
                 [clj-time "0.8.0"]
                 [conf-er "1.0.1"]

                 [mysql/mysql-connector-java "5.1.31"]
                 [korma "0.3.3"]
                 [com.googlecode.flyway/flyway-core "2.3.1"]

                 [org.slf4j/slf4j-simple "1.7.7"]
                 [org.clojure/tools.logging "0.3.0"]
                 [log4j/log4j "1.2.17" :exclusions [javax.mail/mail
                                                    javax.jms/jms
                                                    com.sun.jdmk/jmxtools
                                                    com.sun.jmx/jmxri]]]

  :ring {:handler {{name}}.core/handler
         :init {{name}}.core/post-init!
         :port 4000}
  :jvm-opts ["-Dconfig=application.conf"]
  :main {{name}}.core
  :plugins [[lein-ring "0.8.5"] [lein-midje "3.1.1"]]
  :aot [{{name}}.core]
  :profiles {:test {:jvm-opts ["-Dconfig=application-test.conf"]
                    :resource-paths ["test-resources"]}
             :dev {
                   :dependencies [[ring-mock "0.1.5"]
                                  [midje "1.6.3"]
                                  [ring/ring-devel "1.3.0"]]
                   }
             }

  )

