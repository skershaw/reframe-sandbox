(set-env!
 :source-paths    #{"src/cljs"}
 :resource-paths  #{"src/resources"}
 :dependencies '[; Dev deps
                 [adzerk/boot-cljs          "2.1.4"      :scope "test"]
                 [adzerk/boot-cljs-repl     "0.3.3"      :scope "test"]
                 [adzerk/boot-reload        "0.5.2"      :scope "test"]
                 [com.cemerick/piggieback   "0.2.2"      :scope "test"]
                 [org.clojure/tools.nrepl   "0.2.13"     :scope "test"]
                 [pandeiro/boot-http        "0.8.3"      :scope "test"]
                 [weasel                    "0.7.0"      :scope "test"]
                 [binaryage/devtools        "0.9.7"    :scope "test"]
                 [day8.re-frame/trace       "0.1.11"   :scope "test"]

                 ;; Project deps
                 [org.clojure/clojure       "1.9.0-beta4"]
                 [org.clojure/clojurescript "1.9.946"]
                 [reagent                   "0.7.0"]
                 [re-frame                  "0.10.2"]
                 [prismatic/schema          "1.1.6"]])

(require
 '[adzerk.boot-cljs      :refer [cljs]]
 '[adzerk.boot-cljs-repl :refer [cljs-repl start-repl]]
 '[adzerk.boot-reload    :refer [reload]]
 '[pandeiro.boot-http    :refer [serve]])

(task-options! serve  {:port 3001 :dir "resources"}
               reload {:on-jsload 're-demo.core/init!})

(def compiler-options-dev
  {:preloads        '[day8.re-frame.trace.preload
                      devtools.preload]
   :closure-defines {"re_frame.trace.trace_enabled_QMARK_" true}})

(deftask dev []
  (comp (serve)
        (watch)
        (cljs-repl)
        (reload)
        (cljs :source-map true
              :optimizations :none
              :compiler-options compiler-options-dev)
        (target :dir #{"resources"})))

