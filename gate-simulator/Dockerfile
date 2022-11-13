<<<<<<< HEAD:Dockerfile
FROM node:erbium-alpine3.12
WORKDIR /opt/base
COPY ./gate-simulator .
=======
FROM node:lts-alpine3.12
WORKDIR /opt/app
COPY . .
>>>>>>> d71d14a2366ce464d5e45c69ac4eb9e1b00a6a33:gate-simulator/Dockerfile
RUN npm install
CMD ["npm", "start"]
EXPOSE 9999
