(ns degree9.env
 (:refer-clojure :exclude [get keys])
 (:require
  [cuerdas.core :as str]
  [goog.object :as obj]
  [degree9.debug :as dbg]
  [cljs.test :refer-macros [deftest is]]
  ["dotenv" :as dotenv]))

(dbg/defdebug debug "degree9:enterprise:env")

(def ^:dynamic *env* (atom nil))

(defn env-obj
  "Initialize dotenv and return the process.env object."
  [& conf]
  (debug "Initializing env object with config %s" conf)
  (when-not @*env*
   (reset! *env* (.config dotenv conf)))
  (obj/get js/process "env"))

(defn keys
  "Return all keys from the process.env object."
  []
  (->> (env-obj)
    (js-keys)
    (js->clj)
    (map str/kebab)
    (map keyword)))

(defn get
  "Return the process.env object value for `key` or `default`."
  ([key] (get key nil))
  ([key default] (obj/get (env-obj) (-> key name str/snake str/upper) default)))
