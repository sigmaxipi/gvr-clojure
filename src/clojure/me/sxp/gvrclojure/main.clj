(ns me.sxp.gvrclojure.main
    ; Standard Clojure imports
    (:require [neko.activity :refer [defactivity set-content-view!]]
              [neko.debug :refer [*a]]
              [neko.notify :refer [toast]]
              [neko.resource :as res]
              [neko.find-view :refer [find-view]]
              [neko.threading :refer [on-ui]])
    ; GVR related imports
    (:import
      android.opengl.GLES20
      com.google.vr.sdk.base.Eye$Type))


; Set colors for later use. Each is a vector of size 4.
(def leftColor  [1 0 0 1])
(def rightColor [0 1 1 1])
(def monoColor  [0.5 0.5 0.5 1])


; Utility function to set the color of the eyeport via a Java GL call.
(defn setEyeColor [r g b a] (GLES20/glClearColor r g b a))


; Create a concrete implementation of the GvrView$StereoRenderer class for the render loop.
(def renderer
  (reify com.google.vr.sdk.base.GvrView$StereoRenderer
    (onDrawEye [this eye]
      ; Define onDrawEye to glClear(color) based on the eye type.
      (let [type (.getType eye)]
        (cond
          (= type Eye$Type/LEFT)  (apply setEyeColor leftColor)
          (= type Eye$Type/RIGHT) (apply setEyeColor rightColor)
          :else                   (apply setEyeColor monoColor)))
      (GLES20/glClear (bit-or GLES20/GL_COLOR_BUFFER_BIT GLES20/GL_DEPTH_BUFFER_BIT))
    )


    ; Unused methods.
    (onNewFrame [this headTransform])
    (onFinishFrame [this viewport])
    (onRendererShutdown [this])
    (onSurfaceChanged [this width height])
    (onSurfaceCreated [this config])))


; Define Android Activity using leindroid macro
(defactivity me.sxp.gvrclojure.MinGvrActivity
  :key :main
  :extends com.google.vr.sdk.base.GvrActivity


  ; Minimal setup code.
  (onCreate [this bundle]
    (.superOnCreate this bundle)
    (on-ui
      (def gvrView (new com.google.vr.sdk.base.GvrView (*a)))
      (.setRenderer gvrView renderer)
      (set-content-view! (*a) gvrView)))


  ; Toggle mono/stereo rendering on touch events.
  (onCardboardTrigger [this]
    (.setStereoModeEnabled gvrView (not (.getStereoModeEnabled gvrView)))))
