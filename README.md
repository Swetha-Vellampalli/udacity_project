# Spring-Boot-Docker-Kubernetes
This spring boot application contains following
1. H2 in memory database to store values
2. flyway data migration
5. Build and Push the docker images to Docker hub
4. Deploy the image to a EKS Kubernetes Cluster
5. Run the app
 
### Environment Setup
Install docker toolbox

[`https://www.docker.com/products/docker-toolbox`](https://www.docker.com/products/docker-toolbox)

### Maven Install
https://fabianlee.org/2018/05/24/docker-running-a-spring-boot-based-app-using-docker-compose/
Build the project using mvn clean install
command ti build the docker image.
docker build -t swetha29vellampalli/spring-boot-udacity .

Validate the build with
$docker images


### Create a VPC
From Amazon CLoud Formation this can be created which protects communication between worker nodes 
and the AWS Kubernetes API server— for our EKS cluster.
THis is the template which is used
https://amazon-eks.s3.us-west-2.amazonaws.com/cloudformation/2020-03-23/amazon-eks-vpc-sample.yaml

### Create the cluster
aws eks --region us-east-2 create-cluster 
--name springbootapp 
--role-arn arn:aws:iam::983182628457:role/eks_cluster1 
--resources-vpc-config subnetIds=subnet-025b1fe3a46998615,subnet-07aef142022918464,
subnet-00205b6180fd880c6,securityGroupIds=sg-070dff67ef79f2799

### Launch the EKS worker nodes
THis can be done from the cloudformation service in AWS and use the following template
https://amazon-eks.s3.us-west-2.amazonaws.com/cloudformation/2020-06-10/amazon-eks-nodegroup.yaml
ClusterName – the name of your Kubernetes cluster eg: spring boot app
ClusterControlPlaneSecurityGroup – the same security group you used for creating the cluster in previous step.
NodeGroupName – a name for your node group.
NodeInstanceType – leave as-is. The instance type used for the worker nodes.
KeyName – the name of an Amazon EC2 SSH key pair for connecting with the worker nodes once they launch.
VpcId – enter the ID of the VPC you created from create VPC step
Subnets – select the three subnets you created in create VPC step.


Apply the AWS Authenticator Config map
kubectl apply -f aws-auth-cm.yaml

Deploy to kubernetes:
kubectl apply -f kubernetes-deployment.yaml
kubectl apply -f kubernetes-service.yaml

### verify the status of the pods
Verify the status of the pods 
kubectl get pods --watch
NAME                          READY   STATUS    RESTARTS   AGE
kubernetes1-c75fd49b7-4vggj   1/1     Running   0          41m

$ kubectl get all
NAME                               READY   STATUS    RESTARTS   AGE
pod/kubernetes1-6456498687-m98ff   1/1     Running   0          89s

NAME                  TYPE           CLUSTER-IP      EXTERNAL-IP                                                              PORT(S)        AGE
service/kubernetes    ClusterIP      10.100.0.1      <none>                                                                   443/TCP        3h10m
service/kubernetes1   LoadBalancer   10.100.183.84   a0176707303f34b66ac3fb5a00210a36-658487547.us-east-2.elb.amazonaws.com   80:30440/TCP   129m

NAME                          READY   UP-TO-DATE   AVAILABLE   AGE
deployment.apps/kubernetes1   1/1     1            1           129m

NAME                                     DESIRED   CURRENT   READY   AGE
replicaset.apps/kubernetes1-6456498687   1         1         1       89s

### Logs
Local logs are in /usr/tmp/logs/app.log
The logs on AWS EKS are sent to Cloud watch service and it can be monitored from there.

### Rolling Update and 2 versions of the application
To deploy two version of a microservice at the same time. 
Update the kubernetes-deployment.yaml file of the microservice with the new version number.
Travis pipeline will rebuild the image and update the deployment to run the two version at the same time. 
Update the kubernetes-deployment.yaml file for the microservice with the new version number. The travis
pipeline will send kubectl apply command to update the service, and traffic will be redirected to the new version .
To Rollback, change back the verion number in service.yml for the microservice to previous version number and re-trigger the pipeline.
All the update and rollback will occur without downtime.

### CI/CD tool
Travis tool is used to build the docker image, push it to docker hub
Deploy it to AWS- EKS cluster worker nodes.

### H2 database Configuration
* Use the following details to connect to the database.

    ````
    JDBC URL    : jdbc:h2:mem:PersonDB
    User Name   : sa
    password    :
    ````
  
### Run Application

Open the browser and hit the following url to invoke the service.
````
http://<url>>/person?firstName=udacity&lastName=finalproject
````
This is a post request which would create the person with a unique id along with first name and last name.
The data would be persisted in the In-memory H2 database. 
