(ns {{name}}.http
  (:require [clojure.tools.logging :refer [debug info error]]))


(defonce cors-headers
  ["X-Requested-With" "Origin" "X-Csrftoken" "Content-Type" "Accept" "accept"
   "origin" "Access-Control-Request-Method" "Access-Control-Request-Headers"
   "Authorization" "content-type" "auth-token" "Content-Disposition"])


(defn handle-exception
  "Log exceptions in Liberator at ERROR level.  Use this function as :handle-exception"
  [ctx]
  (let [e (:exception ctx)]
    (error "Liberator caught" (.getClass e) "message:" (.getMessage e))))
