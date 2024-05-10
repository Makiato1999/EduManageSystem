#!/bin/bash

# Pull the latest image (optional step, if you want to ensure the latest version is used)
docker pull edu-manage-system:1.0

# Stop the previous container if it exists
docker stop EduManageSystem
docker rm EduManageSystem

# Run the new container
docker run -p 8080:8080 --name EduManageSystem -d edu-manage-system:1.0