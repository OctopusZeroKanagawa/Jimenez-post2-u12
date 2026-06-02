# Catalogo Spring Boot

![CI/CD Status](https://github.com/OctopusZeroKanagawa/Jimenez-post2-u12/actions/workflows/ci.yml/badge.svg)

**Programación Web — Unidad 12: Despliegue y CI/CD**  
**Post-Contenido 2 — Pipeline CI/CD con GitHub Actions y Docker Hub**  
**Autor:** Andres Felipe Jimenez Ramirez  
**Universidad Francisco de Paula Santander — 2026**

---
## Pipeline CI/CD

El pipeline realiza automáticamente:

1. Compilación Maven.
2. Pruebas unitarias.
3. Generación de reporte JaCoCo.
4. Construcción de imagen Docker.
5. Publicación en Docker Hub.
---
## Entregables

| Documento | Enlace |
|------------|---------|
| Informe PDF | [Abrir](./Informe-1152219.pdf) |
| Imagen Docker | docker pull almcafe/catalogo:latest |

---
## Tecnologías

| Tecnología        | Versión       | Rol                                         |
|-------------------|---------------|---------------------------------------------|
| Java              | 17            | Lenguaje                                    |
| Spring Boot       | 3.2.5         | Framework principal                         |
| Spring Actuator   | 3.2.5         | Health check para Docker y Railway          |
| PostgreSQL        | 16-alpine     | Base de datos de producción                 |
| H2                | Runtime       | Base de datos de desarrollo local           |
| Docker            | multi-stage   | Contenedorización (JDK builder + JRE prod)  |
| Docker Compose    | 3.9           | Orquestación local app + PostgreSQL         |
| Railway           | cloud         | Plataforma de despliegue en la nube         |

---

## Arquitectura de contenedores

```
┌─────────────────────────────────────────────────┐
│              Docker Compose / Railway            │
│                                                  │
│  ┌──────────────────┐    ┌───────────────────┐  │
│  │  app (Spring Boot)│    │  db (PostgreSQL)  │  │
│  │  puerto: 8080     │◄──►│  puerto: 5432     │  │
│  │  perfil: prod     │    │  volumen persistente│ │
│  └──────────────────┘    └───────────────────┘  │
│          Red interna: app-net                    │
└─────────────────────────────────────────────────┘
```

---

## Ejecución local con Docker Compose

```bash
# 1. Construir imagen y levantar servicios (app + PostgreSQL)
podman compose up -d --build
# o con Docker:
docker compose up -d --build

# 2. Verificar que ambos servicios están corriendo
podman compose ps

# 3. Verificar health check
curl http://localhost:8080/actuator/health

# 4. Probar endpoints
curl http://localhost:8080/api/productos
curl -X POST http://localhost:8080/api/productos \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Laptop","precio":3500000,"categoria":"ELECTRONICA"}'

# 5. Bajar los servicios
podman compose down
```

---

## Ejecución local (desarrollo, sin Docker)

```bash
mvn spring-boot:run
# Usa H2 en memoria — perfil default
```

---

## Variables de entorno requeridas (perfil prod)

| Variable                | Descripción                        | Ejemplo                                    |
|-------------------------|------------------------------------|--------------------------------------------|
| `SPRING_PROFILES_ACTIVE`| Activa el perfil de producción     | `prod`                                     |
| `DATABASE_URL`          | URL JDBC de PostgreSQL             | `jdbc:postgresql://db:5432/appdb`          |
| `DB_USER`               | Usuario de la base de datos        | `appuser`                                  |
| `DB_PASS`               | Contraseña de la base de datos     | `apppass`                                  |

En Railway, usar las referencias automáticas:
- `DATABASE_URL` → `${{Postgres.DATABASE_URL}}`
- `DB_USER` → `${{Postgres.PGUSER}}`
- `DB_PASS` → `${{Postgres.PGPASSWORD}}`

---

## Despliegue en Railway

1. Ir a [railway.app](https://railway.app) e iniciar sesión con GitHub
2. **New Project** → **Deploy from GitHub repo** → seleccionar este repositorio
3. Railway detecta el `Dockerfile` automáticamente → esperar el build (3-5 min)
4. **+ New** → **Database** → **Add PostgreSQL**
5. En el servicio app → **Variables** → agregar las 4 variables de la tabla anterior
6. **Settings** → **Networking** → **Generate Domain**
7. Verificar: `https://jimenez-post1-u12.onrender.com`

---

## Endpoints disponibles

| Método | URL                      | Descripción                  |
|--------|--------------------------|------------------------------|
| GET    | /api/productos           | Lista productos activos       |
| GET    | /api/productos/{id}      | Busca producto por ID         |
| POST   | /api/productos           | Crea producto (201 Created)   |
| PUT    | /api/productos/{id}      | Actualiza producto            |
| DELETE | /api/productos/{id}      | Elimina producto (204)        |
| GET    | /actuator/health         | Estado de salud de la app     |

---



