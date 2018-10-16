(ns re-demo.views
  (:require [reagent.core :as r]
            [reagent.ratom :as r*]
            [re-frame.core :as f]
            [re-frame.loggers :as log]
            [baking-soda.core :as b]))

(defn root []
  (r/with-let [counter (f/subscribe [:counter/value])]
              [b/Container
               [b/Row
                [b/Col
                 [b/Button {:class "mr-4"
                            :color "primary"
                            :on-click #(f/dispatch [:counter/inc])}
                  "Increment"]
                 @counter]]]))

(defn surrogate-instance [text]
  [:div
   {:style {:color "red"}
    :font-weight "bold"}
   text])

(defn surrogate [text]
  [:div
   [:p
    {:style {:color "red"}
     :font-weight "bold"}
    text]
   [surrogate-instance "si"]])

(defn root2 []
  (r/with-let [pointer (r/atom nil)
               handler #(swap! pointer assoc
                               :x (.-pageX %)
                               :y (.-pageY %))
               _ (.addEventListener js/document "mousemove" handler)]
              [:div
               "Pointer moved to: "
               (str @pointer)]
              (finally
                (.removeEventListener js/document "mousemove" handler))))

(defn modal-example [opts]
  (let [{:keys [button-label
                class]} opts]
        ; show-modal?     (get @ratom :show-modal? false)]
    [:div
     [b/Button {:color    "danger"
                :on-click #(f/dispatch [:toggle-modal])}
      button-label]

     [b/Modal {:is-open @(f/subscribe [:show-modal?])
               :toggle  @(f/subscribe [:show-modal?])
               :class   class}
      [b/ModalHeader
       "Modal title"]

      [b/ModalBody
       "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."]

      [b/ModalFooter
       [b/Button {:color    "primary"
                  :on-click #(f/dispatch [:toggle-modal])}
        "Do Something"]
       [b/Button {:color    "secondary"
                  :on-click #(f/dispatch [:toggle-modal])}
        "Cancel"]]]]))
