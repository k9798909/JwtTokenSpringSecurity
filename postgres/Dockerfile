FROM postgres:15.2-alpine
COPY . /docker-entrypoint-initdb.d/
ENV POSTGRES_DB=postgres
ENV POSTGRES_USER=postgres
ENV POSTGRES_PASSWORD=postgres