(ns re-demo.nav
  (:require [reagent.core :as r]
            [reagent.ratom :as r*]
            [re-frame.core :as f]
            [re-frame.loggers :as log]
            [baking-soda.core :as b]
            [re-demo.modal :as modal]
            [re-demo.views :as views]
            [re-demo.rfSimpleExmaple :as rfSimpleExmaple]
            [keybind.core :as key]))

(key/bind! "ctrl-t" ::my-trigger2 #(modal/toggle! modal/modal-app-state))

(defonce app-state
  (r/atom {:page "home"}))

(defn nav-link [uri title page]
  [b/NavItem
   [b/NavLink
    {:href   uri
     :active (when (= page (:page @app-state)) "active")}
    title]])

(defn navbar []
  (r/with-let [expanded? (r/atom true)]
              [b/Navbar {:light true
                         :class-name "navbar-dark bg-primary"
                         :expand "md"}
               [b/NavbarBrand {:href "/"} "<<name>>"]
               [b/NavbarToggler {:on-click #(swap! expanded? not)}]
               [b/Collapse {:is-open @expanded? :navbar true}
                [b/Nav {:class-name "mr-auto" :navbar true}
                 [nav-link "#/" "Home" :home]
                 [nav-link "#/about" "About" :about]]
                [:div
                 [views/modal-example {:button-label "Click Me"
                                       :class "mymodal"}]]]]))
                ;  [:label "input a key to bind to toggle modal (+ctrl)"]
                ;  [:input {:type "text"}]
                ;  [b/Button
                ;   {:on-click (fn [] (fn [] (key/bind! "ctrl-g" ::my-trigger2 #(modal/toggle! modal/modal-app-state))))}
                ;   "bind key"]
                ;  [b/Button
                ;   {:on-click #(.log js/console "button")}
                ;   "log"]]
                ; [:div
                ;  [b/Button
                ;   {:on-click (r/render [rfSimpleExmaple/ui] (.getElementById js/document "container"))}
                ;   "rfSimpleExmaple/run"]]]]))

(defn about-page []
  [:div.container
   [:div.row
    [:div.col-md-12
     [:p "lorem ipsum"]]]])

(defn home-page []
  [:div.container
   (when-let [docs (:docs @app-state)]
     [:div.row>div.col-sm-12
      [:div "lorem lorem"]])])

(def pages
  {:home #'home-page
   :about #'about-page})

(defn page []
  [(pages (:page @app-state))])

; select page
(defn root
  "Draws app window when client systems have successfully booted."
  []
  [#'navbar]
  [#'page])

  ; (r/render [#'navbar] (.getElementById js/document "navbar"))
  ; (r/render [#'page] (.getElementById js/document "app")))
  ; (r/with-let [status (f/subscribe [app-state])]
  ;             (case @app-state
  ;               :success [app/window]
  ;               :loading [app/loading]
  ;               :error   [app/error]
  ;               [:div nil])))
