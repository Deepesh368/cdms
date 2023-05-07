FROM node:14.21.3
WORKDIR /app
COPY package.json ./
COPY package-lock.json ./
RUN npm install
COPY ./ ./
CMD ["npm", "start"]