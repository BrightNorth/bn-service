(ns {{name}}.core
  (:gen-class :main true)
  (:require
   [cheshire.core :refer [generate-string parse-string]]
   [clojure.java.io :refer [reader]]
   [clojure.tools.logging :refer [debug info error]]
   [compojure.core :refer [defroutes ANY GET]]
   [compojure.handler :as handler]
   [compojure.route :as route]
   [conf-er :refer [config]]
   [liberator.core :refer [resource defresource]]
   [liberator.dev :refer [wrap-trace]]
   [liberator.representation :refer [ring-response]]
   [ring.adapter.jetty :refer [run-jetty]]
   [ring.middleware.cors :refer [wrap-cors]]
   [ring.middleware.json :refer :all]
   [ring.middleware.params :refer :all]
   [ring.util.response :refer [response header content-type]]
   [ring.util.response]
   [{{name}}.db :refer :all]
   [{{name}}.http :refer :all]))


(def resource-defaults
  {:available-media-types ["application/json"]
   :handle-exception      handle-exception})


(defresource thing-resource resource-defaults
  :allowed-methods [:get :post :put :delete]
  :post! (fn [ctx] (info "Do something to create a thing here. You've got the whole context available:" ctx))
  :handle-ok (fn [ctx] (info "Handler for 200") {:json-key "json-value"})
  :handle-created (fn [ctx] {:message "CREATED"}))


(defroutes app-routes
  (ANY "/thing" [] thing-resource)
  (GET "/" [] {:status  200
               :headers {"Content-Type" "text/html; charset=utf-8"}
               :body    "<html><body><h1>{{name}}</h1></body></html>"})
  (route/resources "/")
  (route/not-found "Not Found"))


(defn app
  []
  (-> app-routes
    (wrap-json-body {:keywords? true})
    (handler/site)
    (wrap-cors
      :access-control-allow-origin #".*"
      :access-control-allow-methods [:get :put :post :delete :options]
      :access-control-allow-headers cors-headers
      :access-control-allow-credentials "true"
      :p3p "CP='FIXME'") ;; TODO - provide a link to the privacy policy in this string at the very least
    (wrap-trace :header :ui)))


(def handler (app))


(defn post-init!
  []
  (info "{{name}} post-init tasks starting")
  (migrate!)
  (info "{{name}} post-init tasks complete"))


(def server (atom nil))


(defn stop-server
  []
  (info "{{name}} service stopping")
  (.stop @server)
  (info "{{name}} service stopped"))


(defn start-server
  ([]
   (start-server (config :port)))
  ([port]
   (info "{{name}} service starting")
   (swap! server (fn [_] (run-jetty handler {:port port :join? false})))
   (post-init!)
   (info "{{name}} started on port" port)))


(defn -main [& args]
  (start-server))
