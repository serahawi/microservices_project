#!/bin/bash

services=("configserver" "accounts" "loans" "cards")

echo "🚀 Starting Maven Jib Docker build for all services..."

for service in "${services[@]}"; do
  echo "🔹 Building $service ..."
  cd "$service" || { echo "❌ Failed to enter $service"; exit 1; }

  mvn clean compile jib:dockerBuild -DskipTests

  if [ $? -ne 0 ]; then
    echo "❌ Build failed for $service"
    exit 1
  fi

  cd ..
  echo "✅ Done: $service"
done

echo "🎉 All services built successfully!"