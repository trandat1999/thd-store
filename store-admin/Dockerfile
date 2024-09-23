FROM node:18.20.4-alpine as build
LABEL authors="DatNuclear"
WORKDIR /app
COPY package*.json ./
RUN npm install --force
COPY . .
RUN npm run build --prod
FROM nginx:alpine
COPY --from=build /app/dist/store-admin /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
