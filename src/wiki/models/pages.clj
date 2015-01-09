(ns wiki.models.pages
  (:require [monger.core :as mg]
            [monger.collection :as mc])
  (:import [org.bson.types ObjectId]))

(defn persist [page]
  (let [conn (mg/connect)
        db (mg/get-db conn "wikidb")]
    (mc/insert db "pages" (merge {:_id (ObjectId.)} page))))

(defn- lookup [name]
  (let [conn (mg/connect)
        db (mg/get-db conn "wikidb")]
    (mc/find-maps db "pages" {:name name})))

(defn- create-page [name]
  (let [page {:name name :content "This page does not exist! [[Test]]"}]
    (persist page)
    page))

(defn find-page [name]
  (let [page (lookup name)]
    (if (empty? (seq page))        
      (create-page name)
      page)))
