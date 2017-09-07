(ns example.core
  (:require [rum.core :as rum]
            [reagent.core :as r]
            [om.dom :as dom]
            [om.next :as om :refer [defui]]
            [goog.dom :as gdom]
            [cljss.core :refer [defstyles defkeyframes]]
            [cljss.reagent :as rss]
            [cljss.rum :as rumss]
            [cljss.om :as omss]))

(defkeyframes bounce [bounce-height]
  {[:from 20 53 80 :to] {:transform "translate3d(0,0,0)"}
   [40 43]              {:transform (str "translate3d(0,-" bounce-height "px,0)")}
   70                   {:transform (str "translate3d(0,-" (/ bounce-height 2) "px,0)")}
   90                   {:transform (str "translate3d(0,-" (/ bounce-height 4) "px,0)")}})

(defstyles wrapper [v-padding]
  {:padding-top v-padding
   :padding-bottom v-padding
   :text-align "center"
   :font "normal 18px sans-serif"})

(def color (r/atom "#856dcf"))

;;; Rum
(rumss/defstyled RumH1 :h1
  {:font-size "48px"
   :color :color
   :margin-top :v-margin
   :margin-bottom :v-margin})

(rum/defc RumTitle < rum/reactive []
  (let [[anim cls] (bounce 30)]
    [:div {:class (str (wrapper "8px") " " cls)
           :css {:border-bottom (str "1px solid " @color)
                 :animation (str anim " 1s ease infinite")}}
     (RumH1 {:v-margin "8px"
             :color (rum/react color)}
            "Clojure Style Sheets for Rum")]))

(rum/mount (RumTitle) (gdom/getElement "rum-app"))


;; Reagent
(rss/defstyled ReagentH1 :h1
  {:font-size "48px"
   :color :color
   :margin-top :v-margin
   :margin-bottom :v-margin})

(defn ReagentTitle []
  [:div {:class (wrapper "8px")}
   [ReagentH1 {:v-margin "8px" :color @color}
    "Clojure Style Sheets for Reagent"]])

(r/render [ReagentTitle] (gdom/getElement "reagent-app"))


;; Om
(omss/defstyled OmH1 :h1
  {:font-size "48px"
   :color :color
   :margin-top :v-margin
   :margin-bottom :v-margin})

(defui OmTitle
  Object
  (render [this]
    (dom/div #js {:className (wrapper "8px")}
      (OmH1 {:v-margin "8px"
             :color @color}
            "Clojure Style Sheets for Om"))))

(om/add-root! (om/reconciler {:state color}) OmTitle (gdom/getElement "om-app"))



(rss/defstyled InputField :div
  {:display "flex"
   :flex-direction "column"
   :justify-content "center"
   :align-items "center"})

(rss/defstyled InputLabel :label
  {:font "normal 14px sans-serif"})

(rss/defstyled Input :input
  {:border-radius "2px"
   :border "1px solid #ccc"
   :padding "4px 8px"})

(defn App []
  [InputField
   [InputLabel "text color"]
   [Input {:onChange #(reset! color (.. % -target -value))}]])

(r/render [App] (gdom/getElement "app"))