(ns re-demo.core
  (:require [reagent.core :as r]
            [reagent.ratom :as r*]
            [re-frame.core :as f]
            [re-frame.loggers :as log]
            [baking-soda.core :as b]))

;; Silence handler overwrite warnings on cljs reload.
(log/set-loggers!
 {:warn (fn [& args]
          (when-not (re-find #"^re-frame: overwriting" (first args))
            (apply js/console.warn args)))})

(f/reg-sub :counter/value
  (fn [db _]
    (get db ::counter 0)))

(f/reg-event-db :counter/inc
  (fn [db _]
    (update db ::counter #(if % (inc %) 1))))

(defn root
  []
  (r/with-let [counter (f/subscribe [:counter/value])]
    [b/Container
     [b/Row
      [b/Col
       [b/Button {:class "mr-4"
                  :color "primary"
                  :on-click #(f/dispatch [:counter/inc])}
        "Increment"]
       @counter]]]))

(defn init!
  []
  (f/clear-subscription-cache!)
  (some->> "container"
           (.getElementById js/document)
           (r/render [root])))
