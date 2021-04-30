Crear cuenta de IBM Cloud
Crear cluster de K8s

Install IBM Cloud CLI
Install IBM Cloud CLI k8s plugins
ibmcloud login -a cloud.ibm.com -r us-south -g default --sso

ibmcloud cr login
ibmcloud cr namespace-list
ibmcloud cr namespace-add demoassetez



$ ibmcloud cr namespace-add demoassetez
Your IBM Cloud account is not enabled for IBM Cloud Container Registry IAM policy enforcement in this region. To enable policy enforcement and to use IAM policies and resource groups, you must run the 'ibmcloud cr iam-policies-enable' command. For more information, see https://cloud.ibm.com/docs/Registry?topic=Registry-user#existing_users.

Adding namespace 'demoassetez' in resource group 'default' for account Edgar Zamora's Account in registry us.icr.io...

Successfully added namespace 'demoassetez'

OK



(base) Edgars-MacBook-Pro:workshop ezamora$ ibmcloud cr namespace-list
Listing namespaces for account 'Edgar Zamora's Account' in registry 'us.icr.io'...

Namespace   
demoassetez   
helloworldez   

OK



ibmcloud ks cluster config --cluster c2496fgd0ijjt6knhlg0
kubectl config current-context

mvn clean package


$ mvn clean package
[INFO] Scanning for projects...
[INFO] 
[INFO] -----------------------< k8sworkshop:demoasset >------------------------
[INFO] Building demoasset 1.0-SNAPSHOT
[INFO] --------------------------------[ war ]---------------------------------
[INFO] 
[INFO] --- maven-clean-plugin:2.5:clean (default-clean) @ demoasset ---
[INFO] 
[INFO] --- maven-resources-plugin:2.6:resources (default-resources) @ demoasset ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] skip non existing resourceDirectory /Users/ezamora/Documents/teckubernetes/workshop/be/src/main/resources
[INFO] 
[INFO] --- maven-compiler-plugin:3.1:compile (default-compile) @ demoasset ---
[INFO] Changes detected - recompiling the module!
[INFO] Compiling 4 source files to /Users/ezamora/Documents/teckubernetes/workshop/be/target/classes
[INFO] 
[INFO] --- maven-resources-plugin:2.6:testResources (default-testResources) @ demoasset ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] skip non existing resourceDirectory /Users/ezamora/Documents/teckubernetes/workshop/be/src/test/resources
[INFO] 
[INFO] --- maven-compiler-plugin:3.1:testCompile (default-testCompile) @ demoasset ---
[INFO] No sources to compile
[INFO] 
[INFO] --- maven-surefire-plugin:2.22.2:test (default-test) @ demoasset ---
[INFO] No tests to run.
[INFO] 
[INFO] --- maven-war-plugin:3.2.3:war (default-war) @ demoasset ---
[INFO] Packaging webapp
[INFO] Assembling webapp [demoasset] in [/Users/ezamora/Documents/teckubernetes/workshop/be/target/demoasset]
[INFO] Processing war project
[INFO] Copying webapp resources [/Users/ezamora/Documents/teckubernetes/workshop/be/src/main/webapp]
[INFO] Webapp assembled in [52 msecs]
[INFO] Building war: /Users/ezamora/Documents/teckubernetes/workshop/be/target/demoasset.war
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  2.918 s
[INFO] Finished at: 2021-04-27T17:24:18-06:00
[INFO] ------------------------------------------------------------------------
(base) Edgars-MacBook-Pro:be ezamora$ docker build -t demoasset .
[+] Building 2.1s (8/8) FINISHED                                                                                                                      
 => [internal] load build definition from Dockerfile                                                                                             0.0s
 => => transferring dockerfile: 254B                                                                                                             0.0s
 => [internal] load .dockerignore                                                                                                                0.0s
 => => transferring context: 2B                                                                                                                  0.0s
 => [internal] load metadata for docker.io/openliberty/open-liberty:kernel-java8-openj9-ubi                                                      1.8s
 => CACHED [1/3] FROM docker.io/openliberty/open-liberty:kernel-java8-openj9-ubi@sha256:5446158e6a252ee0c069eee8e0aa3af694e41106e30047018eb3add  0.0s
 => [internal] load build context                                                                                                                0.0s
 => => transferring context: 8.34kB                                                                                                              0.0s
 => [2/3] COPY --chown=1001:0 src/main/liberty/config/server.xml /config/                                                                        0.0s
 => [3/3] COPY --chown=1001:0 target/*.war /config/apps/                                                                                         0.0s
 => exporting to image                                                                                                                           0.1s
 => => exporting layers                                                                                                                          0.0s
 => => writing image sha256:09ad6881b17f02dc87be2fd65446c05a6f2001cb6c7445fbe6a57a506602dad9                                                     0.0s
 => => naming to docker.io/library/demoasset                                                                                                     0.0s

Use 'docker scan' to run Snyk tests against images to find vulnerabilities and learn how to fix them
(base) Edgars-MacBook-Pro:be ezamora$ docker image ls 
REPOSITORY          TAG       IMAGE ID       CREATED          SIZE
demoasset           latest    09ad6881b17f   24 seconds ago   729MB
my-java-test-app    latest    3eca436dba08   3 weeks ago      730MB
<none>              <none>    e94300000a85   3 weeks ago      730MB
<none>              <none>    39f7f30ba819   3 weeks ago      471MB
devacademy          latest    9859dbde580f   3 weeks ago      729MB
restapitest         latest    99d7ce9c089f   3 weeks ago      729MB
node-web-app        1.0       fe105e6127c1   7 weeks ago      921MB
node-web-app        latest    916cda6a13c0   7 weeks ago      921MB
mysql               latest    8457e9155715   2 months ago     546MB
websphere-liberty   <none>    b9f99ad122a9   2 months ago     651MB
open-liberty        latest    972892a50576   2 months ago     498MB
open-liberty        kernel    65577a88b600   6 months ago     440MB
(base) Edgars-MacBook-Pro:be ezamora$ docker tag demoasset us.icr.io/demoassetez/demoasset-be
(base) Edgars-MacBook-Pro:be ezamora$ docker image ls 
REPOSITORY                           TAG       IMAGE ID       CREATED              SIZE
demoasset                            latest    09ad6881b17f   About a minute ago   729MB
us.icr.io/demoassetez/demoasset-be   latest    09ad6881b17f   About a minute ago   729MB
my-java-test-app                     latest    3eca436dba08   3 weeks ago          730MB
<none>                               <none>    e94300000a85   3 weeks ago          730MB
<none>                               <none>    39f7f30ba819   3 weeks ago          471MB
devacademy                           latest    9859dbde580f   3 weeks ago          729MB
restapitest                          latest    99d7ce9c089f   3 weeks ago          729MB
node-web-app                         1.0       fe105e6127c1   7 weeks ago          921MB
node-web-app                         latest    916cda6a13c0   7 weeks ago          921MB
mysql                                latest    8457e9155715   2 months ago         546MB
websphere-liberty                    <none>    b9f99ad122a9   2 months ago         651MB
open-liberty                         latest    972892a50576   2 months ago         498MB
open-liberty                         kernel    65577a88b600   6 months ago         440MB
(base) Edgars-MacBook-Pro:be ezamora$ docker push  us.icr.io/demoassetez/demoasset-be

Using default tag: latest
The push refers to repository [us.icr.io/demoassetez/demoasset-be]
06688969710d: Pushed 
c08d298b6675: Pushed 
4f04e7098d96: Pushed 
248016390e0a: Pushed 
0fa7eb58a57c: Pushed 
b5489882eed9: Pushed 
2fb5caadbbb0: Pushed 
d06182ac791b: Pushed 
b39b0291530b: Pushed 
a04c77af4b60: Pushed 
479c44e860ff: Pushed 
fc905c23b8a3: Pushed 
161ec220381b: Pushed 
b7b591e3443f: Pushed 
ccf04fbd6e19: Pushed 
latest: digest: sha256:04460b0d9ef82bb9175553b1244123491e161adb60475113c90daf0fd3f198b8 size: 3459


(base) Edgars-MacBook-Pro:be ezamora$ ibmcloud cr image-list
Listing images...

Repository                           Tag      Digest         Namespace      Created          Size     Security status   
us.icr.io/demoassetez/demoasset-be   latest   04460b0d9ef8   demoassetez    17 minutes ago   388 MB   Scanning... 

kubectl get pods -A

kubectl apply -f deployment.yaml

