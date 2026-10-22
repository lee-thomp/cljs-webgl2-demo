(ns demo.gl-constants
  ;; Exclude some stuff from `cljs.core` with a matching name below —this should
  ;; be fine as long as any code using these constants uses them within some
  ;; namespace
  (:refer-clojure :exclude [float]))

(def compile-status js/WebGL2RenderingContext.COMPILE_STATUS)
(def vertex-shader  js/WebGL2RenderingContext.VERTEX_SHADER)
(def fragment-shader js/WebGL2RenderingContext.FRAGMENT_SHADER)
(def link-status js/WebGL2RenderingContext.LINK_STATUS)
(def array-buffer js/WebGL2RenderingContext.ARRAY_BUFFER)
(def static-draw js/WebGL2RenderingContext.STATIC_DRAW)
(def float js/WebGL2RenderingContext.FLOAT)
(def unsigned-byte js/WebGL2RenderingContext.UNSIGNED_BYTE)
(def color-buffer-bit js/WebGL2RenderingContext.COLOR_BUFFER_BIT)
(def depth-buffer-bit js/WebGL2RenderingContext.DEPTH_BUFFER_BIT)
(def triangles js/WebGL2RenderingContext.TRIANGLES)
(def cull-face js/WebGL2RenderingContext.CULL_FACE)
(def depth-test js/WebGL2RenderingContext.DEPTH_TEST)
