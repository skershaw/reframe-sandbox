(ns re-demo.subs
  (:require [re-frame.core :as f]))

(f/reg-sub :root/input
  (fn [db _]
    (:root/input db)))
