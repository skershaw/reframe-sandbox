(ns re-demo.core
  (:require [cljs.loader :as loader]
            [reagent.core :as r]))

(def state
  (r/atom {:input "Hello, world!"}))

(defn event->value
  [e]
  (-> e .-target .-value))

(defn input-handler
  [app e]
  (let [value (event->value e)]
   (loader/load :data
     (fn []
       ((resolve 're-demo.data/update-input) app value)))))

(defn root
  [app]
  [:input {:value (:input @app)
           :on-change (partial input-handler app)}])

(defn init!
  []
  (r/render [root state]
    (.getElementById js/document "container")))

(defonce _
  (loader/set-loaded! :core))
