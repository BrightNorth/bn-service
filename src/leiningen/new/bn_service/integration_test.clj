(ns {{name}}.integration-test
  (:use midje.sweet)
  (:require [clj-http.client :as client]
            [{{name}}.core :refer :all]
            [{{name}}.db :refer :all]))


(def cfg {:throw-exceptions false
          :content-type     :json
          :as               :json})


(with-state-changes [(before :contents (start-server 1234))
                     (after :contents (stop-server))
                     (before :facts (clean-db!))
                     (after :facts (clean-db!))]


  (fact "maths is fun"
    (+ (- (+ 12 3) 4) 5 67 8 9) => 100))
