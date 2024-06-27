@echo off
if /i "%1"=="BUILD" (
    docker build -t graphscc-app .  
)
if /i "%ERRORLEVEL%"=="0" (
    docker run --name graphscc-visualizer -p 8501:8501 -d graphscc-app
)