#!/bin/bash
cp ./support-integration-core/target/support-integration-core-0.0.1-SNAPSHOT.jar ./deploy
docker-compose up --build -d