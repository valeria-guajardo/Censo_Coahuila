# Usa una imagen base oficial de PHP (ajusta la versión y el servidor web según necesites)
FROM php:8.2-fpm

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /var/www/html

# Instala extensiones de PHP comunes (ej. mysqli para MySQL)
# Es crucial para que tu aplicación pueda conectarse a la base de datos
RUN apt-get update && apt-get install -y \
    libzip-dev \
    libicu-dev \
    && docker-php-ext-install pdo pdo_mysql mysqli

# Copia el código de tu aplicación al directorio de trabajo del contenedor
# Nota: La vinculación (mount) en Compose hará que esto sea menos crítico, pero es una buena práctica.
COPY CensoINEGI_Coahuila/src /var/www/html/

# Por defecto, la imagen php:apache ya configura el puerto 80.
EXPOSE 80
