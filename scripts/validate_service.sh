# validate_service.sh
echo "Waiting for Spring application to start..."
sleep 20  # 等待 20 秒，确保 Spring 完全启动

if curl -f http://localhost:8101/api/health; then
  echo "Application is running."
  exit 0
else
  echo "Application is not running."
  exit 1
fi
