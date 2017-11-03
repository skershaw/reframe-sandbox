(ns re-demo.core
  (:require [reagent.core :as r]
            [re-frame.core :as f]
            [re-frame.loggers :as log]

            ;; Requried for use.
            [re-demo.subs]
            [re-demo.events]))

;; Silence handler overwrite warnings on cljs reload.
(log/set-loggers!
 {:warn (fn [& args]
          (when-not (re-find #"^re-frame: overwriting" (first args))
            (apply js/console.warn args)))})

(defn event->value
  [e]
  (-> e .-target .-value))

(defn root
  []
  (let [input (f/subscribe [:root/input])]
    (fn []
      [:span
       [:b "Input "]
       [:input {:value @input
                :on-change #(f/dispatch [:root/input (event->value %)])}]])))

(defn init!
  []
  (f/clear-subscription-cache!)
  (some->> "container"
           (.getElementById js/document)
           (r/render [root])))
