# DataSeeder - Datos de Prueba

## Descripción

El `DataSeeder` carga automáticamente datos de prueba al iniciar la aplicación cuando el perfil `dev` está activo.

## Datos que se crean

### 1. Roles
| Rol | Descripción |
|-----|-------------|
| ADMIN | Administrador del sistema |
| DOCENTE | Profesor titular |
| ESTUDIANTE | Alumno matriculado |

### 2. Permisos
- `usuarios.leer`, `usuarios.escribir`
- `cursos.leer`, `cursos.escribir`
- `notas.leer`, `notas.escribir`
- `asistencia.leer`, `asistencia.escribir`

### 3. Usuarios

#### Admin
| Email | Password |
|-------|----------|
| admin@edutrack.edu | password123 |

#### Docentes
| Email | Password | Especialidad |
|-------|----------|--------------|
| roberto.martinez@edutrack.edu | password123 | Ingenieria de Software |
| ana.silva@edutrack.edu | password123 | Base de Datos |
| carlos.gomez@edutrack.edu | password123 | Inteligencia Artificial |

#### Estudiantes
| Email | Password | Codigo | Estado |
|-------|----------|--------|--------|
| vcastillo@edutrack.edu | password123 | 2023-0145 | REGULAR |
| mrojas@edutrack.edu | password123 | 2023-0211 | REGULAR |
| cmendoza@edutrack.edu | password123 | 2023-0089 | OBSERVADO |
| arojas@edutrack.edu | password123 | 2023-0301 | REGULAR |
| lpena@edutrack.edu | password123 | 2023-0412 | REGULAR |
| lmendez@edutrack.edu | password123 | 2023-0182 | REGULAR |
| scastro@edutrack.edu | password123 | 2023-0523 | REGULAR |
| dtorres@edutrack.edu | password123 | 2023-0091 | CONDICIONADO |
| gruiz@edutrack.edu | password123 | 2023-0341 | REGULAR |
| vgomez@edutrack.edu | password123 | 2023-0112 | REGULAR |

### 4. Periodo Académico
- **2026-1**: 2026-03-01 al 2026-07-31 (17 semanas)

### 5. Cursos
| Codigo | Nombre | Creditos |
|--------|--------|----------|
| ISW-401 | Ingenieria de Software III | 4 |
| BBD-302 | Base de Datos II | 3 |
| INT-501 | Inteligencia Artificial | 4 |
| SOP-205 | Sistemas Operativos | 3 |

### 6. Secciones
| Curso | Periodo | Grupo | Capacidad |
|-------|---------|-------|-----------|
| ISW-401 | 2026-1 | Grupo A | 35 |
| BBD-302 | 2026-1 | Grupo B | 30 |
| INT-501 | 2026-1 | Grupo A | 25 |
| SOP-205 | 2026-1 | Grupo B | 32 |

### 7. Asignaciones Docente-Seccion
| Docente | Seccion |
|---------|---------|
| Roberto Martinez | ISW-401 Grupo A |
| Roberto Martinez | SOP-205 Grupo B |
| Ana Silva | BBD-302 Grupo B |
| Carlos Gomez | INT-501 Grupo A |

### 8. Criterios de Evaluacion
- ISW-401: PC (30%), Parcial (30%), Final (25%), Proyecto (15%)
- BBD-302: Lab (40%), Parcial (30%), Final (30%)
- INT-501: Practicas (25%), Proyecto (35%), Final (40%)

### 9. Matrículas
- **ISW-401 Grupo A**: 6 estudiantes
- **BBD-302 Grupo B**: 4 estudiantes
- **INT-501 Grupo A**: 3 estudiantes

### 10. Actividades de Prueba
- PC1: Patrones Singleton y Factory (ISW-401)
- Practica Dirigida: Patron MVC (ISW-401)
- Proyecto Final (ISW-401)

### 11. Entregas de Prueba
- 3 entregas registradas con diferentes estados (ENTREGADO, ATRASADO, REVISADO)

### 12. Notificaciones
- Notificaciones de prueba para estudiantes

## Endpoints para probar

### Login
```bash
# Login como estudiante
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"vcastillo@edutrack.edu","password":"password123"}'

# Login como docente
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"roberto.martinez@edutrack.edu","password":"password123"}'

# Login como admin
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"admin@edutrack.edu","password":"password123"}'
```

### Estudiantes
```bash
GET /api/estudiantes
GET /api/estudiantes/{id}
GET /api/estudiantes/codigo/2023-0145
GET /api/estudiantes/estado/REGULAR
```

### Cursos
```bash
GET /api/cursos
GET /api/cursos/{id}
GET /api/cursos/codigo/ISW-401
```

### Secciones
```bash
GET /api/secciones
GET /api/secciones/{id}
GET /api/secciones/periodo/{periodoId}
GET /api/secciones/curso/{cursoId}
```

### Matrículas
```bash
GET /api/matriculas
GET /api/matriculas/seccion/{seccionId}
GET /api/matriculas/estudiante/{estudianteId}
```

### Periodo Activo
```bash
GET /api/periodos/activo
```

### Asistencias
```bash
GET /api/asistencias/sesion/{sesionId}
GET /api/asistencias/matricula/{matriculaId}
```

### Actividades
```bash
GET /api/actividades
GET /api/actividades/visibles
GET /api/actividades/semana/{semanaId}
```

### Entregas
```bash
GET /api/entregas
GET /api/entregas/actividad/{actividadId}
GET /api/entregas/matricula/{matriculaId}
```

### Notificaciones
```bash
GET /api/notificaciones/usuario/{usuarioId}
GET /api/notificaciones/usuario/{usuarioId}/no-leidas/count
PATCH /api/notificaciones/{id}/leida
```

## H2 Console

Accede a: http://localhost:8080/h2-console

- **JDBC URL**: `jdbc:h2:mem:edutrack`
- **Username**: `sa`
- **Password**: (vacío)

## Notas

- Los datos se cargan automáticamente al iniciar con perfil `dev`
- Con `ddl-auto=create-drop`, la BD se reinicia en cada ejecución
- Para agregar más datos de prueba, edita `DataSeeder.java`
