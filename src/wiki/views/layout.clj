(ns wiki.views.layout
  (:require [hiccup.page :refer [html5 include-css]]
            [hiccup.form :refer [form-to]]))

(defn common [& body]
  (html5
    [:head
     [:title "Welcome to wiki"]
     (include-css "/css/screen.css")]
    [:body
     [:div#menu
      (form-to [:get "/search"]
               [:input {:type "text" :name "q" :placeholder "Search for stuff!"}]
               [:input {:type "submit" :value "Search"}])]
     [:div#content body]]))
