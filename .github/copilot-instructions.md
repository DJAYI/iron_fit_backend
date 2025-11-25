# IronFit Backend - AI Coding Agent Instructions

## Project Overview

Spring Boot 4.0 fitness management backend using JWT auth, MySQL database, and feature-based vertical slice architecture. Built for managing trainers, clients, workout routines, and training plans.

## Feature Status

### ✅ Completed Features (CRUD Básico)

- **Users & Authentication** - UserEntity with username, password, roles (N:M with RoleEntity), JWT cookies
- **Clients** - ClientEntity with personal info + OneToOne UserEntity
- **Trainers** - TrainerEntity with personal info + OneToOne UserEntity + ManyToMany TrainerExpertise
- **Auditors** - AuditorEntity with personal info + OneToOne UserEntity
- **Attendance** - AttendanceEntity with client FK, datetime, observations (REQUIERE MEJORA - ver abajo)
- **Categories** - CategoryEntity for exercise categorization
- **Training Plan States** - TrainmentPlanState entity
- **Objectives** - TrainmentPlanObjective entity
- **Training Plan Objective Assignments** - N:M junction table between plans and objectives
- **Muscular Groups** - MuscularGroupEntity
- **Trainer Specialties** - TrainerExpertise entity with ManyToMany to TrainerEntity
- **Exercises** - Exercise entity with name, muscular group FK, category FK, description
- **Training Plans** - TrainmentPlan entity with client FK, trainer FK, objective FK, state FK, dates, name, description
- **Routines** - Routine entity with name, description, training plan FK
- **Routine Exercises** - RoutineExercise entity with routine FK, exercise FK, order, sets, reps, time, rest, target weight
- **Sessions** - Session entity with start date, start time, notes, routine FK
- **Physical Evaluations** - PhysicalEvaluation entity with client FK, trainer FK, date, weight, BMI, body fat %, measurements, notes
- **Nutrition Plans** - NutritionPlan entity with training plan FK, calories, protein/carbs/fat (grams), description

### 🚧 Funcionalidad Faltante por Rol

#### ADMINISTRADOR (AUDITOR Role)

- [ ] **Dashboard General** - Estadísticas: total clientes, total entrenadores, planes activos, asistencia semanal
- [ ] **Gestión de Usuarios**
  - [ ] Soft delete (desactivar/activar clientes, entrenadores, auditors)
  - [ ] Endpoint para cambiar estado de usuario (isEnabled)
  - [ ] Ver usuarios por estado (activos/inactivos)
- [ ] **Reportes de Asistencia**
  - [ ] Por rango de fechas
  - [ ] Por cliente
  - [ ] Por entrenador
  - [ ] Con filtros de estado (asistió/no asistió/asistió sin rutina)
- [ ] **Reportes de Cumplimiento**
  - [ ] Porcentaje de sesiones completadas por plan
  - [ ] Progreso por cliente (sesiones realizadas vs programadas)
  - [ ] Ejercicios completados vs asignados

#### ENTRENADOR (TRAINER Role)

- [ ] **Dashboard del Entrenador** - Resumen de MIS clientes asignados
- [ ] **Mis Clientes**
  - [ ] GET /api/trainers/me/clients (solo clientes asignados a mí)
  - [ ] Ver estado del plan activo por cliente
  - [ ] Ver historial de asistencia por cliente
- [ ] **Mis Planes de Entrenamiento**
  - [ ] GET /api/trainers/me/training-plans (solo mis planes)
  - [ ] Ver estado: activo/cerrado/finalizado
- [ ] **Mis Rutinas**
  - [ ] GET /api/trainers/me/routines (solo rutinas de mis planes)
- [ ] **Cumplimiento por Cliente**
  - [ ] GET /api/trainers/me/clients/{id}/compliance
  - [ ] Porcentaje de asistencia
  - [ ] Sesiones completadas vs programadas
  - [ ] Ejercicios completados por sesión

#### CLIENTE (CLIENT Role)

- [ ] **Mi Perfil**
  - [ ] GET /api/clients/me (datos personales del cliente autenticado)
  - [ ] PUT /api/clients/me (actualizar teléfono, email)
