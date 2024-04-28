FROM postman/newman:5.3.1-alpine as base
LABEL authors="georman"
WORKDIR /etc/newman

RUN npm install -g newman-reporter-html
RUN npm install -g newman-reporter-json-summary

COPY ["integrationtest/*","./"]


ENTRYPOINT newman run HexagonalTestEcommerce.postman_collection.json --environment=hexagonal-environment.postman_environment.json --reporters cli,json --reporter-json-export /data/results/outputfile.json