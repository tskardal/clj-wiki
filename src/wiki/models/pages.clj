(ns wiki.models.pages
  (:require [monger.core :as mg]
            [monger.collection :as mc]
            [monger.operators :refer [$set]])
  (:import [org.bson.types ObjectId]))

(defn persist [page]
  (let [conn (mg/connect)
        db (mg/get-db conn "wikidb")]
    (mc/update db "pages" {:name (:name page)} {$set page} {:upsert true})))

(defn- lookup [name]
  (let [conn (mg/connect)
        db (mg/get-db conn "wikidb")]
    (mc/find-one-as-map db "pages" {:name name})))

(defn- create-page [name]
  (let [page {:name name :content "This page does not exist! You can create it by editing this page and save it!"}]
    page))

(defn save [page content]
  (persist {:name page :content content}))

(defn find-page [name]
  (let [page (lookup name)]
    (if (empty? (seq page))        
      (create-page name)
      page)))