- [ ] **Mi Plan Activo**
  - [ ] GET /api/clients/me/training-plan (plan activo del cliente autenticado)
  - [ ] Ver calendario de rutinas
- [ ] **Mis Rutinas**
  - [ ] GET /api/clients/me/routines (rutinas de mi plan activo)
  - [ ] Ver ejercicios por rutina con descripción
- [ ] **Mi Asistencia**
  - [ ] POST /api/clients/me/attendance (registrar asistencia con estado)
  - [ ] GET /api/clients/me/attendance (ver mi historial)
  - [ ] Marcar ejercicios completados en sesión
  - [ ] Ver porcentaje de cumplimiento
- [ ] **Mi Progreso**
  - [ ] GET /api/clients/me/evaluations (mis evaluaciones físicas)
  - [ ] Ver evolución: peso, IMC, % grasa (tablas)

### 🔧 Mejoras Requeridas en Attendance

**Problema actual:** AttendanceEntity solo tiene `client FK, datetime, observations`

**Necesita:**

- Campo `status` enum: ATTENDED, NOT_ATTENDED, ATTENDED_NO_ROUTINE
- Campo `completed` boolean: true si completó TODOS los ejercicios de la sesión
- Campo `sessionId` FK: vincular asistencia con Session específica
- Tabla `AttendanceExercise` (N:M): tracking de ejercicios completados por sesión
  - attendance FK
  - routine_exercise FK
  - completed boolean
  - notes

## Architecture Pattern: Feature-Based Vertical Slices

Each feature lives under `features/` with a consistent 3-layer structure:

```
features/<feature_name>/
├── application/          # Service layer (business logic)
│   └── services/        # Named by action: Register*, Modify*, Retrieve*
├── domain/              # Core business objects
│   ├── entities/        # JPA entities (@Entity, Lombok @Data/@Builder)
│   └── dto/            # Records for requests/responses
│       ├── requests/
│       └── responses/
└── infrastructure/      # External interfaces
    ├── <Feature>Controller.java  # REST endpoints
    └── repository/               # Spring Data JPA repos
```

**Key Points:**

- Services use imperative naming: `RegisterExpertise`, `ModifyExpertise`, `RetrieveExpertises` (not CRUD terminology)
- All services have an `execute()` method returning `HashMap<String, Object>` with error handling via `"error"` key
- Controllers inject 3 services (Register, Modify, Retrieve) and map HashMap results to ResponseEntity
- DTOs use Java records for immutability
- Entities use Lombok `@Data`, `@Builder`, `@AllArgsConstructor`, `@NoArgsConstructor`

### Example Feature Pattern

**Service (application/services/):**

```java
@Service
public class RegisterExpertise {
    @Autowired
    private TrainerExpertiseRepository repository;

    public HashMap<String, Object> execute(RegisterExpertiseDto dto) {
        try {
            // Business logic
            TrainerExpertise saved = repository.save(entity);
            return new HashMap<>() {{
                put("message", "...");
                put("data", responseDto);
            }};
        } catch (Exception e) {
            return new HashMap<>() {{
                put("message", "...");
                put("error", true);
            }};
        }
    }
}
```

**Controller (infrastructure/):**

```java
@RestController
@RequestMapping("/api/expertises")
public class ExpertiseController {
    @Autowired private RegisterExpertise registerExpertise;
    @Autowired private ModifyExpertise modifyExpertise;
    @Autowired private RetrieveExpertises retrieveExpertises;

    @PreAuthorize("hasRole('AUDITOR')")
    @PostMapping
    public ResponseEntity<?> register(@RequestBody RegisterDto dto) {
        HashMap<String, Object> result = registerExpertise.execute(dto);
        if (result.containsKey("error")) return ResponseEntity.badRequest().body(result);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}
```

## Security Architecture

**JWT Authentication Flow:**

