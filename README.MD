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
2. If you use eclipse, it already comen with a embeeded mvn
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

ibmcloud ks cluster config --cluster c2496fgd0ijjt6knhlg0
kubectl config current-context


### Building the project

1. Navigate to the `basics\be` folder
2. Run the command `mvn clean package`
The execution should end with success and a new file <your path>/workshop/be/target/demoasset.war


### Build the docker image and make it available for minikube cluster

1. Run the command `docker build -t demoasset-be .`
List the local docker images for 
```
$ docker image ls 
REPOSITORY          TAG       IMAGE ID       CREATED          SIZE
demoasset-be        latest    09ad6881b17f   24 seconds ago   729MB
```

2. Make the new docker image available to minube by running `minikube cache add demoasset-be:latest` (or `minikube image load demoasset-be:latest --daemon`)

### Explore the cluster

1. Get pods running in the default ***namespace*** 
`kubectl get pods`

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

7. Get the service exposed port with `$ kubectl describe service demoasset-be` command. Grab the `NodePort` value
```
Name:                     demoasset-be
...
NodePort:                 <unset>  31228/TCP
...
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
        f:ownerReferences:
          .: {}
          k:{"uid":"24cae409-f666-457b-a980-af2492adce9d"}:
            .: {}
            f:apiVersion: {}
            f:blockOwnerDeletion: {}
            f:controller: {}
            f:kind: {}
            f:name: {}
            f:uid: {}
      f:spec:
        f:containers:
          k:{"name":"demoasset-be"}:
            .: {}
            f:image: {}
            f:imagePullPolicy: {}
            f:name: {}
            f:ports:
              .: {}
              k:{"containerPort":9080,"protocol":"TCP"}:
                .: {}
                f:containerPort: {}
                f:protocol: {}
            f:resources: {}
            f:terminationMessagePath: {}
            f:terminationMessagePolicy: {}
        f:dnsPolicy: {}
        f:enableServiceLinks: {}
        f:imagePullSecrets:
          .: {}
          k:{"name":"all-icr-io"}:
            .: {}
            f:name: {}
        f:restartPolicy: {}
        f:schedulerName: {}
        f:securityContext: {}
        f:terminationGracePeriodSeconds: {}
    manager: kube-controller-manager
    operation: Update
    time: "2021-04-30T11:13:09Z"
  - apiVersion: v1
    fieldsType: FieldsV1
    fieldsV1:
      f:status:
        f:conditions:
          k:{"type":"ContainersReady"}:
            .: {}
            f:lastProbeTime: {}
            f:lastTransitionTime: {}
            f:status: {}
            f:type: {}
          k:{"type":"Initialized"}:
            .: {}
            f:lastProbeTime: {}
            f:lastTransitionTime: {}
            f:status: {}
            f:type: {}
          k:{"type":"Ready"}:
            .: {}
            f:lastProbeTime: {}
            f:lastTransitionTime: {}
            f:status: {}
            f:type: {}
        f:containerStatuses: {}
        f:hostIP: {}
        f:phase: {}
        f:podIP: {}
        f:podIPs:
          .: {}
          k:{"ip":"172.17.0.3"}:
            .: {}
            f:ip: {}
        f:startTime: {}
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
  dnsPolicy: ClusterFirst
  enableServiceLinks: true
  imagePullSecrets:
  - name: all-icr-io
  nodeName: minikube
  priority: 0
  restartPolicy: Always
  schedulerName: default-scheduler
  securityContext: {}
  serviceAccount: default
  serviceAccountName: default
  terminationGracePeriodSeconds: 30
  tolerations:
  - effect: NoExecute
    key: node.kubernetes.io/not-ready
    operator: Exists
    tolerationSeconds: 300
  - effect: NoExecute
    key: node.kubernetes.io/unreachable
    operator: Exists
    tolerationSeconds: 300
  volumes:
  - name: default-token-kv4z4
    secret:
      defaultMode: 420
      secretName: default-token-kv4z4
status:
  conditions:
  - lastProbeTime: null
    lastTransitionTime: "2021-04-30T11:13:09Z"
    status: "True"
    type: Initialized
  - lastProbeTime: null
    lastTransitionTime: "2021-04-30T11:13:11Z"
    status: "True"
    type: Ready
  - lastProbeTime: null
    lastTransitionTime: "2021-04-30T11:13:11Z"
    status: "True"
    type: ContainersReady
  - lastProbeTime: null
    lastTransitionTime: "2021-04-30T11:13:09Z"
    status: "True"
    type: PodScheduled
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


4. Explore the service devinition with `kubectl describe service <service-name>` 
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