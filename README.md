# EduManageSystem
A versatile educational management platform that streamlines course registration, class assignments, and user role management for students, teachers, and administrators.

## Prerequisites
Before proceeding, ensure that Docker is installed on your system. You can download and install Docker from Docker's official website.

## Pulling the Docker Image
First, pull the image from Docker Hub:
```
docker pull makiato1999/edu-manage-system-repo:1.0
```
## Running the Container
To run the container in the background, use the following command:
```
docker run -d -p 8080:8080 --name edu-manage-system makiato1999/edu-manage-system-repo:1.0
```
Options Explained
- -d: Run the container in detached mode (in the background).
- -p 8080:8080: Map port 8080 of the container to port 8080 on the host. Adjust the ports as necessary for your application.
- -name edu-manage-system: Name the container edu-manage-system for easy reference.
## Starting the application
Access the app by `http://localhost:8080/`
