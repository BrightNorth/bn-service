(ns {{name}}.db
  (:require [clojure.java.jdbc :as jdbc]
   [clojure.tools.logging :refer [debug info error warn]]
   [conf-er :refer [config]]
   [korma.core :refer :all]
   [korma.db :refer :all])
  (:import com.googlecode.flyway.core.Flyway
   com.googlecode.flyway.core.exception.FlywayException))


(def db-config (config :database))


(defn migrate!
  []
  (let [flyway (Flyway.)
        table "schema_version_{{sanitized}}"
        user (:user db-config)
        password (:password db-config)
        host (:host db-config)
        port (:port db-config)
        db (:db db-config)
        db-url (str "jdbc:mysql://" host ":" port "/" db)]
    (info "Running migrations for user:" user "with url:" db-url "into:" table)
    (.setTable flyway table)
    (.setLocations flyway (into-array ["migrations"]))
    (.setDataSource flyway db-url user password)
    (try
      (.init flyway)
      (catch FlywayException e
        (warn "Caught exception in Flyway init:" (.getMessage e))))
    (let [count (.migrate flyway)]
      (info "Migrations complete:" count))))


(defdb db (mysql db-config))


(defentity foobar)


(defn clean-db!
  "DANGER!  HIGH VOLTAGE!  Do not use in production!"
  []
  (debug "Clearing db")
  (doseq [table [foobar]]
    (delete table)))
