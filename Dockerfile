# syntax=docker/dockerfile:1

FROM openjdk:slim
COPY --from=python:3.11.2-slim / /

ENV PYTHONDONTWRITEBYTECODE=1
ENV PYTHONUNBUFFERED=1

WORKDIR /app
COPY . .
RUN mkdir -p /app/tmp

# Instala dependências Python
RUN pip install --upgrade pip && python -m pip install -r /app/client/requirements.txt

# Compila projeto Java e gera o Jar
RUN javac /app/SCCGraphGenerator/src/*.java
RUN cd /app/SCCGraphGenerator/src && jar cfe SCCGraphGenerator.jar App *.class && mv SCCGraphGenerator.jar /app
RUN rm /app/SCCGraphGenerator/src/*.class

# Instala Curl e GraphViz
RUN apt-get update && apt-get install -y curl
RUN apt-get install -y graphviz

EXPOSE 8501
HEALTHCHECK CMD curl --fail http://localhost:8501/_stcore/health
ENTRYPOINT ["streamlit", "run", "./client/app.py", "--server.port=8501", "--server.address=0.0.0.0"]
