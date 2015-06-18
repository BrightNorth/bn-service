(ns leiningen.new.bn-service
  (:require [leiningen.new.templates :refer [renderer name-to-path ->files]]))


(def render (renderer "bn-service"))


(defn bn-service
  "Create a new project"
  [name]
  (let [data {:name      name
              :sanitized (name-to-path name)}]
    (->files data
      "resources/public"
      "src/migrations"
      ["application.conf" (render "application.conf" data)]
      ["application-test.conf" (render "application-test.conf" data)]
      [".gitignore" (render "gitignore" data)]
      ["project.clj" (render "project.clj" data)]
      ["README.md" (render "README.md" data)]
      ["src/logback.xml" (render "logback.xml" data)]
      ["src/migrations/V201310221843__create.sql" (render "V201310221843__create.sql" data)]
      ["src/{{sanitized}}/core.clj" (render "core.clj" data)]
      ["src/{{sanitized}}/db.clj" (render "db.clj" data)]
      ["src/{{sanitized}}/http.clj" (render "http.clj" data)]
      ["test/{{sanitized}}/core_test.clj" (render "core_test.clj" data)]
      ["test/{{sanitized}}/integration_test.clj" (render "integration_test.clj" data)])))
