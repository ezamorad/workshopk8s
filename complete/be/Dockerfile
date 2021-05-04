# Start with OL runtime.
FROM openliberty/open-liberty:kernel-java8-openj9-ubi

USER root
COPY --chown=1001:0 src/main/liberty/config/server.xml /config/
COPY --chown=1001:0 target/demoasset-be-complete.war /config/apps/
USER 1001