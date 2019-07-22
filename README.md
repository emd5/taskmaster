# Build & Deploy TaskMaster to EC2 Using DynamoDB

## Author 
Liz Mahoney

## Deployed Link/Routes

Frontend - https://master.d1abu7pwsmv6f1.amplifyapp.com/

Backend - http://taskmaster-app-dev.us-west-2.elasticbeanstalk.com/tasks

Taskmaster Backend Github Repo - https://github.com/emd5/taskmaster

Lambda Resize Image Repo -https://github.com/emd5/lambdaresize

***Must test with Postman***
`/tasks` - get all tasks
http://taskmaster-app-dev.us-west-2.elasticbeanstalk.com/tasks

`/tasks/{id}` - post a task
http://taskmaster-app-dev.us-west-2.elasticbeanstalk.com/tasks/{id}

`/tasks/{id}/state` - change status on a task
http://taskmaster-app-dev.us-west-2.elasticbeanstalk.com/tasks/{id}/state


## Overview
Today, you’ll start building a new application called TaskMaster. It’s a task-tracking application with the same basic goal as Trello: allow users to keep track of tasks to be done and their status. While we’ll start today with a basic feature set, we will continue building out the capabilities of this application over time.
The reason we’re starting small on this server is because the main focus for the rest of the course is not full-stack web development, but is instead to gain experience with different features of AWS. Everything we build will have the added task of deployment using AWS. Today, you’ll get to use DynamoDB as the database for your application. As we continue to build out our skill with DynamoDB, that structure of our data will be our main focus for future development on TaskMaster.

Create a new repo called taskmaster to hold your work on this series of labs. Within that repo, use the Spring Initializr to set up a new web app. Use the directions from the Using DynamoDB With Java resource, linked above, to ensure you have the dependencies you need.

## Getting Started

***To Run Application***

In terminal run: `./gradlew bootrun`

***To view gradle commands***

In terminal run: `./gradlew tasks`

***To Run Test***

In terminal run: `./gradlew test`

## Deploy to Elastic Beanstalk

***EBS***
- run `./gradlew bootJar`
- `eb deploy`


## Setup
Create a new repo `Taskmaster` to hold your work for the last 5 Spring labs. Use the Spring `Initializr` to set 
up an app with dependencies on AWS core, Web, Thymeleaf, JPA, Postgres, and Security (and optionally DevTools for auto 
refresh of app on building). Remember to do your initial commit on the master branch before creating your feature branch. Also, 
see the below note about configuring Spring Security.

## Feature Tasks

***7/22/19***
Using Queues and Notifications

- [] Send an email to an administrator when a task is completed
- [] Send a text to the person to whom a task is assigned (when it gets assigned)
- [] When a task is deleted from Dynamo, trigger a message that will fire a lambda to remove any images associated to it
 from S3
- [] Instead of having S3 run the resizer automatically on upload, evaluate the size of the image in your Java code and
 then send a message to a Q, that will in turn trigger the lambda resizer -- only when the image > 350k

***7/9/19***
- [x] A user should be able to upload an image at any size, and have both the original size and a thumbnail size 
associated with the task in question.
 - [x] When an image is uploaded to your S3 bucket, it should trigger a Lambda function. (That Lambda function may be 
  written in any language.)
 - [x] That function should create a 50x50 pixel thumbnail version of that image, and save it to another S3 bucket. It should do so with a predictable naming convention, so that your server and/or frontend know where that thumbnail image will be.

***7/8/19***
- [x] Users should be able to upload images that are associated with tasks.
    - [x] This ability should be at a route like POST /tasks/{id}/images. (This means it only needs to work for existing tasks, not as part of the initial creation of a task.)
    - [x] Your server should programmatically upload this image to S3.
    - [x] Your server should store the image URL (on S3) somewhere in its database, associated with the task.
    - [x] Fetching a single task (at GET /tasks/{id}) should also include the image URLs associated with that image.

***7/3/19***

- [x] A user should be able to make a GET request to /tasks and receive JSON data representing all of the tasks.
    - [x] Each task should have a title, description, assignee, and status, all of which are strings, as well as an id.
- [x] A user should be able to make a GET request to /users/{name}/tasks and receive JSON data representing all of 
the tasks assigned to that user.
    - [x] This should work (i.e. return an empty array) if the requested username does not have any assigned tasks.
- [x] A user should be able to make a POST request to /tasks with body parameters for title, description, and 
assignee to add a new task.
    - [x] A task should start with a status of Available if there is no assignee, and Assigned if there is an assignee.
    - [x] The response to that request should contain the complete saved data for that task.
    - [x] It should not matter whether or not that assignee is already in the database.
- [x] A user should be able to make a PUT request to /tasks/{id}/state to advance the status of that task.
    - [x] Tasks should advance from Available -> Assigned -> Accepted -> Finished.
- [x] A user should be able to make a PUT request to /tasks/{id}/assign/{assignee} to assign a particular user to a 
task.
    - [x] Changing the assignee should set the task’s state to Assigned.
    - [x] This should work whether or not that assignee name is already in the database.
- [x] A user should be able to access this application on the Internet.
    - [x] The application should be deployed to EC2, with the database on DynamoDB.
    - [x] You should also use DynamoDB for your local application testing; in other words, you should connect to your production database, even in your development environment. (This is generally a bad practice, and we’ll see how to work differently soon.)

### Stretch Goals
- [] Put your application at a custom domain or sub-domain.
- [x] A user should be able to make a DELETE request to /tasks/{id} to delete a task.
- [] A user should be able to make a GET request to /users/{name}/tasks?status=assigned to receive JSON data representing all of the tasks assigned to that user that are not in the Assigned state.
- [] A user should be able to make a GET request to /tasks?status=available to receive JSON data representing all of the available (unassigned) tasks.
- [] That status query parameter should be extended to work with all statuses and all GET requests.
- [] And should allow comma-separated values, i.e. ?status=assigned,accepted.

***7/2/19***
- [x] A user should be able to make a GET request to /tasks and receive JSON data representing all of the tasks.
- [x] Each task should have a title, description, and status.
- [x] A user should be able to make a POST request to /tasks with body parameters for title and description to add a 
new 
task.
- [x] All tasks should start with a status of Available.
- [x] The response to that request should contain the complete saved data for that task.
- [x] A user should be able to make a PUT request to /tasks/{id}/state to advance the status of that task.
- [x] Tasks should advance from Available -> Assigned -> Accepted -> Finished.
- [x] A user should be able to access this application on the Internet.
- [x] The application should be deployed to EC2, with the database on DynamoDB.
- [x] You should also use DynamoDB for your local application testing; in other words, you should connect to your 
production database, even in your development environment. (This is generally a bad practice, and we’ll see how to work differently soon.)

### Stretch Goals
- A user should be able to make a DELETE request to /tasks/{id} to delete a task.

## Resources 

https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/welcome.html
https://www.baeldung.com/spring-data-dynamodb


