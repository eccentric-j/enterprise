(ns degree9.hellosign.embedded
  (:require ["hellosign-embedded" :as hello]))

(defn hellosign [opts]
  (hello. (clj->js opts)))
