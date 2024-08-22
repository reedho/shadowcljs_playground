(ns my.app
  (:require
   [emmy.env :as e]
   [emmy.sci]
   [sci.core :as sci]
   [clojure.core.protocols :refer [Datafiable]]
   [portal.web :as p]
   )
  )

(def portal (p/open))

(add-tap #'p/submit)

(defn js->clj+
  "For cases when built-in js->clj doesn't work. Source: https://stackoverflow.com/a/32583549/4839573"
  [x]
  (into {} (for [k (js-keys x)] [(keyword k) (aget x k)])))

(extend-protocol Datafiable
  js/KeyboardEvent
  (datafy [this] (js->clj+ this)))

(comment
  portal
  )


(defn ^:export hello []
  (println "Hello")
  (println (e/->infix (e/square (e/sin (e/+ 'a 3)))))
  (println (e/->TeX (e/square (e/sin (e/+ 'a 3)))))
  (println (sci/eval-string "(inc 1)"))
  )
