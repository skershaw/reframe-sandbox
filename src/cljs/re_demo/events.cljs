(ns re-demo.events
  (:require [re-frame.core :as f]))

(f/reg-event-db :root/input
  (fn [db [_ value]]
    (assoc db :root/input value)))
