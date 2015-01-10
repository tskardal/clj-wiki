(defproject wiki "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [compojure "1.3.1"]
                 [hiccup "1.0.5"]
                 [clj-tagsoup "0.3.0"]
                 [ring-server "0.3.1"]
                 [com.novemberain/monger "2.0.0"]
                 [markdown-clj "0.9.62"]
                 [clojurewerkz/elastisch "2.1.0"]]
  :plugins [[lein-ring "0.8.12"]]
  :ring {:handler wiki.handler/app
         :init wiki.handler/init
         :destroy wiki.handler/destroy}
  :profiles
  {:uberjar {:aot :all}
   :production
   {:ring
    {:open-browser? false, :stacktraces? false, :auto-reload? false}}
   :dev
   {:dependencies [[ring-mock "0.1.5"]
                   [ring/ring-devel "1.3.1"]
                   [lein-midje "3.1.3"]]}})
