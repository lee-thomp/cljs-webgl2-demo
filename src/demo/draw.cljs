(ns demo.draw
  (:require [demo.gl-constants :as glc]
            [demo.matrix-4x4 :as m4]
            [shadow.resource :as rc]))

;;; Below I owe to webgl2fundamentals.org
(def vertices
  (js/Float32Array.
   [;; left column front
    0,   0,  0,
    0, 150,  0,
    30,   0,  0,
    0, 150,  0,
    30, 150,  0,
    30,   0,  0,

    ;; top rung front
    30,   0,  0,
    30,  30,  0,
    100,   0,  0,
    30,  30,  0,
    100,  30,  0,
    100,   0,  0,

    ;; middle rung front
    30,  60,  0,
    30,  90,  0,
    67,  60,  0,
    30,  90,  0,
    67,  90,  0,
    67,  60,  0,

    ;; left column back
    0,   0,  30,
    30,   0,  30,
    0, 150,  30,
    0, 150,  30,
    30,   0,  30,
    30, 150,  30,

    ;; top rung back
    30,   0,  30,
    100,   0,  30,
    30,  30,  30,
    30,  30,  30,
    100,   0,  30,
    100,  30,  30,

    ;; middle rung back
    30,  60,  30,
    67,  60,  30,
    30,  90,  30,
    30,  90,  30,
    67,  60,  30,
    67,  90,  30,

    ;; top
    0,   0,   0,
    100,   0,   0,
    100,   0,  30,
    0,   0,   0,
    100,   0,  30,
    0,   0,  30,

    ;; top rung right
    100,   0,   0,
    100,  30,   0,
    100,  30,  30,
    100,   0,   0,
    100,  30,  30,
    100,   0,  30,

    ;; under top rung
    30,   30,   0,
    30,   30,  30,
    100,  30,  30,
    30,   30,   0,
    100,  30,  30,
    100,  30,   0,

    ;; between top rung and middle
    30,   30,   0,
    30,   60,  30,
    30,   30,  30,
    30,   30,   0,
    30,   60,   0,
    30,   60,  30,

    ;; top of middle rung
    30,   60,   0,
    67,   60,  30,
    30,   60,  30,
    30,   60,   0,
    67,   60,   0,
    67,   60,  30,

    ;; right of middle rung
    67,   60,   0,
    67,   90,  30,
    67,   60,  30,
    67,   60,   0,
    67,   90,   0,
    67,   90,  30,

    ;; bottom of middle rung.
    30,   90,   0,
    30,   90,  30,
    67,   90,  30,
    30,   90,   0,
    67,   90,  30,
    67,   90,   0,

    ;; right of bottom
    30,   90,   0,
    30,  150,  30,
    30,   90,  30,
    30,   90,   0,
    30,  150,   0,
    30,  150,  30,

    ;; bottom
    0,   150,   0,
    0,   150,  30,
    30,  150,  30,
    0,   150,   0,
    30,  150,  30,
    30,  150,   0,

    ;; left side
    0,   0,   0,
    0,   0,  30,
    0, 150,  30,
    0,   0,   0,
    0, 150,  30,
    0, 150,   0,
    ]))


(def colors
  (js/Uint8Array.
   [;; left column front
    200,  70, 120,
    200,  70, 120,
    200,  70, 120,
    200,  70, 120,
    200,  70, 120,
    200,  70, 120,

    ;; top rung front
    200,  70, 120,
    200,  70, 120,
    200,  70, 120,
    200,  70, 120,
    200,  70, 120,
    200,  70, 120,

    ;; middle rung front
    200,  70, 120,
    200,  70, 120,
    200,  70, 120,
    200,  70, 120,
    200,  70, 120,
    200,  70, 120,

    ;; left column back
    80, 70, 200,
    80, 70, 200,
    80, 70, 200,
    80, 70, 200,
    80, 70, 200,
    80, 70, 200,

    ;; top rung back
    80, 70, 200,
    80, 70, 200,
    80, 70, 200,
    80, 70, 200,
    80, 70, 200,
    80, 70, 200,

    ;; middle rung back
    80, 70, 200,
    80, 70, 200,
    80, 70, 200,
    80, 70, 200,
    80, 70, 200,
    80, 70, 200,

    ;; top
    70, 200, 210,
    70, 200, 210,
    70, 200, 210,
    70, 200, 210,
    70, 200, 210,
    70, 200, 210,

    ;; top rung right
    200, 200, 70,
    200, 200, 70,
    200, 200, 70,
    200, 200, 70,
    200, 200, 70,
    200, 200, 70,

    ;; under top rung
    210, 100, 70,
    210, 100, 70,
    210, 100, 70,
    210, 100, 70,
    210, 100, 70,
    210, 100, 70,

    ;; between top rung and middle
    210, 160, 70,
    210, 160, 70,
    210, 160, 70,
    210, 160, 70,
    210, 160, 70,
    210, 160, 70,

    ;; top of middle rung
    70, 180, 210,
    70, 180, 210,
    70, 180, 210,
    70, 180, 210,
    70, 180, 210,
    70, 180, 210,

    ;; right of middle rung
    100, 70, 210,
    100, 70, 210,
    100, 70, 210,
    100, 70, 210,
    100, 70, 210,
    100, 70, 210,

    ;; bottom of middle rung.
    76, 210, 100,
    76, 210, 100,
    76, 210, 100,
    76, 210, 100,
    76, 210, 100,
    76, 210, 100,

    ;; right of bottom
    140, 210, 80,
    140, 210, 80,
    140, 210, 80,
    140, 210, 80,
    140, 210, 80,
    140, 210, 80,

    ;; bottom
    90, 130, 110,
    90, 130, 110,
    90, 130, 110,
    90, 130, 110,
    90, 130, 110,
    90, 130, 110,

    ;; left side
    160, 160, 220,
    160, 160, 220,
    160, 160, 220,
    160, 160, 220,
    160, 160, 220,
    160, 160, 220,
    ]))

