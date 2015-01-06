(ns wiki.routes.home
  (:require [compojure.core :refer :all]
            [wiki.views.layout :as layout]
            [wiki.models.pages :as pages]
            [pl.danieljanus.tagsoup :refer [parse-string]]))

(defn home []
  (layout/common [:h1 "Hello World!"]))

;; (clojure.string/replace comment
;; #"#(\d+)"
;; (str "[#$1](https://github.com/" repo "/issues/$1)")))

(defn- render-content [{content :content :as page}]
  (let [with-links (clojure.string/replace content #"(\[\[(.+)\]\])" "<a href=\"/$2\">$2</a>")]
    [:p (parse-string (str "<div>" with-links "</div>"))]))

(defn render-page [page-name]
  (let [page (pages/find page-name)]
    (layout/common
     [:div
      [:h2 page-name]
      (render-content page)])))

(defroutes home-routes
  (GET "/" [] (home))
  (GET "/:page" [page] (render-page page)))
