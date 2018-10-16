(set-env!
 :init-ns 're-demo.core
 :source-paths   #{"src/cljs"}
 :resource-paths #{"src/resources"}
 :jvm-opts ["--add-modules" "java.xml.bind"]
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
                   [keybind                       "2.2.0"]

                                        ; Dev deps
                   [adzerk/boot-cljs              "2.1.4"      :scope "test"]
                   [adzerk/boot-cljs-repl         "0.3.3"      :scope "test"]
                   [adzerk/boot-reload            "0.5.2"      :scope "test"]
                   [com.cemerick/piggieback       "0.2.2"      :scope "test"]
                   [org.clojure/tools.nrepl       "0.2.13"     :scope "test"]
                   [pandeiro/boot-http            "0.8.3"      :scope "test"]
                   [weasel                        "0.7.0"      :scope "test"]
                   [http-kit "2.3.0"]
                   [binaryage/devtools           "0.9.10"        :scope "test"]
                   [day8.re-frame/re-frame-10x   "0.3.3-react16" :scope "test"]
                   [powerlaces/boot-cljs-devtools "0.2.0" :scope "test"]])

(require
 '[adzerk.boot-cljs      :refer [cljs]]
 '[adzerk.boot-cljs-repl :refer [cljs-repl start-repl]]
 '[adzerk.boot-reload    :refer [reload]]
 '[pandeiro.boot-http    :refer [serve]]
 '[powerlaces.boot-cljs-devtools :refer [cljs-devtools]])

(task-options! serve  {:port 3001 :dir "resources"}
               reload {:on-jsload 're-demo.core/init!})

(def compiler-options-dev
  {:closure-defines {"re_frame.trace.trace_enabled_QMARK_" true}
   :preloads        '[day8.re-frame-10x.preload]
   :main "re-demo.core"})

(deftask dev [r nrepl-port VALUE int "nrepl-port"]
         (comp (serve)
               (watch)
               (cljs-devtools)
               (cljs-repl :nrepl-opts {:port nrepl-port})
               (reload)
               (cljs :source-map true
                     :optimizations :none
                     :compiler-options compiler-options-dev)
               (target :dir #{"resources"})))
