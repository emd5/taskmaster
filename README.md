# Build & Deploy TaskMaster to EC2 Using DynamoDB

## Author 
Liz Mahoney

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

## Setup
Create a new repo `Taskmaster` to hold your work for the last 5 Spring labs. Use the Spring `Initializr` to set 
up an app with dependencies on AWS core, Web, Thymeleaf, JPA, Postgres, and Security (and optionally DevTools for auto 
refresh of app on building). Remember to do your initial commit on the master branch before creating your feature branch. Also, 
see the below note about configuring Spring Security.

## Feature Tasks
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

### Resources 


