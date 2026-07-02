# EduTrack — Backend

> API REST para la plataforma académica EduTrack.
> Base técnica con Java 21, Spring Boot, JPA y MySQL.

![Java](https://img.shields.io/badge/Java-21-ED8B00?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-4-6DB33F?logo=springboot)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?logo=mysql)

## 📌 Sobre el proyecto

EduTrack es una plataforma de gestión académica desarrollada como parte del
curso de **Desarrollo Web Integrado**. Este repositorio contiene la
API REST y la primera base de capas por separado para exponer recursos de
forma limpia: repositorios, servicios, controladores, DTOs y excepciones.

## ✨ Características

- ✨ Arquitectura por capas con DTOs y manejo centralizado de errores
- 👤 CRUD REST para `Usuario`
- 🛡️ CRUD REST para `Rol`
- 🔑 CRUD REST para `Permiso`
- ✅ Validación de entradas con `jakarta.validation`

## 🛠️ Tecnologías utilizadas

| Tecnología      | Propósito                    |
|-----------------|------------------------------|
| Java 21         | Lenguaje principal           |
| Spring Boot     | Framework de la aplicación   |
| Spring Data JPA | Acceso a base de datos ORM   |
| Spring Web      | Exposición de endpoints REST |
| Validation      | Validación de DTOs           |
| MySQL           | Base de datos relacional     |
| Maven           | Gestión de dependencias      |

## 🚀 Cómo empezar

### Requisitos previos

- Java 21+
- Maven 3.8+
- MySQL local o remoto

### Instalación

```bash
# Clonar el repositorio
git clone https://github.com/danielaoxeda/edutrack-backend.git
cd edutrack-backend
```

### Ejecutar pruebas

```bash
mvn test
```

## Endpoints disponibles

## Swagger / OpenAPI

Una vez levantada la aplicación, puedes abrir:

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

### Usuarios
- `GET /api/usuarios`
- `GET /api/usuarios/{id}`
- `POST /api/usuarios`
- `PUT /api/usuarios/{id}`
- `DELETE /api/usuarios/{id}`

### Roles
- `GET /api/roles`
- `GET /api/roles/{id}`
- `POST /api/roles`
- `PUT /api/roles/{id}`
- `DELETE /api/roles/{id}`

### Permisos
- `GET /api/permisos`
- `GET /api/permisos/{id}`
- `POST /api/permisos`
- `PUT /api/permisos/{id}`
- `DELETE /api/permisos/{id}`

## Repositorio relacionado
edutrack-frontend — Cliente en React + TypeScript

## 📜 Licencia
Este proyecto fue desarrollado con fines académicos.
