(ns re-demo.subs
  (:require [reagent.core :as r]
            [reagent.ratom :as r*]
            [re-frame.core :as f]
            [re-frame.loggers :as log]
            [baking-soda.core :as b]))

;;;;;; Subscription handlers - domino 4
; (rf/reg-sub
;   :some-query-id  ;; query id (used later in subscribe)
;   a-query-fn)     ;; the function which will compute the query


(f/reg-sub :counter/value
           (fn [db _]
             (get db ::counter 0)))

(f/reg-sub :show-modal?
           (fn [db _]
             (get db ::show-modal?)))
