(defproject example "0.1.0-SNAPSHOT"
            :description "FIXME: write this!"
            :url "http://example.com/FIXME"
            :license {:name "Eclipse Public License"
                      :url  "http://www.eclipse.org/legal/epl-v10.html"}

            :min-lein-version "2.7.1"

            :dependencies [[org.clojure/clojure "1.9.0"]
                           [org.clojure/clojurescript "1.10.238"]
                           [rum "0.11.2"]
                           [devcards "0.2.4"]]

            :plugins [[lein-figwheel "0.5.13"]
                      [lein-cljsbuild "1.1.7" :exclusions [[org.clojure/clojure]]]]

            :source-paths ["src" "../src"]

            :cljsbuild {:builds
                        [{:id           "dev"
                          :source-paths ["src" "../src"]
                          :figwheel     {:on-jsload example.core/mount
                                         :devcards  true}
                          :compiler     {:main                 example.core
                                         :asset-path           "js/compiled/out"
                                         :output-to            "resources/public/js/compiled/example.js"
                                         :output-dir           "resources/public/js/compiled/out"
                                         :source-map-timestamp true
                                         :preloads             [devtools.preload]}}

                         {:id           "min"
                          :source-paths ["src" "../src"]
                          :compiler     {:output-to       "resources/public/js/compiled/example.js"
                                         :main            example.core
                                         :optimizations   :advanced
                                         :closure-defines {"goog.DEBUG" false}
                                         :verbose         true
                                         :pretty-print    false}}

                         {:id           "bench"
                          :source-paths ["src" "../src"]
                          :compiler     {:main            example.bench
                                         :output-dir      "resources/public/js/compiled-bench/out"
                                         :output-to       "resources/public/js/compiled-bench/benchmark.js"
                                         :optimizations   :advanced
                                         :closure-defines {"goog.DEBUG" false}
                                         :install-deps    true}}]}

            :figwheel {:server-port 3450}

            :profiles {:dev {:dependencies  [[binaryage/devtools "0.9.2"]
                                             [figwheel-sidecar "0.5.13"]
                                             [com.cemerick/piggieback "0.2.2"]]
                             :source-paths  ["src" "../src"]
                             :repl-options  {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}
                             :clean-targets ^{:protect false} ["resources/public/js/compiled"
                                                               :target-path]}})
