(ns wiki.views.layout
  (:require [hiccup.page :refer [html5 include-css include-js]]
            [hiccup.form :refer [form-to]]))

(defn common [& body]
  (html5 {:lang "en"}
   [:head
    [:meta {:charset "utf-8"}]
    [:meta {:http-equiv "X-UA-Compatible" :content "IE=edge"}]
    [:meta {:name "viewport" :content "width=device-width, initial-scale=1"}]
    [:title "Welcome to wiki"]
    (include-css "/css/bootstrap.min.css")
    (include-css "/css/screen.css")]
   [:body
    [:nav.navbar.navbar-inverse.navbar-fixed-top
     [:div.container
      [:div.navbar-header
       [:button {:type "button" :class "navbar-toggle collapsed"
                 :data-toggle "collapse" :data-target "#navbar"
                 :aria-expanded "false" :aria-controls="navbar"}
        [:span.sr-only "Toggle navigation"]
        [:span.icon-bar]
        [:span.icon-bar]
        [:span.icon-bar]]
       [:a.navbar-brand {:href "#"} "Weekkee"]]
      [:div#navbar {:class "navbar-collapse collapse"}
       (form-to {:class "navbar-form navbar-right"} [:get "/search"]
                [:div.form-group
                 [:input.form-control {:type "text" :name "q" :placeholder "Looking for something?"}]]                                
                [:button.btn.btn-success {:type "submit"} "Search"])]]]        
    [:div {:class "container"}     
     body]
    (include-js "https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"
                "js/bootstrap.min.js")]))
