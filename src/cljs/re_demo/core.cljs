(ns re-demo.core
  ; (:require-macros [secretary.core :refer [defroute]])
  (:require [reagent.core :as r]
            [reagent.ratom :as r*]
            [re-frame.core :as f]
            [re-frame.loggers :as log]
            ; [secretary.core :as secretary]
            [re-demo.nav :as nav]
            [re-demo.views :as views]
            [re-demo.subs :as subs]
            [re-demo.events :as events]
            [devtools.core :as devtools]))

;; -- Debugging aids ----------------------------------------------------------
; (devtools/install!)       ;; we love https://github.com/binaryage/cljs-devtools
; (enable-console-print!)   ;; so that println writes to `console.log`

;; Silence handler overwrite warnings on cljs reload.
(log/set-loggers!
 {:warn (fn [& args]
          (when-not (re-find #"^re-frame: overwriting" (first args))
            (apply js/console.warn args)))})

;; Put an initial value into app-db.
;; The event handler for `:initialise-db` can be found in `events.cljs`
;; Using the sync version of dispatch means that value is in
;; place before we go onto the next step.
(f/dispatch-sync [:initialize])

;;;;;; Dispatch - domino 1
; (f/dispatch [:event-id  value1 value2])

(defn dispatch-timer-event
  []
  (let [now (js/Date.)]
    (f/dispatch [:timer now])))  ;; <-- dispatch used

;; call the dispatching function every second
(defonce do-timer (js/setInterval dispatch-timer-event 1000))

; (defn init!
;   []
;   (f/clear-subscription-cache!)
;   (some->> "container"
;            (.getElementById js/document)
;            ; (r/render [surrogate "bal"])
;            (r/render [nav/navbar])
;            (r/render [rfSimpleExample/ui])))

(defn init!
  []
  (f/clear-subscription-cache!)
  (f/dispatch-sync [:initialize])     ;; puts a value into application state
  (r/render [nav/navbar] (.getElementById js/document "nav")))
  ; (r/render [rfSimpleExmaple/ui] (.getElementById js/document "container")))
