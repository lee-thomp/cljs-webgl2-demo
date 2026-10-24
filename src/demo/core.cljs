(ns demo.core
  (:require [reagent.dom.client :as rd]
            [reagent.core :as r]
            [demo.draw :as d]))

(defonce trs
  (r/atom {:tx 0 :ty -100 :tz -600
           :rx -0.3 :ry 0 :rz 0
           :scale 1}))

(defonce spin-rate (r/atom 0))

(defn slider [key min max]
  [:div
   [:label (str key " " (.toFixed (key @trs) 2))]
   [:input {:id key
            :type "range"
            :min min
            :max max
            :step 0.01
            :value (key @trs)
            :on-change #(swap! trs assoc key (js/parseFloat
                                              (-> % .-target .-value)))}]])

(defn demo-ui []
  (let [stop-animation-loop (volatile! nil)]
    (r/create-class
     {:component-did-mount
      #(vreset! stop-animation-loop
                ;; `start-animation-loop` returns a callback to stop itself with
                (d/start-animation-loop "gl" trs spin-rate))

      :component-will-unmount
      #(when-let [stop @stop-animation-loop]
        (stop)
        (vreset! stop-animation-loop nil))

      :reagent-render
      (fn []
        [:main
         [:h1 "CLJS WebGL2 Demo"]
         [:canvas {:id "gl"
                   :height 480
                   :width 480}]
         [:div#sliders
          (slider :tx -400 400)
          (slider :ty -400 400)
          (slider :tz -2000 0)
          (slider :rx (- js/Math.PI) js/Math.PI)
          (slider :ry (- js/Math.PI) js/Math.PI)
          (slider :rz (- js/Math.PI) js/Math.PI)
          [:p "spin-rate"]
          [:button {:on-click #(reset! spin-rate -0.005)} "←"]
          [:button {:on-click #(reset! spin-rate 0)} "✕"]
          [:button {:on-click #(reset! spin-rate 0.005)} "→"]
          (slider :scale -2 2)]
         [:p "This is a small WebGL2 demo made using "
          [:a {:href "https://clojurescript.org"} "ClojureScript"]
          " via "
          [:a {:href "https://shadow-cljs.org/"} "shadow-cljs"]
          ". The content of the demo is all really taken from "
          [:a {:href "https://webgl2fundamentals.org/"} "webgl2fundamentals.org"]
          "."]])})))

(defn main []
  (let [container (js/document.getElementById "body")
        root (rd/create-root container)]
    (rd/render root [demo-ui])))
