(ns leiningen.new.bn-webservice
  (:use [leiningen.new.templates :only [renderer name-to-path ->files]]))

(def render (renderer "bn-webservice"))

(defn bn-webservice
  "Creates a webservice with Flyway DB migrations, Korma DB, Liberator resources and conf-er configuration"
  [name]
  (let [data {:name name
              :sanitized (name-to-path name)}]
    (->files data
             "resources/public"
             "src/migrations"
             ["application.conf" (render "application.conf" data)]
             ["application-test.conf" (render "application-test.conf" data)]
             [".gitignore" (render "gitignore" data)]
             ["project.clj" (render "project.clj" data)]
             ["README.md" (render "README.md" data)]
             ["src/log4j.properties" (render "log4j.properties" data)]
             ["src/migrations/V201310221843__create.sql" (render "V201310221843__create.sql" data)]
             ["src/{{sanitized}}/core.clj" (render "core.clj" data)]
             ["test/{{sanitized}}/core_test.clj" (render "core_test.clj" data)]
             ["src/{{sanitized}}/db.clj" (render "db.clj" data)])))
