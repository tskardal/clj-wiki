(ns wiki.routes.home
  (:require [compojure.core :refer :all]
            [ring.util.response :refer [redirect]]
            [hiccup.form :refer [form-to]]
            [markdown.core :refer [md-to-html-string]]
            [wiki.views.layout :as layout]
            [wiki.models.pages :as pages]
            [pl.danieljanus.tagsoup :refer [parse-string]]))

(defn home []
  (layout/common [:h1 "Hello World!"]))

;; TODO bytt til reagent og lag edit-knapp og textarea for endring av innhold
(defn- render-content [{content :content :as page}]
  (let [with-links (clojure.string/replace content #"(\[\[(.+)\]\])" "<a href=\"/$2\">$2</a>")
        md (md-to-html-string with-links)]
    [:p (parse-string (str "<div>" md "</div>"))]))

(defn render-page [page-name]
  (let [page (pages/find-page page-name)]    
    (layout/common
     [:div
      [:h2 page-name]
      [:a {:href (str "/edit/" page-name)} "Edit"]
      (render-content page)])))

(defn edit-page [page-name]
  (let [page (pages/find-page page-name)]
    (println "type: " (type  page))
    (layout/common
     [:div
      [:h2 "Edit page"]
      [:h3 page-name]
      (form-to [:post ""]
               [:textarea {:name "content" :cols 80 :rows 40} (:content page)]
               [:input {:type "submit" :value "Hoist"}])])))

(defn save-page [page content]
  (pages/save page content)
  (redirect (str "/" page)))

(defroutes home-routes
  (GET "/" [] (render-page "Welcome to this wiki"))
  (GET "/edit/:page" [page] (edit-page page))
  (POST "/edit/:page" {{:keys [page content]} :params} (save-page page content))
  (GET "/:page" [page] (render-page page)))
