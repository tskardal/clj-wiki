(ns wiki.search.elastic
  (:require [clojurewerkz.elastisch.rest :as esr]
            [clojurewerkz.elastisch.rest.index :as esi]
            [clojurewerkz.elastisch.rest.document :as esd]
            [clojurewerkz.elastisch.query         :as q]
            [clojurewerkz.elastisch.rest.response :as esrsp]
            [clojure.pprint :as pp]))

(defn store-index [page]
  (let [conn (esr/connect "http://localhost:9200")]    
    (esd/put conn "myapp1_development" "page" (:_id page) page)))

(defn search [query]
  (let [conn (esr/connect "http://127.0.0.1:9200")
        res  (esd/search conn "myapp1_development" "page"
                         :query (q/match :content query)
                         :highlight {:fields {:content {}}
                                     :pre_tags ["**"]
                                     :post_tags ["**"]})
        n    (esrsp/total-hits res)
        hits (esrsp/hits-from res)]
    (println (format "Total hits: %d" n))
    (pp/pprint hits)
    hits))

(defn setup []
  (let [conn (esr/connect "http://localhost:9200")
        mapping-types {"page" {:properties {:_id {:store true :index "not_analyzed"}}}}]
    (esi/create conn "myapp1_development" :mappings mapping-types)))
