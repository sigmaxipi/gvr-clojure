# MinGvrApp

This is a minimal Clojure + Android + GVR application.

## Background

* Clojure is a Lisp that targets the JVM: http://clojure.org/
* Brave Clojure has a good crash course: http://www.braveclojure.com/do-things/
  * http://clojure.org/api/cheatsheet is a good reference for syntax.
  * Lein-droid is a build system for creating Android apps in Clojure: https://github.com/clojure-android/lein-droid/
* GVR is the Cardboard & Daydream framework: https://developers.google.com/vr/android/get-started

## Steps to write a minimal Cardboard app in Clojure

1. Make sure you can create a new lein-droid project using the steps at https://github.com/clojure-android/lein-droid/wiki/Tutorial
1. Run `lein new droid mingvrapp my.company.app :activity MinGvrActivity :target-sdk 25 :app-name MinGvrApp` to create the new GVR project using a standard template. Running `lein droid doall` at this point will just install and run that template.
1. Add GVR to the dependencies by editing **project.clj** and modifying the `:dependencies` lines to:

  ```
  :repositories [["googlevr" {:url "http://google.bintray.com/googlevr/"}]]
  :dependencies [[org.clojure-android/clojure "1.7.0-r4"]
                 [neko/neko "4.0.0-alpha5"]
                 [com.google.vr/sdk-base "1.0.3" :extension "aar"]]
  ```
This will add the google.bintray.com/googlevr repo and download the sdk-base library from it.

1. Edit your **src/clojure/my/company/app/main.clj** as desired. **src/clojure/me/sxp/gvrclojure/main.clj** in this repo shows a basic example that renders a different color for each eye in a Cardboard app. Using the Cardboard Trigger input will toggle monoscopic rendering mode.
1. Run `lein droid doall` as usual to build, install, and run this new app.
1. Use a [REPL](https://en.wikipedia.org/wiki/Read%E2%80%93eval%E2%80%93print_loop) to change the app while it's running. Run `lein droid repl` to launch a Clojure REPL and connect to the app via ADB. Then type the following commands into the prompt and note how the left eye's color changes.

  ```
  REPL-y 0.3.7, nREPL 0.2.10
  Clojure 1.7.0
  Dalvik 0.9
          Exit: Control+D or (exit) or (quit)
      Commands: (user/help)
          Docs: (doc function-name-here)
                (find-doc "part-of-name-here")
        Source: (source function-name-here)

  user=> (in-ns 'my.company.app.main)
  #object[clojure.lang.Namespace 0x2f87340 "my.company.app.main"]

  my.company.app.main=> (def my.company.app.main/leftColor [1 1 1 1])
  #'my.company.app.main/leftColor
  ```

