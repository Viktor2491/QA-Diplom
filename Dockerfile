FROM node:erbium-alpine3.12
WORKDIR /opt/base
COPY ./gate-simulator .
RUN npm install
CMD ["npm", "start"]
EXPOSE 9999
