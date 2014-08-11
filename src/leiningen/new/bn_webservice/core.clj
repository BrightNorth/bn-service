(ns {{name}}.core
  (:gen-class :main true)
  (:use compojure.core)
  (:require [compojure.handler :as handler]
            [{{name}}.db :refer :all]
            [compojure.route :as route]
            [liberator.core :refer [resource defresource]]
            [liberator.representation :refer [ring-response]]
            [ring.util.response]
            [clj-time.core]
            [clj-time.coerce]
            [clojure.walk :refer :all]
            [clojure.data.json]
            [clojure.tools.logging :refer [debug info error]]
            [cheshire.core :refer [generate-string parse-string]]
            [clojure.java.io :refer [reader]]
            [korma.core :refer [defentity insert select values fields where group join has-many belongs-to with dry-run]]
            [korma.db :refer [defdb mysql]]
            [ring.middleware.reload :refer :all]
            [ring.middleware.params :refer :all]
            [ring.util.response :refer [response header content-type]]
            [ring.adapter.jetty :refer [run-jetty]]
            [conf-er :refer [config]]))

;; Ring port
(def PORT (config :port))


;; Reference to the running server
(def svr (atom nil))


;; Set of headers returned by resources; set up to allow COR requests.
(defonce cors-headers
  { "Accept"                       "application/json",
    "Content-Type"                 "application/json;charset=UTF-8"
    "Access-Control-Allow-Origin"  "*"
    "Access-Control-Allow-Headers" "X-Requested-With, Origin, X-Csrftoken, Content-Type, Accept"
    "Access-Control-Allow-Methods" "GET, POST, OPTIONS" })


(defresource thing-resource
  :available-media-types ["application/json"]
  :allowed-methods [:get :post :options]
  :handle-options (fn [ctx] (ring-response {:headers cors-headers }))
  :post! (fn [ctx] (info "Do something to create a thing here. You've got the whole context available:" ctx))
  :handle-ok (fn [ctx] (info "Handler for 200") (ring-response { :headers cors-headers :body "Description of thing"}))
  :handle-created (fn [ctx] (ring-response {:headers cors-headers :body (generate-string {:message "CREATED"})})))


(defroutes app-routes
  (ANY "/thing" [] thing-resource)
  (GET "/" [] "BN Web Service")
  (route/resources "/")  
  (route/not-found "Not Found"))


(defn post-init!
  []
  (migrate!))


(def handler
  "Represents the wrapped set of configured routes and handlers - WITHOUT starting the server or post-init!"
  (handler/api app-routes))


(defn stop-server
  "Stop the server"
  []
  (info "{{name}} stopping")
  (.stop @svr))


(defn start-server []
  "Call this from the REPL to setup the DB and start the server"
  (swap! svr (fn [_] (run-jetty handler {:port PORT :join? false})))
  (post-init!)
  (info "{{name}} started on port" PORT))


(defn -main [& args]
  (start-server))
