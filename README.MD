# Kubernetes workshop

This repo contains two different examples of moving workloads to a K8s cluster in Minikube


## Prerequisites

### Install Git on your machine (Optional, since you can download this public repo files as a zip)
 
1. Download the latest installer from [https://git-scm.com/downloads](https://git-scm.com/downloads)
2. Run the installer and follow the instructions
3. Open a terminal and run: `git --version ` to get the next output: 
```    
$ git version
git version 2.27.0
```
4. Do some basic configuration (The assumption here is that you will be using IBM GitHub Enterprise, for public GitHub you may want to change this)
```
    $ git config --global user.name "<your-name>"
    $ git config --global user.email "<your-email>"
```

For Windows an additional set of tools are needed, GitBash among them. [https://gitforwindows.org/](https://gitforwindows.org/). [This video](https://learning.oreilly.com/videos/learn-git-in/9781789348231/9781789348231-video1_3) is also an useful guide for installation of Git on different OS. 

### Open account in GitHub (Optional, since you can download this public repo files as a zip)

1. Open the browser to https://github.com
2. Create your account with user and password

### Configure Personal Token in GitHub (Optional, and depends if you have/created an GitHub account)

1. Go to https://github.com 
2. Click on your avatar and click on the **settings** option
3. Click on **Developer Settings** 
4. Click on **Personal access tokens**
5. Click on **Generate new token**
6. Add a description in the **Note** field and select the scope by checking **repo**
7. Click on the **Generate token** button
8. Save the generated token

Note: Tokens can be revoked and regenerated. 

### Maven

1. Install [maven](https://maven.apache.org/)
2. If you use Eclipse, it already comes with a embeeded mvn
3. For VSCode you can install the extensions (This is my setup)

### Install Docker on your machine

Install the Docker Community Edition (CE). For Mac and Windows the installation is packaged as *Docker Desktop*. For Linux, the native platform of Docker, you will need to run some commands. Please update the version if you have it installed from before, at the moment of writting this guide 19.x was the latest stable. 

1. Go to [https://www.docker.com/products/docker-desktop](https://www.docker.com/products/docker-desktop).

2. For Linux, select the "View Linux engine" option or go directly to [here](https://hub.docker.com/search?q=&type=edition&offering=community&operating_system=linux). Docker installation process vary according to the distribution, therefore you have to select the option that matches your current OS distro. Some options are:
- [Ubuntu](https://docs.docker.com/engine/install/ubuntu/)
- [CentOS (covers RedHat)](https://hub.docker.com/editions/community/docker-ce-server-centos)

2. For Windows and Mac, download the *stable* version of the installer (edge version works, but let's be consistent), and run the installer (dmg for Mac, exe for Windows)
- [Mac](https://hub.docker.com/editions/community/docker-ce-desktop-mac)
- [Windows](https://hub.docker.com/editions/community/docker-ce-desktop-windows)

3. Go to the terminal and type:
    - `docker version` to check that the installer version is running. 
    - `docker run hello-world` to verify that Docker is pulling images and running as expected.


### Install kubectl

As per the link below:
*kubectl: The Kubernetes command-line tool, kubectl, allows you to run commands against Kubernetes clusters. You can use kubectl to deploy applications, inspect and manage cluster resources, and view logs. For more information including a complete list of kubectl operations, see the kubectl reference documentation.*

1. Follow the steps listed in [https://kubernetes.io/docs/tasks/tools/](https://kubernetes.io/docs/tasks/tools/)

### Install Minikube

Minikube allows a local Kubernetes, with single node cluster running on a local VM. It requires a VM, so **take a look its prerequisites**. Latest version should be fine (just for reference I used my old 1.19.0).

1. Follow the steps at https://minikube.sigs.k8s.io/docs/start/ to install minikube
2. Run command
`minikube start`
```
🎉  minikube 1.19.0 is available! Download it: https://github.com/kubernetes/minikube/releases/tag/v1.19.0
💡  To disable this notice, run: 'minikube config set WantUpdateNotification false'

🙄  minikube v1.9.2 on Darwin 11.3
✨  Using the hyperkit driver based on existing profile
👍  Starting control plane node m01 in cluster minikube
🔄  Restarting existing hyperkit VM for "minikube" ...
🐳  Preparing Kubernetes v1.18.0 on Docker 19.03.8 ...
❗  This VM is having trouble accessing https://k8s.gcr.io
💡  To pull new external images, you may need to configure a proxy: https://minikube.sigs.k8s.io/docs/reference/networking/proxy/
🌟  Enabling addons: default-storageclass, storage-provisioner
🏄  Done! kubectl is now configured to use "minikube"
```
3. Check minikube status with command
`$ minikube status`
```
m01
host: Running
kubelet: Running
apiserver: Running
kubeconfig: Configured
```

### Getting the source code of the examples

1. If you have a github account, simply run the command below and provide yout access token:
`git clone https://github.com/ezamorad/workshopk8s.git` 
2. Otherwise download the zip in the **Code** option in [https://github.com/ezamorad/workshopk8s](https://github.com/ezamorad/workshopk8s) and uncompress it

## Running the basic example

The basic example moves a minimal Java project to a local cluster in minikube. The minimal Java project exposes an API to query an simple asset catalog in memory

### Building the project

1. Navigate to the `basics\be` folder
2. Run the command `mvn clean package`
The execution should end with success and a new file <your path>/workshop/be/target/demoasset.war


### Build the docker image and make it available for minikube cluster

1. Run the command `docker build -t demoasset-be .`
2. List the local docker images to check out new image is there
```
$ docker image ls 
REPOSITORY          TAG       IMAGE ID       CREATED          SIZE
demoasset-be        latest    09ad6881b17f   24 seconds ago   729MB
```

3. Make the new docker image available to minukube by running:
- `minikube cache add demoasset-be:latest` (or `minikube image load demoasset-be:latest --daemon`).
- `minikube ssh` (to enter the minikube vm terminal)
- `docker images` (demoasset-be image should be listed)
- `exit` (to return to your computer terminal)

### Explore the cluster

1. Get pods running in the default ***namespace*** with command `kubectl get pods`
```
NAMESPACE     NAME                               READY   STATUS             RESTARTS   AGE
default       sidecar-pod                        0/2     ImagePullBackOff   0          170d
default       sleepy                             0/1     ImagePullBackOff   0          170d
```

<!-- 2. Get pods running all namespaces
`kubectl get pods --all-namespaces`

3. Get pods running a specific container
`kubectl get pods -n kube-system`

4. List namespaces
`kubectl get namespaces` -->

### Create the application and expose it
1. Navigate to `basics/be/deployment/k8s`
2. Run the command `kubectl apply -f deployment.yaml`
3. Check the new pod is running `kubectl get pods`
```
NAMESPACE     NAME                               READY   STATUS             RESTARTS   AGE
default       demoasset-be-58955c76b9-cdr5w      1/1     Running            0          61m
default       sidecar-pod                        0/2     ImagePullBackOff   0          170d
default       sleepy                             0/1     ImagePullBackOff   0          170d
```

4. Run the command `kubectl apply -f service.yaml`
5. Check the new service is running `kubectl get service`
```
NAME           TYPE        CLUSTER-IP       EXTERNAL-IP   PORT(S)          AGE
demoasset-be   NodePort    10.102.185.223   <none>        9080:31228/TCP   103m
```

6. Get the IP to access the minikube k8s node with the command `minikube ip`
```
192.168.64.3
```

7. Get the service exposed port with `kubectl describe service demoasset-be` command. Grab the `NodePort` value
```
Name:                     demoasset-be
...
NodePort:                 <unset>  31228/TCP
...
```

8. Check the running app in the minikube ip and service exposed port `192.168.64.3:31228`. The application context is `k8sworkshop`, the application name is `api` and the exposed method is `assets`. Use a browser, or cURL or Postman.
```
$ curl http://192.168.64.3:31228/k8sworkshop/api/assets | jq
[
  {
    "details": "Portatil Lenovo",
    "id": 1,
    "name": "thinkpad",
    "owner": "OscarZ",
    "serial": "1234",
    "status": "active"
  },
  {
    "details": "Portatil Apple",
    "id": 2,
    "name": "macbook",
    "owner": "JuanR",
    "serial": "5678",
    "status": "inactive"
  },
  {
    "details": "Tableta Apple",
    "id": 3,
    "name": "ipad",
    "owner": "MariaC",
    "serial": "9123",
    "status": "active"
  }
]
```


### Explore the pod and the service

1. The name of the pods is composed of several elements, for example `demoasset-be-58955c76b9-cdr5w` has:
- `demoasset-be`: the deployment name
- `58955c76b9`: the *[replica-set](https://kubernetes.io/es/docs/concepts/workloads/controllers/replicaset/)* name. The replica set is the kubernetes object used to monitor a number of replica pods are running. It is created when the deployment is created.
- `cdr5w`: a unique identifier

2. Check the pod with the command `kubectl describe pod demoasset-be-<replica>-<unique-id>`
```
$ kubectl describe pod demoasset-be-58955c76b9-cdr5w
Name:         demoasset-be-58955c76b9-cdr5w
Namespace:    default
Priority:     0
Node:         minikube/192.168.64.3
Start Time:   Fri, 30 Apr 2021 05:13:09 -0600
Labels:       app=demoasset-be
              pod-template-hash=58955c76b9
Annotations:  <none>
Status:       Running
IP:           172.17.0.3
IPs:
  IP:           172.17.0.3
Controlled By:  ReplicaSet/demoasset-be-58955c76b9
Containers:
  demoasset-be:
    Container ID:   docker://5aa66805649c3196f4cb80bcf2eda39c875cc5b497b470cc0280382f04a9a2f3
    Image:          demoasset-be:latest
    Image ID:       docker://sha256:175a70d40381bd465083bc21996ee04e15cf1396b40f1072eec627300df638cf
    Port:           9080/TCP
    Host Port:      0/TCP
    State:          Running
      Started:      Fri, 30 Apr 2021 05:13:10 -0600
    Ready:          True
    Restart Count:  0
    Environment:    <none>
    Mounts:
      /var/run/secrets/kubernetes.io/serviceaccount from default-token-kv4z4 (ro)
Conditions:
  Type              Status
  Initialized       True 
  Ready             True 
  ContainersReady   True 
  PodScheduled      True 
Volumes:
  default-token-kv4z4:
    Type:        Secret (a volume populated by a Secret)
    SecretName:  default-token-kv4z4
    Optional:    false
QoS Class:       BestEffort
Node-Selectors:  <none>
Tolerations:     node.kubernetes.io/not-ready:NoExecute for 300s
                 node.kubernetes.io/unreachable:NoExecute for 300s
Events:          <none>
```
3. Get the yaml definition of the pod with `kubectl get pods demoasset-be-<replica>-<unique-id> -o yaml`
```
$ kubectl get pod demoasset-be-58955c76b9-cdr5w  -o yaml
apiVersion: v1
kind: Pod
metadata:
  creationTimestamp: "2021-04-30T11:13:09Z"
  generateName: demoasset-be-58955c76b9-
  labels:
    app: demoasset-be
    pod-template-hash: 58955c76b9
  managedFields:
  - apiVersion: v1
    fieldsType: FieldsV1
    fieldsV1:
      f:metadata:
        f:generateName: {}
        f:labels:
          .: {}
          f:app: {}
          f:pod-template-hash: {}
...
      f:spec:
        f:containers:
          k:{"name":"demoasset-be"}:
            .: {}
            f:image: {}
            f:imagePullPolicy: {}
...
    manager: kubelet
    operation: Update
    time: "2021-04-30T11:13:11Z"
  name: demoasset-be-58955c76b9-cdr5w
  namespace: default
  ownerReferences:
  - apiVersion: apps/v1
    blockOwnerDeletion: true
    controller: true
    kind: ReplicaSet
    name: demoasset-be-58955c76b9
    uid: 24cae409-f666-457b-a980-af2492adce9d
  resourceVersion: "2840101"
  selfLink: /api/v1/namespaces/default/pods/demoasset-be-58955c76b9-cdr5w
  uid: 270dfc90-dfce-468e-9a09-621d6210028d
spec:
  containers:
  - image: demoasset-be:latest
    imagePullPolicy: Never
    name: demoasset-be
    ports:
    - containerPort: 9080
      protocol: TCP
    resources: {}
    terminationMessagePath: /dev/termination-log
    terminationMessagePolicy: File
    volumeMounts:
    - mountPath: /var/run/secrets/kubernetes.io/serviceaccount
      name: default-token-kv4z4
      readOnly: true
...
  containerStatuses:
  - containerID: docker://5aa66805649c3196f4cb80bcf2eda39c875cc5b497b470cc0280382f04a9a2f3
    image: demoasset-be:latest
    imageID: docker://sha256:175a70d40381bd465083bc21996ee04e15cf1396b40f1072eec627300df638cf
    lastState: {}
    name: demoasset-be
    ready: true
    restartCount: 0
    started: true
    state:
      running:
        startedAt: "2021-04-30T11:13:10Z"
  hostIP: 192.168.64.3
  phase: Running
  podIP: 172.17.0.3
  podIPs:
  - ip: 172.17.0.3
  qosClass: BestEffort
  startTime: "2021-04-30T11:13:09Z"

```

4. Explore the service definition with `kubectl describe service <service-name>` 
```
kubectl describe service demoasset-be
Name:                     demoasset-be
Namespace:                default
Labels:                   <none>
Annotations:              Selector:  app=demoasset-be
Type:                     NodePort
IP:                       10.102.185.223
Port:                     <unset>  9080/TCP
TargetPort:               9080/TCP
NodePort:                 <unset>  31228/TCP
Endpoints:                172.17.0.3:9080
Session Affinity:         None
External Traffic Policy:  Cluster
Events:                   <none>
```

### Check the pod's containers logs (application logs)

1. Use the command `kubectl logs <pod-name>`
```
$ kubectl logs demoasset-be-58955c76b9-cdr5w
Launching defaultServer (Open Liberty 20.0.0.10/wlp-1.0.45.cl201020200915-1100) on Eclipse OpenJ9 VM, version 1.8.0_265-b01 (en_US)
[AUDIT   ] CWWKE0001I: The server defaultServer has been launched.
[AUDIT   ] CWWKG0093A: Processing configuration drop-ins resource: /opt/ol/wlp/usr/servers/defaultServer/configDropins/defaults/keystore.xml
[AUDIT   ] CWWKG0093A: Processing configuration drop-ins resource: /opt/ol/wlp/usr/servers/defaultServer/configDropins/defaults/open-default-port.xml
[AUDIT   ] CWWKZ0058I: Monitoring dropins for applications.
[AUDIT   ] CWWKT0016I: Web application available (default_host): http://demoasset-be-58955c76b9-cdr5w:9080/k8sworkshop/
[AUDIT   ] CWWKZ0001I: Application demoasset started in 0.662 seconds.
[AUDIT   ] CWWKF0012I: The server installed the following features: [jaxrs-2.1, jaxrsClient-2.1, jsonp-1.1, servlet-4.0].
[AUDIT   ] CWWKF0013I: The server removed the following features: [el-3.0, jsp-2.3, servlet-3.1].
[AUDIT   ] CWWKF0011I: The defaultServer server is ready to run a smarter planet. The defaultServer server started in 7.676 seconds.
```

### Execute commands in a container
```
$ kubectl exec -it demoasset-be-58955c76b9-cdr5w -c demoasset-be -- bash

bash-4.4$ ls /config/
apps  configDropins  dropins  server.env  server.xml

bash-4.4$ ls /config/apps/
demoasset.war

bash-4.4$ cat /logs/messages.log 
********************************************************************************
product = Open Liberty 20.0.0.10 (wlp-1.0.45.cl201020200915-1100)
wlp.install.dir = /opt/ol/wlp/
server.output.dir = /opt/ol/wlp/output/defaultServer/
java.home = /opt/java/openjdk/jre
java.version = 1.8.0_265
java.runtime = OpenJDK Runtime Environment (1.8.0_265-b01)
os = Linux (4.19.107; amd64) (en_US)
process = 1@172.17.0.3
********************************************************************************
[4/30/21 11:13:10:887 UTC] 00000001 com.ibm.ws.kernel.launch.internal.FrameworkManager           A CWWKE0001I: The server defaultServer has been launched.
[4/30/21 11:13:11:928 UTC] 0000001d com.ibm.ws.config.xml.internal.ServerXMLConfiguration        A CWWKG0093A: Processing configuration drop-ins resource: /opt/ol/wlp/usr/servers/defaultServer/configDropins/defaults/keystore.xml
[4/30/21 11:13:11:941 UTC] 0000001d com.ibm.ws.config.xml.internal.ServerXMLConfiguration        A CWWKG0093A: Processing configuration drop-ins resource: /opt/ol/wlp/usr/servers/defaultServer/configDropins/defaults/open-default-port.xml
[4/30/21 11:13:12:039 UTC] 00000001 com.ibm.ws.kernel.launch.internal.FrameworkManager           I CWWKE0002I: The kernel started after 1.217 seconds
[4/30/21 11:13:12:071 UTC] 00000026 com.ibm.ws.kernel.feature.internal.FeatureManager            I CWWKF0007I: Feature update started.
[4/30/21 11:13:13:046 UTC] 0000001d g.apache.cxf.cxf.core.3.2:1.0.45.cl201020200915-1100(id=96)] I Aries Blueprint packages not available. So namespaces will not be registered
[4/30/21 11:13:13:134 UTC] 0000001e com.ibm.ws.app.manager.internal.monitor.DropinMonitor        A CWWKZ0058I: Monitoring dropins for applications.
[4/30/21 11:13:13:656 UTC] 00000029 com.ibm.ws.app.manager.AppMessageHelper                      I CWWKZ0018I: Starting application demoasset.
[4/30/21 11:13:13:657 UTC] 00000029 bm.ws.app.manager.war.internal.WARDeployedAppInfoFactoryImpl I CWWKZ0136I: The demoasset application is using the archive file at the /opt/ol/wlp/usr/servers/defaultServer/apps/demoasset.war location.
[4/30/21 11:13:14:008 UTC] 00000029 com.ibm.ws.session.WASSessionCore                            I SESN8501I: The session manager did not find a persistent storage location; HttpSession objects will be stored in the local application server's memory.
[4/30/21 11:13:14:015 UTC] 00000029 com.ibm.ws.webcontainer.osgi.webapp.WebGroup                 I SRVE0169I: Loading Web Module: REST API Demo.
[4/30/21 11:13:14:017 UTC] 00000029 com.ibm.ws.webcontainer                                      I SRVE0250I: Web Module REST API Demo has been bound to default_host.
[4/30/21 11:13:14:018 UTC] 00000029 com.ibm.ws.http.internal.VirtualHostImpl                     A CWWKT0016I: Web application available (default_host): http://demoasset-be-58955c76b9-cdr5w:9080/k8sworkshop/
[4/30/21 11:13:14:134 UTC] 00000027 com.ibm.ws.session.WASSessionCore                            I SESN0176I: A new session context will be created for application key default_host/k8sworkshop
[4/30/21 11:13:14:147 UTC] 00000027 com.ibm.ws.util                                              I SESN0172I: The session manager is using the Java default SecureRandom implementation for session ID generation.
[4/30/21 11:13:14:318 UTC] 00000027 com.ibm.ws.app.manager.AppMessageHelper                      A CWWKZ0001I: Application demoasset started in 0.662 seconds.
[4/30/21 11:13:14:433 UTC] 0000002a com.ibm.ws.webcontainer.osgi.mbeans.PluginGenerator          I SRVE9103I: A configuration file for a web server plugin was automatically generated for this server at /opt/ol/wlp/output/defaultServer/logs/state/plugin-cfg.xml.
[4/30/21 11:13:18:342 UTC] 00000026 com.ibm.ws.tcpchannel.internal.TCPPort                       I CWWKO0219I: TCP Channel defaultHttpEndpoint has been started and is now listening for requests on host *  (IPv4) port 9080.
[4/30/21 11:13:18:493 UTC] 00000026 com.ibm.ws.kernel.feature.internal.FeatureManager            A CWWKF0012I: The server installed the following features: [jaxrs-2.1, jaxrsClient-2.1, jsonp-1.1, servlet-4.0].
[4/30/21 11:13:18:496 UTC] 00000026 com.ibm.ws.kernel.feature.internal.FeatureManager            A CWWKF0013I: The server removed the following features: [el-3.0, jsp-2.3, servlet-3.1].
[4/30/21 11:13:18:497 UTC] 00000026 com.ibm.ws.kernel.feature.internal.FeatureManager            I CWWKF0008I: Feature update completed in 6.457 seconds.
[4/30/21 11:13:18:497 UTC] 00000026 com.ibm.ws.kernel.feature.internal.FeatureManager            A CWWKF0011I: The defaultServer server is ready to run a smarter planet. The defaultServer server started in 7.676 seconds.
[4/30/21 11:19:46:485 UTC] 00000029 g.apache.cxf.cxf.core.3.2:1.0.45.cl201020200915-1100(id=96)] I Setting the server's publish address to be /api/
[4/30/21 11:19:46:692 UTC] 00000029 com.ibm.ws.webcontainer.servlet                              I SRVE0242I: [demoasset] [/k8sworkshop] [api.RestApplication]: Initialization successful.

bash-4.4$ exit
exit
```

### Increase replica number of the deployment

1. Change the file `basics/be/deployment/k8s/deployment.yaml`, set `replicas:` value to  *3* and save the file
2. Run the command `kubectl apply -f deployment/k8s/deployment.yaml`
```
deployment.apps/demoasset-be configured
```
3. Check the new pods are running `kubectl get pods -o wide`.
```
NAME                            READY   STATUS             RESTARTS   AGE    IP           NODE       NOMINATED NODE   READINESS GATES
demoasset-be-58955c76b9-cdr5w   1/1     Running            0          9h     172.17.0.3   minikube   <none>           <none>
demoasset-be-58955c76b9-rqvl2   1/1     Running            0          82s    172.17.0.9   minikube   <none>           <none>
demoasset-be-58955c76b9-whktl   1/1     Running            0          82s    172.17.0.8   minikube   <none>           <none>
sidecar-pod                     0/2     ImagePullBackOff   0          170d   172.17.0.6   minikube   <none>           <none>
sleepy                          0/1     ImagePullBackOff   0          170d   172.17.0.7   minikube   <none>           <none>
```
Note:
- The `-o wide` flag to bring more pods details
- The first pod created was not re-created since this was only a scale change

4. Scaling a deployment can also be done by command with `kubectl scale deployment demoasset-be --replicas=<count>`. Let's try by down-scaling to 2 pods:
```
$kubectl scale deployment demoasset-be --replicas=2
deployment.apps/demoasset-be scaled

$ kubectl get pods -o wide
NAME                            READY   STATUS             RESTARTS   AGE     IP           NODE       NOMINATED NODE   READINESS GATES
demoasset-be-58955c76b9-cdr5w   1/1     Running            0          9h      172.17.0.3   minikube   <none>           <none>
demoasset-be-58955c76b9-whktl   1/1     Running            0          5m52s   172.17.0.8   minikube   <none>           <none>
sidecar-pod                     0/2     ImagePullBackOff   0          170d    172.17.0.6   minikube   <none>           <none>
sleepy                          0/1     ImagePullBackOff   0          170d    172.17.0.7   minikube   <none>           <none>
```

### Recreating pods
Re-create pods is a normal operation in K8s, usually to solve random issues and/or load a new imahe of configuration. There are two ways
1. Delete the pod and allow the replica set recreate it to maintain the number of replicas running `kubectl delete pod`
```
$ kubectl delete pod demoasset-be-58955c76b9-whktl
pod "demoasset-be-58955c76b9-whktl" deleted

$ kubectl get pods 
NAME                            READY   STATUS             RESTARTS   AGE
demoasset-be-58955c76b9-cdr5w   1/1     Running            0          9h
demoasset-be-58955c76b9-hq7vx   1/1     Running            0          5s
...
```
2. Ask the deployment to bounce its pods with `kubectl rollout restart deployment <deployment-name>`
```
kubectl rollout restart deployment demoasset-be
deployment.apps/demoasset-be restarted

$ kubectl get pods 
NAME                            READY   STATUS              RESTARTS   AGE
demoasset-be-58955c76b9-cdr5w   1/1     Running             0          9h
demoasset-be-58955c76b9-hq7vx   1/1     Terminating         0          2m9s
demoasset-be-754c594fc4-82cnq   1/1     Running             0          3s
demoasset-be-754c594fc4-8zb8t   0/1     ContainerCreating   0          1s
...

$ kubectl get pods 
(base) Edgars-MacBook-Pro:be ezamora$ kubectl get pods 
NAME                            READY   STATUS             RESTARTS   AGE
demoasset-be-754c594fc4-82cnq   1/1     Running            0          110s
demoasset-be-754c594fc4-8zb8t   1/1     Running            0          108s
...
```

### Delete the deployment and service

1. Delete deployment using `kubectl delete deployment <deployment-name>`. All its pods will be removed also.
```
$ kubectl delete deployment demoasset-be
deployment.apps "demoasset-be" deleted
```
2. Delete service using `kubectl delete service <service-name>`. The service can not be reached any more
```
$ kubectl delete service demoasset-be
service "demoasset-be" deleted

$ kubectl get svc
NAME         TYPE        CLUSTER-IP   EXTERNAL-IP   PORT(S)   AGE
kubernetes   ClusterIP   10.96.0.1    <none>        443/TCP   280d

curl http://192.168.64.3:31228/k8sworkshop/api/assets
curl: (7) Failed to connect to 192.168.64.3 port 31228: Connection refused
```

## Running the complete example


Go to `complete` folder

### Create namespace
$ kubectl apply -f deployment/k8s/namespace.yaml 
namespace/demoasset created

### create Deployments y service for back end

### Create *Secret* for MySQL
Kubernetes uses Secret to store and manage sensitive information such as passwords, ssh keys and OAuth tokens. 

1. Create user password
<!-- Use base64 encoded to store 'MYSQL_ROOT_PASSWORD': -->


kubectl create secret generic mysqlcreds --from-literal=username=ezamora --from-literal=password=pa55word --dry-run=client -o yaml > be/deployment/k8s/secret.yaml

$ kubectl apply -f deployment/k8s/secret.yaml 
secret/mysqlcreds created


docker pull mysql:latest
minikube cache add mysql:latest

kubectl apply -f deployment/k8s/mysql-pv.yaml 
persistentvolume/mysql-pv-volume created
persistentvolumeclaim/mysql-pv-claim created

kubectl get pods
NAME                     READY   STATUS             RESTARTS   AGE
mysql-665d658fd8-dhvnn   1/1     Running            0          15s
sidecar-pod              0/2     ImagePullBackOff   0          171d
sleepy                   0/1     ImagePullBackOff   0          171d

$ kubectl exec -it mysql-665d658fd8-dhvnn  -- bash
root@mysql-665d658fd8-dhvnn:/# env
KUBERNETES_SERVICE_PORT_HTTPS=443
KUBERNETES_SERVICE_PORT=443
MYSQL_MAJOR=8.0
HOSTNAME=mysql-665d658fd8-dhvnn
PWD=/
MYSQL_ROOT_PASSWORD=pa55word
HOME=/root
KUBERNETES_PORT_443_TCP=tcp://10.96.0.1:443
MYSQL_VERSION=8.0.23-1debian10
GOSU_VERSION=1.12
TERM=xterm
SHLVL=1
KUBERNETES_PORT_443_TCP_PROTO=tcp
KUBERNETES_PORT_443_TCP_ADDR=10.96.0.1
KUBERNETES_SERVICE_HOST=10.96.0.1
KUBERNETES_PORT=tcp://10.96.0.1:443
KUBERNETES_PORT_443_TCP_PORT=443
PATH=/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin
_=/usr/bin/env

root@k8s-mysql:/# mysql --user=root --password=$MYSQL_ROOT_PASSWORD
--- Lo siguiente se borra si se reinicia minikube
mysql> create database demoasset;
mysql> use demoasset;
mysql>  create table assets(
serial_number  char(12) primary key unique,
name           char(100) not null,
description    text not null,
assignee_email char(20) ,
type           char(50) ,
data_assignment date ,
data_registered date 
);

mysql> create table assets(
    -> serial_number  char(12) primary key unique,
    -> name           char(100) not null,
    -> description    text not null,
    -> assignee_email char(20) ,
    -> type           char(50) ,
    -> data_assignment date ,
    -> data_registered date 
    -> );

```
minikube ssh
                         _             _            
            _         _ ( )           ( )           
  ___ ___  (_)  ___  (_)| |/')  _   _ | |_      __  
/' _ ` _ `\| |/' _ `\| || , <  ( ) ( )| '_`\  /'__`\
| ( ) ( ) || || ( ) || || |\`\ | (_) || |_) )(  ___/
(_) (_) (_)(_)(_) (_)(_)(_) (_)`\___/'(_,__/'`\____)

$ ls /mnt/data/
'#ib_16384_0.dblwr'   auto.cnf        binlog.000003   ca-key.pem        client-key.pem   ib_logfile0   ibtmp1      performance_schema   server-cert.pem   undo_001
'#ib_16384_1.dblwr'   binlog.000001   binlog.000004   ca.pem            demoasset        ib_logfile1   mysql       private_key.pem      server-key.pem    undo_002
'#innodb_temp'        binlog.000002   binlog.index    client-cert.pem   ib_buffer_pool   ibdata1       mysql.ibd   public_key.pem       sys
```


mysql.default.svc.cluster.local