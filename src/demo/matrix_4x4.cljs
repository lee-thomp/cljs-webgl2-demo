(ns demo.matrix-4x4
  (:require [clojure.math :as math]))

(defn multiply [a b]
  (let [[a00 a01 a02 a03
         a10 a11 a12 a13
         a20 a21 a22 a23
         a30 a31 a32 a33] a
        [b00 b01 b02 b03
         b10 b11 b12 b13
         b20 b21 b22 b23
         b30 b31 b32 b33] b]
    [(+ (* a00 b00) (* a10 b01) (* a20 b02) (* a30 b03))
     (+ (* a01 b00) (* a11 b01) (* a21 b02) (* a31 b03))
     (+ (* a02 b00) (* a12 b01) (* a22 b02) (* a32 b03))
     (+ (* a03 b00) (* a13 b01) (* a23 b02) (* a33 b03))
     (+ (* a00 b10) (* a10 b11) (* a20 b12) (* a30 b13))
     (+ (* a01 b10) (* a11 b11) (* a21 b12) (* a31 b13))
     (+ (* a02 b10) (* a12 b11) (* a22 b12) (* a32 b13))
     (+ (* a03 b10) (* a13 b11) (* a23 b12) (* a33 b13))
     (+ (* a00 b20) (* a10 b21) (* a20 b22) (* a30 b23))
     (+ (* a01 b20) (* a11 b21) (* a21 b22) (* a31 b23))
     (+ (* a02 b20) (* a12 b21) (* a22 b22) (* a32 b23))
     (+ (* a03 b20) (* a13 b21) (* a23 b22) (* a33 b23))
     (+ (* a00 b30) (* a10 b31) (* a20 b32) (* a30 b33))
     (+ (* a01 b30) (* a11 b31) (* a21 b32) (* a31 b33))
     (+ (* a02 b30) (* a12 b31) (* a22 b32) (* a32 b33))
     (+ (* a03 b30) (* a13 b31) (* a23 b32) (* a33 b33))
     ]))

(defn translation [tx ty tz]
  [1 0 0 0
   0 1 0 0
   0 0 1 0
   tx ty tz 1])

(defn x-rotation [r]
  (let [c (js/Math.cos r)
        s (js/Math.sin r)]
    [1 0 0 0
     0 c s 0
     0 (- s) c 0
     0 0 0 1]))

(defn y-rotation [r]
  (let [c (js/Math.cos r)
        s (js/Math.sin r)]
    [c 0 (- s) 0
     0 1 0 0
     s 0 c 0
     0 0 0 1]))

(defn z-rotation [r]
  (let [c (js/Math.cos r)
        s (js/Math.sin r)]
    [c s 0 0
     (- s) c 0 0
     0 0 1 0
     0 0 0 1]))

(defn scaling [sx sy sz]
  [sx 0 0 0
   0 sy 0 0
   0 0 sz 0
   0 0 0 1])

(defn translate [m tx ty tz]
  (multiply m
            (translation tx ty tz)))

(defn x-rotate [m r]
  (multiply m
            (x-rotation r)))

(defn y-rotate [m r]
  (multiply m
            (y-rotation r)))

(defn z-rotate [m r]
  (multiply m
            (z-rotation r)))

(defn scale [m sx sy sz]
  (multiply m
            (scaling sx sy sz)))

(defn projection [w h d]
  [(/ 2 w) 0 0 0
   0 (/ -2 h) 0 0
   0 0 (/ 2 d) 0
   -1 1 0 1])

(defn perspective [fov aspect near far]
  (let [f (math/tan (- (/ 2.0 math/PI) (/ 2.0 fov)))
        range-inverse (/ 1.0 (- near far))]
    [(/ f aspect) 0 0 0
     0 f 0 0
     0 0 (* (+ near far) range-inverse) -1
     0 0 (* near far range-inverse 2) 0]))
