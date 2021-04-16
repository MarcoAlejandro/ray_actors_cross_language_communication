# Creates a ray docker image with Java on it. 
# And also contains the fat .jar of the application.

FROM rayproject/ray:nightly

USER ray

# Install OpenJDK-8
RUN sudo apt-get update && \
    sudo apt-get install -y openjdk-8-jdk && \
    sudo apt-get install -y ant && \
    sudo apt-get clean;

# Fix certificate issues
RUN sudo apt-get update && \
    sudo apt-get install ca-certificates-java && \
    sudo apt-get clean && \
    sudo update-ca-certificates -f;

# Setup JAVA_HOME -- useful for docker commandline
ENV JAVA_HOME /usr/lib/jvm/java-8-openjdk-amd64/
RUN export JAVA_HOME
WORKDIR /home/ray

# Setup the CLASSPATH environment variable for Java
# Might be necessary for running the job.
COPY python/echo.jar /home/ray

# Copy the run on head script
COPY python/main.py /home/ray
COPY python/ray_echo.py /home/ray

ENTRYPOINT /bin/bash