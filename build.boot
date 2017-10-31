(set-env!
 :source-paths    #{"src/cljs"}
 :resource-paths  #{"src/resources"}
 :dependencies '[[adzerk/boot-cljs          "2.1.4-SNAPSHOT"]
                 [adzerk/boot-cljs-repl     "0.3.3"      :scope "test"]
                 [adzerk/boot-reload        "0.5.2"      :scope "test"]
                 [com.cemerick/piggieback   "0.2.2"      :scope "test"]
                 [org.clojure/clojure       "1.9.0-beta2"]
                 [org.clojure/clojurescript "1.9.946"]
                 [org.clojure/tools.nrepl   "0.2.13"     :scope "test"]
                 [reagent                   "0.8.0-alpha2"]
                 [prismatic/schema          "1.1.6"]
                 [pandeiro/boot-http        "0.8.3"]
                 [weasel                    "0.7.0"      :scope "test"]])

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
        (cljs)
        (target :dir #{"resources"})))

