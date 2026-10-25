(ns demo.core
  (:require [reagent.dom.client :as rd]
            [reagent.core :as r]
            [demo.draw :as d]))

(defonce trs
  (r/atom {:tx 0 :ty 0 :tz -600
           ;; Slight x-axis tilt to show 3D on page load
           :rx -0.3 :ry 0 :rz 0
           :scale 1}))

(defonce spin-rate (atom 0))

(defn slider [k min max]
  (let [id (name k)]
    [:div.control-row
     [:label {:for id} (str id " " (.toFixed (k @trs) 2))]
     [:input {:id id
              :type "range"
              :min min
              :max max
              :step 0.01
              :value (k @trs)
              :on-change #(swap! trs assoc k
                                 (-> % .-target .-value js/parseFloat))}]]))

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
          [:div.control-row
           [:span.control-label "spin"]
           [:div.spin-buttons
            [:button {:type "button"
                      :aria-label "Spin left"
                      :on-click #(reset! spin-rate -0.005)} "←"]
            [:button {:type "button"
                      :aria-label "Stop spinning"
                      :on-click #(reset! spin-rate 0)} "✕"]
            [:button {:type "button"
                      :aria-label "Spin right"
                      :on-click #(reset! spin-rate 0.005)} "→"]]]
          (slider :scale 0.01 2)]
         [:p "This is a small WebGL2 demo made using "
          [:a {:href "https://clojurescript.org"} "ClojureScript"]
          " via "
          [:a {:href "https://shadow-cljs.org/"} "shadow-cljs"]
          ". The content of the demo is all really taken from "
          [:a {:href "https://webgl2fundamentals.org/"}
           "webgl2fundamentals.org"]
          ". View the source for this demo at "
          [:a {:href "https://github.com/lee-thomp/cljs-webgl2-demo"}
           "github.com/lee-thomp/cljs-webgl2-demo"]
          " or at "
          [:a {:href "https://codeberg.org/lee-thomp/cljs-webgl2-demo"}
           "codeberg.org/lee-thomp/cljs-webgl2-demo"]
          "."]])})))

(defn main []
  (let [container (js/document.getElementById "body")
        root (rd/create-root container)]
    (rd/render root [demo-ui])))
