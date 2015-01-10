(ns wiki.search.elastic
  (:require [clojurewerkz.elastisch.rest :as esr]
            [clojurewerkz.elastisch.rest.index :as esi]
            [clojurewerkz.elastisch.rest.document :as esd]))

(defn store-index [page]
  (let [conn (esr/connect "http://localhost:9200")]    
    (esd/create conn "myapp1_development" "page" page)))

(defn setup []
  (let [conn (esr/connect "http://localhost:9200")]
    (esi/create conn "myapp1_development")))