- `JwtUtils` (core/utils/) creates/validates tokens (2h expiry) using Auth0 JWT library
- `JwtTokenValidator` filter extracts JWT from cookies (not Authorization header)
- Tokens stored as HttpOnly cookies with `SameSite=Lax`, `domain=localhost`
- `SecurityConfig` adds filter before `BasicAuthenticationFilter`
- Password encoding: BCrypt via `PasswordEncoder` bean
- `CustomUserDetailsService` handles login/user loading

**Authorization:**

- Method-level with `@PreAuthorize`: `isAuthenticated()`, `hasRole('AUDITOR')`, `hasRole('TRAINER')`
- Roles stored in `RoleEntity` with `@ManyToMany` to `UserEntity`
- All endpoints permit by default in SecurityFilterChain; restrict with annotations

**People Management:**

- Three entity types: `ClientEntity`, `TrainerEntity`, `AuditorEntity`
- Each has `@OneToOne(cascade = CascadeType.ALL)` to `UserEntity` for auth
- Shared attributes: firstName, lastName, documentType, documentNumber, email, phoneNumber
- `TrainerEntity` has `@ManyToMany` to `TrainerExpertise` for specializations

## Database Configuration

**MySQL Setup (application.yaml):**

```yaml
spring.datasource.url: jdbc:mysql://localhost:3306/iron_fit_db?createDatabaseIfNotExist=true
spring.jpa.hibernate.ddl-auto: create-drop # ⚠️ DROPS SCHEMA ON RESTART
```

**Entity Conventions:**

- `@GeneratedValue(strategy = GenerationType.IDENTITY)` for Long IDs
- `@GeneratedValue(strategy = GenerationType.UUID)` for UUID (UserEntity)
- Table names via `@Table(name = "...")` use snake_case
- Column names default to camelCase unless overridden with `@Column(name = "...")`

## Development Workflow

**Build & Run:**

```powershell
# Maven wrapper (includes Spring Boot Devtools)
.\mvnw clean install
.\mvnw spring-boot:run

# Direct Maven
mvn clean install
mvn spring-boot:run
```

**API Documentation:**

- SpringDoc OpenAPI available at `/swagger-ui.html` when running
- Configured via `springdoc-openapi-starter-webmvc-ui` dependency

**Testing:**

- Test class scaffold exists in `IronFitBackendApplicationTests.java`
- Spring Boot test starters included for JPA, Security, Validation, WebMVC

## Adding New Features

**When implementing incomplete features, follow this workflow:**

1. Create feature directory: `features/<feature_name>/`
2. Add layers: `application/services/`, `domain/entities/`, `domain/dto/`, `infrastructure/`
3. Define entity in `domain/entities/` with Lombok annotations
4. Create repository interface extending `JpaRepository<Entity, ID>` in `infrastructure/repository/`
5. Implement services: `Register*`, `Modify*`, `Retrieve*` with `execute()` method
6. Create controller injecting services, mapping to REST endpoints
7. Add DTOs as records in `domain/dto/requests/` and `domain/dto/responses/`
8. Apply `@PreAuthorize` for role-based access

**For entities with foreign keys:**

- Use `@ManyToOne`, `@OneToMany`, or `@ManyToMany` annotations appropriately
- Junction tables for N:M relationships use `@JoinTable(name = "...")`
- Set `fetch = FetchType.EAGER` or `LAZY` based on usage patterns
- Use `cascade = CascadeType.ALL` for entities that should cascade operations

## Common Patterns

**Error Handling:** Services return HashMap with `"error": true` key; controllers check and return 400/401
**Null Safety:** Entities use Lombok which generates proper null handling in builders
**Validation:** Use Jakarta validation annotations on DTOs (`@NotNull`, `@Email`, etc.)
**CORS:** Globally configured in `CorsConfig` allowing all methods/origins (dev mode)
**Logging:** Spring Boot logging available; SQL visible via `spring.jpa.show-sql: true`

## Important Notes

- Database resets on every application restart (ddl-auto: create-drop)
- Passwords in application.yaml are NOT production-safe (visible private keys)
- CORS/Security configured for localhost development only
- JWT cookies require matching domain/path for cross-port communication
- Feature names use underscore separation (trainer_expertise, trainment_plans)
