# Database Seeder - IronFit Backend

## Overview

A customizable database seeder using JavaFaker to populate the IronFit database with realistic test data. This tool is essential for development, testing, and demo environments.

## Features

- **Customizable Quantity**: Generate any number of records (1-10,000 per entity)
- **Realistic Data**: Uses JavaFaker library for authentic-looking data
- **Maintains Relationships**: Properly creates foreign key relationships between entities
- **Transaction Safe**: All seeding operations wrapped in a single transaction
- **Performance Tracking**: Reports execution time and statistics
- **Role-Based Access**: Only AUDITOR role can execute seeding

## Installation

The JavaFaker dependency is already included in `pom.xml`:

```xml
<dependency>
    <groupId>com.github.javafaker</groupId>
    <artifactId>javafaker</artifactId>
    <version>1.0.2</version>
</dependency>
```

## API Endpoints

### Custom Quantity Seed

```http
POST /api/seeder/seed?quantity={number}
Authorization: Bearer {JWT_TOKEN}
Role Required: AUDITOR
```

**Parameters:**

- `quantity` (optional): Number of records per entity (default: 1000, max: 10000)

**Example:**

```bash
curl -X POST "http://localhost:8080/api/seeder/seed?quantity=1000" \
  -H "Cookie: jwt={your_jwt_token}"
```

### Preset Quantity Endpoints

#### Small Dataset (100 records)

```http
POST /api/seeder/seed/small
```

#### Medium Dataset (500 records)

```http
POST /api/seeder/seed/medium
```

#### Large Dataset (1000 records)

```http
POST /api/seeder/seed/large
```

#### Extra Large Dataset (5000 records)

```http
POST /api/seeder/seed/xlarge
```

## Response Format

### Success Response

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

### Error Response

```json
{
  "message": "Error seeding database: {error_details}",
  "error": true
}
```

## Seeded Entities

The seeder creates data for all entities in the following order (respecting foreign key dependencies):

### 1. Base/Lookup Tables

- **Roles**: 3 fixed roles (ROLE_CLIENT, ROLE_TRAINER, ROLE_AUDITOR)
- **Categories**: Exercise categories (Strength, Cardio, Flexibility, etc.)
- **Muscular Groups**: Muscle groups (Chest, Back, Legs, etc.)
- **Trainer Expertises**: Specializations (Bodybuilding, CrossFit, Yoga, etc.)
- **Training Plan Objectives**: Goals (Weight Loss, Muscle Gain, etc.)
- **Training Plan States**: 4 states (Active, Completed, Cancelled, On Hold)

### 2. User & People Entities

- **Clients**: Personal info + OneToOne User with CLIENT role
- **Trainers**: Personal info + OneToOne User with TRAINER role + ManyToMany Expertises
- **Auditors**: Personal info + OneToOne User with AUDITOR role

### 3. Exercise Management

- **Exercises**: Name, description, category FK, muscular group FK

### 4. Training Plans

- **Training Plans**: Client FK, trainer FK, objective FK, state FK, dates, description
- **Nutrition Plans**: Training plan FK, macros, calories
- **Physical Evaluations**: Client FK, trainer FK, measurements, BMI, body fat %

### 5. Routines & Sessions

- **Routines**: Training plan FK, name, description
- **Routine Exercises**: Routine FK, exercise FK, sets, reps, rest, weight
- **Sessions**: Routine FK, date, time, notes

### 6. Attendance Tracking

- **Attendances**: Client FK, session FK, status, completed flag
- **Attendance Exercises**: Attendance FK, routine exercise FK, completed flag

## Data Characteristics

### Generated User Credentials

- **Username**: Faker-generated with role suffix (e.g., `john_doe_trainer_123`)
- **Password**: All users have password `password123` (BCrypt encoded)
- **Email**: Faker-generated email addresses
- **Phone**: Faker-generated phone numbers

### Document Types

- Randomly assigned from enum: CC, TI, CE, PASSPORT, OTHER

### Numeric Ranges

- **Physical Evaluations**:
  - Weight: 50-130 kg
  - Height: 1.5-2.0 m
  - BMI: Calculated from weight/height
  - Body Fat: 10-35%
- **Nutrition Plans**:

  - Calories: 1500-3500
  - Protein: 80-200g
  - Carbs: 150-400g
  - Fat: 40-120g

