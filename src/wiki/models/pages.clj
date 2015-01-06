(ns wiki.models.pages)

;; TODO: persist
(def pages (atom {}))

(defn- create-page [name]  
  (let [page {:id name :content "This page does not exist!"}]
    (swap! pages assoc (keyword name) page)))

(defn find [name]
  (if-let [page (@pages (keyword name))]
    page
    ((create-page name) (keyword name))))
