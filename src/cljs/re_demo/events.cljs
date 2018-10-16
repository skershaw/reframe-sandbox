(ns re-demo.events
  (:require [reagent.core :as r]
            [reagent.ratom :as r*]
            [re-frame.core :as f]))

;;;;;; Register Events - domino 2
; (f/reg-event-db
;  :the-event-id
;  the-event-handler-fn) ;; (fn [db event-vector] ... modified-db)

(f/reg-event-db              ;; sets up initial application state - why not a ratom??
 :initialize
 (fn [_ _]                   ;; the two parameters are not important here, so use _
   {:time (js/Date.)         ;; What it returns becomes the new application state
    :time-color "#f88"       ;;
    :active-tab "home"
    :counter 0
    :show-modal? false}))    ;; so the application state will initially be a map with two keys

(f/reg-event-db                 ;; usage:  (rf/dispatch [:timer a-js-Date])
 :timer
 (fn [db [_ new-time]]          ;; <-- de-structure the event vector
   (assoc db :time new-time)))  ;; compute and return the new application state

(f/reg-event-db
 :counter/inc
 (fn [db _]
   (update db ::counter #(if % (inc %) 1))))

(f/reg-event-db
 :toggle-modal
 (fn [db _]
   ; (assoc db :show-modal? (not :show-modal?))
   (assoc db :show-modal? true)))

(f/reg-event-db                ;; usage:  (dispatch [:time-color-change 34562])
 :time-color-change            ;; dispatched when the user enters a new colour into the UI text field
 (fn [db [_ new-color-value]]  ;; -db event handlers given 2 parameters:  current application state and event (a vector)
   (assoc db :time-color new-color-value)))
