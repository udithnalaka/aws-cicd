# aws-cicd
simple spring boot crud application deployed to AWS ECS using AWS CodeBuild and CodePipeline services

### Technologies / Frameworks and AWS services used

* SpringBoot v3.5
* Java 21
* Docker
* Swagger documentation
* AWS ECR / ECS
* AWS CodeBuild / CodePipeline

Dockerfile: create the java base image and run the springboot app jar inside a container.

buildspec.yml: has the build steps to
* build the application
* create a docker image
* push to AWS ECR