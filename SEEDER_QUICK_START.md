# Seeder - Guía Rápida de Uso

## Instalación Completa ✅

1. ✅ Dependencia JavaFaker agregada a `pom.xml`
2. ✅ Servicio `DatabaseSeeder.java` creado en `core/utils/`
3. ✅ Controlador `SeederController.java` creado con endpoints REST
4. ✅ Compilación exitosa verificada

## Uso Rápido

### 1. Iniciar la aplicación

```powershell
.\mvnw spring-boot:run
```

### 2. Autenticarse como AUDITOR

Primero debes crear un usuario con rol AUDITOR o usar uno existente:

```bash
POST http://localhost:8080/api/auth/login
Content-Type: application/json

{
  "username": "admin",
  "password": "password"
}
```

El JWT se guardará automáticamente en las cookies.

### 3. Ejecutar el Seeder

#### Opción A: Usar Postman

1. Importar `Seeder_Collection.postman_collection.json`
2. Asegurarte de estar autenticado (cookie JWT presente)
3. Ejecutar cualquier endpoint:
   - Small: 100 registros (~5-10 seg)
   - Medium: 500 registros (~20-30 seg)
   - Large: 1000 registros (~40-60 seg)
   - XLarge: 5000 registros (~3-5 min)

#### Opción B: Usar cURL

```bash
# Seed con cantidad personalizada (1000 registros)
curl -X POST "http://localhost:8080/api/seeder/seed?quantity=1000" \
  -H "Cookie: jwt=TU_TOKEN_JWT_AQUI"

# Seed rápido (100 registros)
curl -X POST "http://localhost:8080/api/seeder/seed/small" \
  -H "Cookie: jwt=TU_TOKEN_JWT_AQUI"
```

#### Opción C: Usar PowerShell

```powershell
# Variables
$baseUrl = "http://localhost:8080"
$jwtToken = "TU_TOKEN_JWT_AQUI"

# Seed con cantidad personalizada
Invoke-WebRequest -Uri "$baseUrl/api/seeder/seed?quantity=1000" `
  -Method POST `
  -Headers @{"Cookie"="jwt=$jwtToken"}

# Seed pequeño (100 registros)
Invoke-WebRequest -Uri "$baseUrl/api/seeder/seed/small" `
  -Method POST `
  -Headers @{"Cookie"="jwt=$jwtToken"}
```

## Respuesta Exitosa

```json
{
  "message": "Database seeded successfully",
  "quantity": 1000,
  "duration_ms": 45230,
  "stats": {
    "roles": 3,
    "categories": 1000,
    "muscular_groups": 1000,
    "trainer_expertises": 1000,
    "objectives": 1000,
    "states": 4,
    "clients": 1000,
    "trainers": 1000,
    "auditors": 1000,
    "exercises": 1000,
    "training_plans": 1000,
    "nutrition_plans": 1000,
    "physical_evaluations": 1000,
    "routines": 1000,
    "routine_exercises": 1000,
    "sessions": 1000,
    "attendances": 1000,
    "attendance_exercises": 1000
  }
}
```

## Endpoints Disponibles

| Endpoint                           | Cantidad         | Tiempo Estimado |
| ---------------------------------- | ---------------- | --------------- |
| `POST /api/seeder/seed?quantity=N` | Custom (1-10000) | Variable        |
| `POST /api/seeder/seed/small`      | 100              | 5-10 seg        |
| `POST /api/seeder/seed/medium`     | 500              | 20-30 seg       |
| `POST /api/seeder/seed/large`      | 1000             | 40-60 seg       |
| `POST /api/seeder/seed/xlarge`     | 5000             | 3-5 min         |

## Datos Generados

### Entidades Creadas (en orden de dependencias):

1. **Tablas Base**

   - Roles (3 fijos: CLIENT, TRAINER, AUDITOR)
   - Categories
   - Muscular Groups
   - Trainer Expertises
   - Training Plan Objectives
   - Training Plan States (4 fijos: Active, Completed, Cancelled, On Hold)

2. **Usuarios y Personas**

   - Clients (con User asociado)
   - Trainers (con User asociado + Expertises)
   - Auditors (con User asociado)

3. **Ejercicios**

   - Exercises (con categoría y grupo muscular)

4. **Planes de Entrenamiento**

   - Training Plans
   - Nutrition Plans
   - Physical Evaluations

5. **Rutinas y Sesiones**

   - Routines
   - Routine Exercises
   - Sessions

6. **Asistencia**
   - Attendances
   - Attendance Exercises

### Credenciales Generadas

Todos los usuarios creados tienen:

- **Password**: `password123` (encriptado con BCrypt)
- **Username**: Generado con Faker + sufijo del rol
- **Estado**: Activos y sin restricciones

## ⚠️ Advertencias Importantes

1. **Database Reset**: La aplicación usa `ddl-auto: create-drop`, por lo que:

   - La BD se borra al cerrar la app
   - Debes ejecutar el seeder después de cada reinicio

2. **Solo AUDITOR**: Solo usuarios con rol AUDITOR pueden ejecutar el seeder

3. **Duplicados**: Ejecutar el seeder múltiples veces crea registros duplicados

4. **Performance**: Cantidades grandes (>5000) pueden tardar varios minutos

## Solución de Problemas

### Error 403 Forbidden

- Verificar que estás autenticado como AUDITOR
- Verificar que el JWT está en las cookies

### Error 400 Bad Request

- Verificar que `quantity` está entre 1 y 10000
- Verificar formato del parámetro

### El seeder tarda mucho

- Reducir la cantidad de registros
- Usar endpoints preset (small/medium)

### Errores de Foreign Key

- Reportar como bug (no debería pasar)
- Verificar que todas las entidades están creadas

## Documentación Completa

Ver `SEEDER_README.md` para documentación detallada incluyendo:

- Rangos de datos generados
- Características de cada entidad
- Guía de desarrollo
- Consideraciones de seguridad

## Archivos Creados

```
iron_fit_backend/
├── pom.xml (actualizado con JavaFaker)
├── SEEDER_README.md (documentación completa)
├── SEEDER_QUICK_START.md (esta guía)
├── Seeder_Collection.postman_collection.json (colección Postman)
└── src/main/java/com/iron_fit/iron_fit_backend/core/utils/
    ├── DatabaseSeeder.java (servicio principal)
    └── SeederController.java (endpoints REST)
```

## Siguiente Paso

1. **Compilar el proyecto** (ya hecho ✅):

   ```powershell
   .\mvnw clean compile
   ```

2. **Iniciar la aplicación**:

   ```powershell
   .\mvnw spring-boot:run
   ```

3. **Autenticarse como AUDITOR**

4. **Ejecutar el seeder**:
   ```bash
   POST http://localhost:8080/api/seeder/seed/small
   ```

¡Listo! 🎉
