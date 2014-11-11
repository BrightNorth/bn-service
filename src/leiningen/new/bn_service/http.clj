(ns {{name}}.http
  (:require [clojure.tools.logging :refer [debug info error]]))


(defonce cors-headers
  {"Access-Control-Allow-Origin"  #".*"
   "Access-Control-Allow-Methods" [:get :put :post :delete :options]
   "Access-Control-Allow-Headers" "X-Requested-With, Origin, X-Csrftoken, Content-Type, Accept, Authorization"})


(defn handle-exception
  "Log exceptions in Liberator at ERROR level.  Use this function as :handle-exception"
  [ctx]
  (let [e (:exception ctx)]
    (error "Liberator caught" (.getClass e) "message:" (.getMessage e))))
