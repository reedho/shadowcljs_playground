# shadowcljs_playground

## Development Istruction

```bash
#// Use this to prep clj(s) dependencies:
clojure -X:deps tree :aliases [:dev]

#// Use bun to install and manage npm (js) dependencies:
bun install
bun add ...

#// open the project directory with emacs then
#// M-x cider-jack-in-cljs RET shadow RET :app RET

#// Wait for some time for the build to finish, after that
#// we must open our browser to http://localhost:8000.

#// Now, the repl is connected, we can playing around as usual.

```


Emmy Viewers with Clerk

```
clojure -Sdeps '{:deps {org.mentat/emmy-viewers {:git/url "https://github.com/mentat-collective/emmy-viewers.git" :git/sha "5afd6de120391c668e8573204321ebe4442746dc"}}}' -Tnew create :template emmy-viewers/clerk :name id.datafy/emmy-playground



clojure -Sdeps '{:deps {io.github.mentat-collective/emmy-viewers {:git/sha "0a33ca99dc6d189b07bea50c484f2a3eaf2747b0"}}}' \
-Tnew create \
:template emmy-viewers/clerk \
:name myusername/my-emmy-project




```