- **Routine Exercises**:
  - Sets: 2-5
  - Reps: 6-15
  - Rest: 30-120 seconds
  - Target Weight: 20-120 kg (optional)

### Dates

- **Training Plans**: Start dates within last 180 days, end dates 30-120 days after start
- **Physical Evaluations**: Within last 365 days
- **Sessions**: Within last 90 days

## Usage Recommendations

### Development Environment

```bash
# Quick test with small dataset
POST /api/seeder/seed/small

# Standard development dataset
POST /api/seeder/seed/medium
```

### QA/Testing Environment

```bash
# Full dataset for comprehensive testing
POST /api/seeder/seed/large
```

### Demo Environment

```bash
# Large dataset for realistic demos
POST /api/seeder/seed/xlarge
```

### Performance Testing

```bash
# Maximum dataset (use with caution)
POST /api/seeder/seed?quantity=10000
```

## Important Notes

### ⚠️ Database Reset Warning

**The application is configured with `spring.jpa.hibernate.ddl-auto: create-drop`**

This means:

- Database schema is **DROPPED AND RECREATED** on every application restart
- All data is **PERMANENTLY DELETED** when the application stops
- Seeding must be performed **AFTER EVERY RESTART**

### Performance Considerations

- **Small (100)**: ~5-10 seconds
- **Medium (500)**: ~20-30 seconds
- **Large (1000)**: ~40-60 seconds
- **XLarge (5000)**: ~3-5 minutes
- **Max (10000)**: ~6-10 minutes

_Times vary based on hardware and database configuration_

### Limitations

1. **Maximum Quantity**: 10,000 records per entity (enforced for performance)
2. **Minimum Quantity**: 1 record per entity
3. **Role Required**: Only AUDITOR role can execute seeding
4. **Single Transaction**: All seeding in one transaction (rollback on error)
5. **No Duplicate Prevention**: Running seeder multiple times creates duplicates

## Troubleshooting

### Seeder Endpoint Returns 403 Forbidden

- Ensure you're authenticated as a user with AUDITOR role
- Check JWT token is valid and not expired
- Verify JWT cookie is being sent with request

### Seeder Takes Too Long

- Reduce the quantity parameter
- Use preset endpoints (small/medium) instead
- Check database connection performance

### Foreign Key Constraint Errors

- This shouldn't happen as seeding respects FK order
- If it does, report as a bug with full error stack trace

### Out of Memory Errors

- Reduce quantity to 5000 or less
- Increase JVM heap size: `-Xmx2g`

## Development Guide

### Modifying Seeder Logic

The `DatabaseSeeder.java` file contains separate methods for each entity type:

```java
private List<EntityType> seedEntityName(int quantity, List<Dependencies> deps) {
    // Seeding logic here
}
```

### Adding New Entity Seeding

1. Create new private method following naming pattern: `seedEntityName()`
2. Add repository autowiring at class level
3. Call method in `seedDatabase()` in correct FK dependency order
4. Update stats HashMap in response

### Custom Faker Data

JavaFaker provides many data generators:

- `faker.name()` - Names
- `faker.address()` - Addresses
- `faker.commerce()` - Products, departments
- `faker.company()` - Company names, buzzwords
- `faker.internet()` - Emails, URLs
- `faker.lorem()` - Lorem ipsum text
- `faker.number()` - Random numbers
- `faker.phoneNumber()` - Phone numbers
- `faker.date()` - Dates

See: [JavaFaker Documentation](https://github.com/DiUS/java-faker)

## Security Considerations

### Production Use

**DO NOT USE THIS SEEDER IN PRODUCTION**

This tool is for development/testing only because:

- Generates fake/invalid personal data
- All users share the same password
- No data validation beyond basic constraints
- Can create duplicate/inconsistent data

### Secure the Endpoint

The seeder is protected by:

1. Spring Security authentication requirement
2. AUDITOR role requirement via `@PreAuthorize`
3. Maximum quantity limit (10,000)

## Support

For issues or questions:

1. Check compilation errors: `.\mvnw clean compile`
2. Review application logs for detailed error messages
3. Verify database connection and credentials
4. Ensure all required entities/repositories exist

## License

Part of IronFit Backend project - internal use only
