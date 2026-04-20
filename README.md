# EduTrack — Backend

> API REST para la plataforma académica EduTrack.
> Desarrollada con Java Spring Boot, JWT y PostgreSQL en Cloud SQL.

![Java](https://img.shields.io/badge/Java-17-ED8B00?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3-6DB33F?logo=springboot)
![MySQL](https://img.shields.io/badge/MySQL-Cloud_SQL-4479A1?logo=mysql)
![GCP](https://img.shields.io/badge/Despliegue-GCP_App_Engine-4285F4?logo=googlecloud)

## 📌 Sobre el proyecto

EduTrack es una plataforma de gestión académica desarrollada como parte del
curso de **Desarrollo Web Integrado**. Este repositorio contiene la
API REST, encargada de manejar la autenticación, autorización y toda la
lógica de negocio para el seguimiento académico.

## ✨ Características

- 🔐 Autenticación con JWT y autorización por roles
- 👨‍🎓 **Estudiante** — acceder a sus propias notas, tareas y asistencia
- 👨‍🏫 **Docente** — gestionar notas, tareas y registros de asistencia
- 🛠️ **Administrador** — gestión completa de usuarios y configuración de la plataforma
- 📚 Gestión de cursos, asignaturas y matrículas
- ☁️ Listo para la nube con GCP App Engine + Cloud SQL

## 🛠️ Tecnologías utilizadas

| Tecnología        | Propósito                         |
|-------------------|-----------------------------------|
| Java 21           | Lenguaje principal                |
| Spring Boot 3     | Framework de la aplicación        |
| Spring Security   | Autenticación y autorización por roles |
| JWT (jjwt)        | Tokens de autenticación sin estado |
| Spring Data JPA   | Acceso a base de datos con ORM     |
| MySQL        | Base de datos relacional          |
| Cloud SQL (GCP)   | Base de datos administrada en la nube |
| GCP App Engine    | Despliegue en la nube             |
| Maven             | Gestión de dependencias           |

## 🚀 Cómo empezar

### Requisitos previos

- Java 17+
- Maven 3.8+
- MySQL (local) o instancia de Cloud SQL
- Cuenta en GCP (para despliegue)

### Instalación

```bash
# Clonar el repositorio
git clone https://github.com/danielaoxeda/edutrack-backend.git
cd edutrack-backend
```
## Repositorio relacionado
edutrack-frontend — Cliente en React + TypeScript

## 📜 Licencia
Este proyecto fue desarrollado con fines académicos.
