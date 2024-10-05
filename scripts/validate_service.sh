#!/bin/bash
# 健康检查，确认应用程序在指定端口上运行
if curl -f http://localhost:8101/api/health; then
  echo "Application is running."
  exit 0
else
  echo "Application is not running."
  exit 1
fi