(defn create-shader [ctx type source]
  (let [shader (.createShader ctx type)]
    (.shaderSource ctx shader source)
    (.compileShader ctx shader)
    (if (.getShaderParameter ctx shader glc/compile-status)
      shader
      (do
        (js/console.error "Shader compile error: "
                          (.getShaderInfoLog ctx shader))
        (.deleteShader ctx shader)
        nil))))

(defn create-program [ctx vertex-shader fragment-shader]
  (let [program (.createProgram ctx)]
    (doto ctx
      (.attachShader program vertex-shader)
      (.attachShader program fragment-shader)
      (.linkProgram program))
    (if (.getProgramParameter ctx program glc/link-status)
      program
      (do
        (js/console.error "Shader link error: "
                          (.getProgramInfoLog ctx program))
        (.deleteProgram ctx program)
        nil))))

(defn resize-canvas-dwim [canvas]
  (let [{:js/keys [clientWidth clientHeight width height]} canvas
        needs-resize? (or (not= clientWidth width)
                          (not= clientHeight height))]
    (when needs-resize?
      (set! (.-width canvas) clientWidth)
      (set! (.-height canvas) clientHeight))
    needs-resize?))

(defn init-gl [canvas]
  (let [ctx (.getContext canvas "webgl2")
        vertex-shader (create-shader ctx glc/vertex-shader
                                     (rc/inline "shaders/cube.vert"))
        fragment-shader (create-shader ctx glc/fragment-shader
                                       (rc/inline "shaders/cube.frag"))
        program (create-program ctx vertex-shader fragment-shader)
        position-attr-location (.getAttribLocation ctx program "a_position")
        color-attr-location (.getAttribLocation ctx program "a_color")
        position-buffer (.createBuffer ctx)
        color-buffer (.createBuffer ctx)
        vao (.createVertexArray ctx)]
    (.bindVertexArray ctx vao)
    (let [size 3
          type glc/float
          normalise false
          stride 0
          offset 0]
      (doto ctx
        (.bindBuffer glc/array-buffer position-buffer)
        (.enableVertexAttribArray position-attr-location)
        (.bufferData glc/array-buffer
                     vertices
                     glc/static-draw)
        (.vertexAttribPointer position-attr-location
                              size
                              type
                              normalise
                              stride
                              offset)))
    (let [size 3
          type glc/unsigned-byte
          normalise true
          stride 0
          offset 0]
      (doto ctx
        (.bindBuffer glc/array-buffer color-buffer)
        (.bufferData glc/array-buffer
                     colors
                     glc/static-draw)
        (.enableVertexAttribArray color-attr-location)
        (.vertexAttribPointer color-attr-location
                              size
                              type
                              normalise
                              stride
                              offset)))

    {:ctx ctx :program program :vao vao}))

(defn draw [gl-env trs]
  (let [{:keys [ctx program vao]} gl-env
        {:keys [tx ty tz rx ry rz scale]} trs
        matrix-location (.getUniformLocation ctx program "u_matrix")
        client-width (-> ctx .-canvas .-clientWidth)
        client-height (-> ctx .-canvas .-clientHeight)
        aspect (/ client-width client-height)
        z-near 1
        z-far 2000
        fov (/ js/Math.PI 3.0)          ; 60° FoV
        matrix (-> (m4/perspective fov aspect z-near z-far) ; Perspective
                   ;; Negative x,y here is preference so controls match movement
                   (m4/translate (- tx) (- ty) tz)
                   (m4/x-rotate rx)
                   ;; Rotate y by a half-turn so it's the /obvious/ way round on
                   ;; page load
                   (m4/y-rotate (+ js/Math.PI ry))
                   (m4/z-rotate rz)
                   (m4/scale scale scale scale))]
    (resize-canvas-dwim (.-canvas ctx))
    (doto ctx
      (.viewport 0 0 client-width client-height)
      (.clearColor 0 0 0 0)
      (.clear (bit-or glc/color-buffer-bit
                      glc/depth-buffer-bit))
      (.enable glc/cull-face)
      (.enable glc/depth-test)
      (.useProgram program)
      (.bindVertexArray vao)
      (.uniformMatrix4fv matrix-location false matrix)
      (.drawArrays glc/triangles 0 (* 16 6)))))

(defn start-animation-loop [canvas-id trs spin-rate]
  (let [canvas (js/document.getElementById canvas-id)
        gl-env (init-gl canvas)
        frame-id (volatile! nil)
        stopped? (volatile! false)
        spin-y (volatile! 0)]
    (letfn [(loop [timestamp]
              (when-not @stopped?
                ;; Gently rotate
                (vswap! spin-y + @spin-rate)
                (draw gl-env (update @trs :ry + @spin-y))
                (vreset! frame-id (js/requestAnimationFrame loop))))]
      (vreset! frame-id (js/requestAnimationFrame loop))
      ;; Return a stop callback so the caller can clean up when it needs to
      (fn stop-animation-loop []
        (vreset! stopped? true)
        (when-let [id @frame-id]
          (js/cancelAnimationFrame id)
          (vreset! frame-id nil))))))
