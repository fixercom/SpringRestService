FROM postgres:14-alpine
COPY src/main/resources/schema.sql /docker-entrypoint-initdb.d/