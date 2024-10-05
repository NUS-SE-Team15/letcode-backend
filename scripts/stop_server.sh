#!/bin/bash

if pgrep -f letcode-0.0.1-SNAPSHOT.jar; then
    echo "Stopping existing LetCode application"
    pkill -f letcode-0.0.1-SNAPSHOT.jar
else
    echo "No existing LetCode application running"
fi
