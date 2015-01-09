(ns wiki.routes.home
  (:require [compojure.core :refer :all]
            [wiki.views.layout :as layout]
            [wiki.models.pages :as pages]
            [pl.danieljanus.tagsoup :refer [parse-string]]))

(defn home []
  (layout/common [:h1 "Hello World!"]))

;; TODO bytt til reagent og lag edit-knapp og textarea for endring av innhold
(defn- render-content [{content :content :as page}]
  (let [with-links (clojure.string/replace content #"(\[\[(.+)\]\])" "<a href=\"/$2\">$2</a>")]
    [:p (parse-string (str "<div>" with-links "</div>"))]))

(defn render-page [page-name]
  (let [page (pages/find-page page-name)]    
    (layout/common
     [:div
      [:h2 page-name]
      (render-content page)])))

(defroutes home-routes
  (GET "/" [] (home))
  (GET "/:page" [page] (render-page page)))
