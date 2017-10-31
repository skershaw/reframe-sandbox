(ns re-demo.data
  (:require [cljs.loader :as loader]
            [reagent.core :as r]))

(def state
  (r/atom {}))

(defn update-input
  [app value]
  (swap! app assoc :input value))

(loader/set-loaded! :data)
