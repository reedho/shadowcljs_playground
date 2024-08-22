(ns my.demo1
  (:require
   ["uid" :as uid]
   [missionary.core :as m]
   [cljs-bean.core :refer [bean ->clj ->js]]
   [hyperfiddle.rcf :as rcf :refer [tests tap %]]
   ))

(rcf/enable!)
(rcf/set-timeout! 100)

#_:clj-kondo/ignore
(tests
  "equality"
  (inc 1) := 2

  "wildcards"
  {:a :b, :b [2 :b]} := {:a _, _ [2 _]}

  "unification"
  {:a :b, :b [2 :b]} := {:a ?b, ?b [2 ?b]}

  "unification on reference types"
  (def x (atom nil))
  {:a x, :b x} := {:a ?x, :b ?x}

  "multiple tests on one value"
  (def xs [:a :b :c])
  (count xs) := 3
  (last xs) := :c
  (let [xs (map identity xs)]
    (last xs) := :c
    (let [] (last xs) := :c))

  "exceptions"
  (assert false "boom") :throws js/Error

  (tests
    "nested tests (is there a strong use case?)"
    1 := 1)

  (tests
    "REPL bindings work"
    (keyword "a") := :a
    (keyword "b") := :b
    (keyword "c") := :c
    *1 := :c
    *2 := :b
    *3 := :a
    *1 := :c                            ; inspecting history does not affect history

    (keyword "d") := :d
    *1 := :d
    *2 := :c
    *3 := :b
    (symbol *2) := 'c                   ; this does affect history
    (symbol *2) := 'd)


  "async tests"
  (tests
    (defn setTimeout [f ms] (js/setTimeout ms f))
    (tap 1) (setTimeout 10 (fn []
                             (tap 2) (setTimeout 200 (fn []
                                                       (tap 3)))))
    % := 1
    % := 2
    % := ::rcf/timeout)

  ;;#_
  (tests
    "missionary"
    (def !x (atom 0))
    (def dispose ((m/reactor (m/stream (m/ap (tap (inc (m/?< (m/watch !x)))))))
                  (fn [_] (prn ::done)) #(prn ::crash %)))
    ;;% := 1
    ;;(swap! !x inc)
    ;;(swap! !x inc)
    ;;% := 2
    ;;% := 3
    (dispose))

  )




(defn ^:export demo
  []
  ;; run missionary task
  (js/Promise. (m/sp (println "A")
                     (m/? (m/sleep 1000))
                     (println "B")))

  ;; inspecting with bean
  (bean (js/Date.))
  (bean (js/Array. 10))
  (bean #js {:a 10 :b 20})
  (bean #js {:a 1 :b {:x #js []}})
  (->clj {:a 1 :b {:x #js []}})
  (->js {:a 1 :b {:x #js []}})
  (->clj (->js {:a 1 :b {:x #js []}}))

  )



(defn ^:export hello
  []
  (let [x (uid/uid)]
    (println x))
  )
