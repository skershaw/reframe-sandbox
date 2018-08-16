(set-env!
 :source-paths   #{"src/cljs"}
 :resource-paths #{"src/resources"}
 :dependencies   '[;; Project deps
                   [org.clojure/clojure           "1.9.0"]
                   [org.clojure/clojurescript     "1.10.339"]
                   [reagent                       "0.8.1"      :exclusions [cljsjs/react cljsjs/react-dom]]
                   [cljsjs/react-transition-group "2.3.1-0"]
                   [cljsjs/react-popper           "0.10.4-0"]
                   [re-frame                      "0.10.5"]
                   [cljsjs/react                  "16.3.2-0"]
                   [cljsjs/react-dom              "16.3.2-0"]
                   [baking-soda                   "0.2.0"]

                                        ; Dev deps
                   [adzerk/boot-cljs              "2.1.4"      :scope "test"]
                   [adzerk/boot-cljs-repl         "0.3.3"      :scope "test"]
                   [adzerk/boot-reload            "0.5.2"      :scope "test"]
                   [com.cemerick/piggieback       "0.2.2"      :scope "test"]
                   [org.clojure/tools.nrepl       "0.2.13"     :scope "test"]
                   [pandeiro/boot-http            "0.8.3"      :scope "test"]
                   [weasel                        "0.7.0"      :scope "test"]])

(require
 '[adzerk.boot-cljs      :refer [cljs]]
 '[adzerk.boot-cljs-repl :refer [cljs-repl start-repl]]
 '[adzerk.boot-reload    :refer [reload]]
 '[pandeiro.boot-http    :refer [serve]])

(task-options! serve  {:port 3001 :dir "resources"}
               reload {:on-jsload 're-demo.core/init!})

(deftask dev []
  (comp (serve)
        (watch)
        (cljs-repl)
        (reload)
        (cljs :source-map true
              :optimizations :none)
        (target :dir #{"resources"})))
