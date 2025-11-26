package com.iron_fit.iron_fit_backend.core.utils;

import com.github.javafaker.Faker;
import com.iron_fit.iron_fit_backend.features.attendance.attendance_exercise.domain.entities.AttendanceExercise;
import com.iron_fit.iron_fit_backend.features.attendance.attendance_exercise.infrastructure.repository.AttendanceExerciseRepository;
import com.iron_fit.iron_fit_backend.features.attendance.domain.entities.AttendanceEntity;
import com.iron_fit.iron_fit_backend.features.attendance.domain.entities.AttendanceStatus;
import com.iron_fit.iron_fit_backend.features.attendance.infrastructure.repository.AttendanceRepository;
import com.iron_fit.iron_fit_backend.features.auth.domain.entities.RoleEntity;
import com.iron_fit.iron_fit_backend.features.auth.domain.entities.UserEntity;
import com.iron_fit.iron_fit_backend.features.auth.infrastructure.repositories.RoleRepository;
import com.iron_fit.iron_fit_backend.features.auth.infrastructure.repositories.UserRepository;
import com.iron_fit.iron_fit_backend.features.nutritional_plans.domain.entities.NutritionPlan;
import com.iron_fit.iron_fit_backend.features.nutritional_plans.infrastructure.repository.NutritionPlanRepository;
import com.iron_fit.iron_fit_backend.features.people_management.auditors.domain.entities.AuditorEntity;
import com.iron_fit.iron_fit_backend.features.people_management.auditors.infrastructure.repository.AuditorRepository;
import com.iron_fit.iron_fit_backend.features.people_management.clients.domain.entities.ClientEntity;
import com.iron_fit.iron_fit_backend.features.people_management.clients.infrastructure.repository.ClientRepository;
import com.iron_fit.iron_fit_backend.features.people_management.shared.enums.DocumentTypeEnum;
import com.iron_fit.iron_fit_backend.features.people_management.trainers.domain.entities.TrainerEntity;
import com.iron_fit.iron_fit_backend.features.people_management.trainers.infrastructure.repository.TrainerRepository;
import com.iron_fit.iron_fit_backend.features.physical_evaluations.domain.entities.PhysicalEvaluation;
import com.iron_fit.iron_fit_backend.features.physical_evaluations.infrastructure.repository.PhysicalEvaluationRepository;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.categories.domain.entities.CategoryEntity;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.categories.infrastructure.repository.CategoryRepository;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.exercises.domain.entities.Exercise;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.exercises.infrastructure.repository.ExerciseRepository;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.muscular_groups.domain.entities.MuscularGroupEntity;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.muscular_groups.infrastructure.repository.MuscularGroupRepository;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.routine_exercises.domain.entities.RoutineExercise;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.routine_exercises.infrastructure.repository.RoutineExerciseRepository;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.routines.domain.entities.Routine;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.routines.infrastructure.repository.RoutineRepository;
import com.iron_fit.iron_fit_backend.features.trainer_expertise.domain.entities.TrainerExpertise;
import com.iron_fit.iron_fit_backend.features.trainer_expertise.infrastructure.repository.TrainerExpertiseRepository;
import com.iron_fit.iron_fit_backend.features.trainment_plans.objectives.domain.entities.TrainmentPlanObjective;
import com.iron_fit.iron_fit_backend.features.trainment_plans.objectives.infrastructure.repository.TrainmentPlanObjectiveRepository;
import com.iron_fit.iron_fit_backend.features.trainment_plans.trainment_plan_state.domain.entities.TrainmentPlanState;
import com.iron_fit.iron_fit_backend.features.trainment_plans.trainment_plan_state.infrastructure.repository.TrainmentPlanStateRepository;
import com.iron_fit.iron_fit_backend.features.trainment_plans.trainment_plans.domain.entities.TrainmentPlan;
import com.iron_fit.iron_fit_backend.features.trainment_plans.trainment_plans.infrastructure.repository.TrainmentPlanRepository;
import com.iron_fit.iron_fit_backend.features.trainment_sessions.sessions.domain.entities.Session;
import com.iron_fit.iron_fit_backend.features.trainment_sessions.sessions.infrastructure.repository.SessionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class DatabaseSeeder {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private MuscularGroupRepository muscularGroupRepository;
    @Autowired
    private TrainerExpertiseRepository trainerExpertiseRepository;
    @Autowired
    private TrainmentPlanObjectiveRepository trainmentPlanObjectiveRepository;
    @Autowired
    private TrainmentPlanStateRepository trainmentPlanStateRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private TrainerRepository trainerRepository;
    @Autowired
    private AuditorRepository auditorRepository;
    @Autowired
    private ExerciseRepository exerciseRepository;
    @Autowired
    private TrainmentPlanRepository trainmentPlanRepository;
    @Autowired
    private RoutineRepository routineRepository;
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private RoutineExerciseRepository routineExerciseRepository;
    @Autowired
    private AttendanceRepository attendanceRepository;
    @Autowired
    private AttendanceExerciseRepository attendanceExerciseRepository;
    @Autowired
    private NutritionPlanRepository nutritionPlanRepository;
    @Autowired
    private PhysicalEvaluationRepository physicalEvaluationRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private final Faker faker = new Faker();
    private final Random random = new Random();

    @Transactional
    public HashMap<String, Object> seedDatabase(int quantity) {
        try {
            log.info("Starting database seeding with {} records per entity", quantity);
            long startTime = System.currentTimeMillis();

            // Step 1: Seed base/lookup tables first
            List<RoleEntity> roles = seedRoles();
            List<CategoryEntity> categories = seedCategories(quantity);
            List<MuscularGroupEntity> muscularGroups = seedMuscularGroups(quantity);
            List<TrainerExpertise> expertises = seedTrainerExpertises(quantity);
            List<TrainmentPlanObjective> objectives = seedTrainmentPlanObjectives(quantity);
            List<TrainmentPlanState> states = seedTrainmentPlanStates();

            // Step 2: Seed users and people entities
            List<ClientEntity> clients = seedClients(quantity, roles);
            List<TrainerEntity> trainers = seedTrainers(quantity, roles, expertises);
            List<AuditorEntity> auditors = seedAuditors(quantity, roles);

            // Step 3: Seed exercises (depends on categories and muscular groups)
            List<Exercise> exercises = seedExercises(quantity, categories, muscularGroups);

            // Step 4: Seed training plans (depends on clients, trainers, objectives,
            // states)
            List<TrainmentPlan> trainmentPlans = seedTrainmentPlans(quantity, clients, trainers, objectives, states);

            // Step 5: Seed nutrition plans and physical evaluations
            List<NutritionPlan> nutritionPlans = seedNutritionPlans(quantity, trainmentPlans);
            List<PhysicalEvaluation> physicalEvaluations = seedPhysicalEvaluations(quantity, clients, trainers);

            // Step 6: Seed routines (depends on training plans)
            List<Routine> routines = seedRoutines(quantity, trainmentPlans);

            // Step 7: Seed routine exercises (depends on routines and exercises)
            List<RoutineExercise> routineExercises = seedRoutineExercises(quantity, routines, exercises);

            // Step 8: Seed sessions (depends on routines)
            List<Session> sessions = seedSessions(quantity, routines);

            // Step 9: Seed attendance (depends on clients and sessions)
            List<AttendanceEntity> attendances = seedAttendances(quantity, clients, sessions);

            // Step 10: Seed attendance exercises (depends on attendances and routine
            // exercises)
            List<AttendanceExercise> attendanceExercises = seedAttendanceExercises(quantity, attendances,
                    routineExercises);

            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;

            log.info("Database seeding completed in {} ms", duration);

            return new HashMap<>() {
                {
                    put("message", "Database seeded successfully");
                    put("quantity", quantity);
                    put("duration_ms", duration);
                    put("stats", new HashMap<String, Integer>() {
                        {
                            put("roles", roles.size());
                            put("categories", categories.size());
                            put("muscular_groups", muscularGroups.size());
                            put("trainer_expertises", expertises.size());
                            put("objectives", objectives.size());
                            put("states", states.size());
                            put("clients", clients.size());
                            put("trainers", trainers.size());
                            put("auditors", auditors.size());
                            put("exercises", exercises.size());
                            put("training_plans", trainmentPlans.size());
                            put("nutrition_plans", nutritionPlans.size());
                            put("physical_evaluations", physicalEvaluations.size());
                            put("routines", routines.size());
                            put("routine_exercises", routineExercises.size());
                            put("sessions", sessions.size());
                            put("attendances", attendances.size());
                            put("attendance_exercises", attendanceExercises.size());
                        }
                    });
                }
            };

        } catch (Exception e) {
            log.error("Error seeding database: {}", e.getMessage(), e);
            return new HashMap<>() {
                {
                    put("message", "Error seeding database: " + e.getMessage());
                    put("error", true);
                }
            };
        }
    }

    private List<RoleEntity> seedRoles() {
        log.info("Seeding roles...");
        List<RoleEntity> roles = new ArrayList<>();

        String[] roleNames = { "ROLE_CLIENT", "ROLE_TRAINER", "ROLE_AUDITOR" };

        for (String roleName : roleNames) {
            if (roleRepository.findByName(roleName).isEmpty()) {
                RoleEntity role = RoleEntity.builder()
                        .name(roleName)
                        .build();
                roles.add(roleRepository.save(role));
            }
        }

        return roleRepository.findAll();
    }

    private List<CategoryEntity> seedCategories(int quantity) {
        log.info("Seeding {} categories...", quantity);
        List<CategoryEntity> categories = new ArrayList<>();

        String[] categoryNames = { "Strength", "Cardio", "Flexibility", "Balance", "Functional",
                "Plyometric", "Olympic Lifts", "Bodyweight", "Resistance", "Core" };

        for (int i = 0; i < quantity; i++) {
            CategoryEntity category = CategoryEntity.builder()
                    .name(i < categoryNames.length ? categoryNames[i] : faker.commerce().department() + " Category")
                    .build();
            categories.add(categoryRepository.save(category));
        }

        return categories;
    }

    private List<MuscularGroupEntity> seedMuscularGroups(int quantity) {
        log.info("Seeding {} muscular groups...", quantity);
        List<MuscularGroupEntity> groups = new ArrayList<>();

        String[] muscleNames = { "Chest", "Back", "Shoulders", "Biceps", "Triceps", "Quadriceps",
                "Hamstrings", "Calves", "Abs", "Glutes", "Forearms", "Traps" };

        for (int i = 0; i < quantity; i++) {
            MuscularGroupEntity group = MuscularGroupEntity.builder()
                    .name(i < muscleNames.length ? muscleNames[i] : faker.medical().symptoms())
                    .build();
            groups.add(muscularGroupRepository.save(group));
        }

        return groups;
    }

    private List<TrainerExpertise> seedTrainerExpertises(int quantity) {
        log.info("Seeding {} trainer expertises...", quantity);
        List<TrainerExpertise> expertises = new ArrayList<>();

        String[] expertiseNames = { "Bodybuilding", "CrossFit", "Powerlifting", "Yoga", "Pilates",
                "Functional Training", "Sports Performance", "Rehabilitation",
                "Weight Loss", "Nutrition" };

        for (int i = 0; i < quantity; i++) {
            TrainerExpertise expertise = TrainerExpertise.builder()
                    .name(i < expertiseNames.length ? expertiseNames[i] : faker.job().field())
                    .build();
            expertises.add(trainerExpertiseRepository.save(expertise));
        }

        return expertises;
    }

    private List<TrainmentPlanObjective> seedTrainmentPlanObjectives(int quantity) {
        log.info("Seeding {} training plan objectives...", quantity);
        List<TrainmentPlanObjective> objectives = new ArrayList<>();

        String[] objectiveNames = { "Weight Loss", "Muscle Gain", "Strength", "Endurance",
                "Flexibility", "Athletic Performance", "Rehabilitation",
                "General Fitness", "Competition Prep" };

        for (int i = 0; i < quantity; i++) {
            TrainmentPlanObjective objective = TrainmentPlanObjective.builder()
                    .name(i < objectiveNames.length ? objectiveNames[i] : faker.company().bs())
                    .build();
            objectives.add(trainmentPlanObjectiveRepository.save(objective));
        }

        return objectives;
    }

    private List<TrainmentPlanState> seedTrainmentPlanStates() {
        log.info("Seeding training plan states...");
        List<TrainmentPlanState> states = new ArrayList<>();

        String[] stateNames = { "Active", "Completed", "Cancelled", "On Hold" };

        for (String stateName : stateNames) {
            TrainmentPlanState state = TrainmentPlanState.builder()
                    .name(stateName)
                    .build();
            states.add(trainmentPlanStateRepository.save(state));
        }

        return states;
    }

    private List<ClientEntity> seedClients(int quantity, List<RoleEntity> roles) {
        log.info("Seeding {} clients...", quantity);
        List<ClientEntity> clients = new ArrayList<>();

        RoleEntity clientRole = roles.stream()
                .filter(r -> r.getName().equals("ROLE_CLIENT"))
                .findFirst()
                .orElseThrow();

        for (int i = 0; i < quantity; i++) {
            UserEntity user = UserEntity.builder()
                    .username(faker.name().username() + "_" + i)
                    .password(passwordEncoder.encode("password123"))
                    .roleEntities(Set.of(clientRole))
                    .isEnabled(true)
                    .isAccountNoExpired(true)
                    .isCredentialNoExpired(true)
                    .isAccountNoLocked(true)
                    .build();

            ClientEntity client = ClientEntity.builder()
                    .firstName(faker.name().firstName())
                    .lastName(faker.name().lastName())
                    .documentType(DocumentTypeEnum.values()[random.nextInt(DocumentTypeEnum.values().length)])
                    .documentNumber(faker.number().digits(10))
                    .email(faker.internet().emailAddress())
                    .phoneNumber(faker.phoneNumber().phoneNumber())
                    .user(user)
                    .build();

            clients.add(clientRepository.save(client));
        }

        return clients;
    }

    private List<TrainerEntity> seedTrainers(int quantity, List<RoleEntity> roles, List<TrainerExpertise> expertises) {
        log.info("Seeding {} trainers...", quantity);
        List<TrainerEntity> trainers = new ArrayList<>();

        RoleEntity trainerRole = roles.stream()
                .filter(r -> r.getName().equals("ROLE_TRAINER"))
                .findFirst()
                .orElseThrow();

        for (int i = 0; i < quantity; i++) {
            UserEntity user = UserEntity.builder()
                    .username(faker.name().username() + "_trainer_" + i)
                    .password(passwordEncoder.encode("password123"))
                    .roleEntities(Set.of(trainerRole))
                    .isEnabled(true)
                    .isAccountNoExpired(true)
                    .isCredentialNoExpired(true)
                    .isAccountNoLocked(true)
                    .build();

            // Assign 1-3 random expertises
            Set<TrainerExpertise> trainerExpertises = new HashSet<>();
            int expertiseCount = random.nextInt(3) + 1;
            for (int j = 0; j < expertiseCount && !expertises.isEmpty(); j++) {
                trainerExpertises.add(expertises.get(random.nextInt(expertises.size())));
            }

            TrainerEntity trainer = TrainerEntity.builder()
                    .firstName(faker.name().firstName())
                    .lastName(faker.name().lastName())
                    .documentType(DocumentTypeEnum.values()[random.nextInt(DocumentTypeEnum.values().length)])
                    .documentNumber(faker.number().digits(10))
                    .email(faker.internet().emailAddress())
                    .phoneNumber(faker.phoneNumber().phoneNumber())
                    .user(user)
                    .expertises(trainerExpertises)
                    .build();

            trainers.add(trainerRepository.save(trainer));
        }

        return trainers;
    }

    private List<AuditorEntity> seedAuditors(int quantity, List<RoleEntity> roles) {
        log.info("Seeding {} auditors...", quantity);
        List<AuditorEntity> auditors = new ArrayList<>();

        RoleEntity auditorRole = roles.stream()
                .filter(r -> r.getName().equals("ROLE_AUDITOR"))
                .findFirst()
                .orElseThrow();

        for (int i = 0; i < quantity; i++) {
            UserEntity user = UserEntity.builder()
                    .username(faker.name().username() + "_auditor_" + i)
                    .password(passwordEncoder.encode("password123"))
                    .roleEntities(Set.of(auditorRole))
                    .isEnabled(true)
                    .isAccountNoExpired(true)
                    .isCredentialNoExpired(true)
                    .isAccountNoLocked(true)
                    .build();

            AuditorEntity auditor = AuditorEntity.builder()
                    .firstName(faker.name().firstName())
                    .lastName(faker.name().lastName())
                    .documentType(DocumentTypeEnum.values()[random.nextInt(DocumentTypeEnum.values().length)])
                    .documentNumber(faker.number().digits(10))
                    .email(faker.internet().emailAddress())
                    .phoneNumber(faker.phoneNumber().phoneNumber())
                    .user(user)
                    .build();

            auditors.add(auditorRepository.save(auditor));
        }

        return auditors;
    }

    private List<Exercise> seedExercises(int quantity, List<CategoryEntity> categories,
            List<MuscularGroupEntity> muscularGroups) {
        log.info("Seeding {} exercises...", quantity);
        List<Exercise> exercises = new ArrayList<>();

        String[] exerciseNames = { "Bench Press", "Squat", "Deadlift", "Pull-up", "Push-up",
                "Bicep Curl", "Tricep Extension", "Leg Press", "Shoulder Press",
                "Lat Pulldown", "Chest Fly", "Lunges", "Plank", "Crunch" };

        for (int i = 0; i < quantity; i++) {
            Exercise exercise = Exercise.builder()
                    .name(i < exerciseNames.length ? exerciseNames[i]
                            : faker.commerce().productName() + " Exercise " + i)
                    .description(faker.lorem().sentence(10))
                    .category(categories.get(random.nextInt(categories.size())))
                    .muscularGroup(muscularGroups.get(random.nextInt(muscularGroups.size())))
                    .build();

            exercises.add(exerciseRepository.save(exercise));
        }

        return exercises;
    }

    private List<TrainmentPlan> seedTrainmentPlans(int quantity, List<ClientEntity> clients,
            List<TrainerEntity> trainers,
            List<TrainmentPlanObjective> objectives,
            List<TrainmentPlanState> states) {
        log.info("Seeding {} training plans...", quantity);
        List<TrainmentPlan> plans = new ArrayList<>();

        for (int i = 0; i < quantity; i++) {
            LocalDate startDate = faker.date().past(180, TimeUnit.DAYS).toInstant()
                    .atZone(java.time.ZoneId.systemDefault()).toLocalDate();
            LocalDate endDate = startDate.plusDays(random.nextInt(90) + 30);

            TrainmentPlan plan = TrainmentPlan.builder()
                    .name(faker.company().buzzword() + " Training Plan " + i)
                    .description(faker.lorem().paragraph())
                    .client(clients.get(random.nextInt(clients.size())))
                    .trainer(trainers.get(random.nextInt(trainers.size())))
                    .objective(objectives.get(random.nextInt(objectives.size())))
                    .state(states.get(random.nextInt(states.size())))
                    .startDate(startDate)
                    .endDate(endDate)
                    .build();

            plans.add(trainmentPlanRepository.save(plan));
        }

        return plans;
    }

    private List<NutritionPlan> seedNutritionPlans(int quantity, List<TrainmentPlan> trainmentPlans) {
        log.info("Seeding {} nutrition plans...", quantity);
        List<NutritionPlan> nutritionPlans = new ArrayList<>();

        for (int i = 0; i < Math.min(quantity, trainmentPlans.size()); i++) {
            NutritionPlan plan = NutritionPlan.builder()
                    .trainmentPlan(trainmentPlans.get(i))
                    .calories(BigDecimal.valueOf(1500 + random.nextInt(2000)))
                    .proteinGrams(BigDecimal.valueOf(80 + random.nextInt(120)))
                    .carbsGrams(BigDecimal.valueOf(150 + random.nextInt(250)))
                    .fatGrams(BigDecimal.valueOf(40 + random.nextInt(80)))
                    .description(faker.lorem().paragraph())
                    .build();

            nutritionPlans.add(nutritionPlanRepository.save(plan));
        }

        return nutritionPlans;
    }

    private List<PhysicalEvaluation> seedPhysicalEvaluations(int quantity, List<ClientEntity> clients,
            List<TrainerEntity> trainers) {
        log.info("Seeding {} physical evaluations...", quantity);
        List<PhysicalEvaluation> evaluations = new ArrayList<>();

        for (int i = 0; i < quantity; i++) {
            double weight = 50 + random.nextDouble() * 80; // 50-130 kg
            double height = 1.5 + random.nextDouble() * 0.5; // 1.5-2.0 m
            double bmi = weight / (height * height);

            PhysicalEvaluation evaluation = PhysicalEvaluation.builder()
                    .client(clients.get(random.nextInt(clients.size())))
                    .trainer(trainers.get(random.nextInt(trainers.size())))
                    .evaluationDate(faker.date().past(365, TimeUnit.DAYS).toInstant()
                            .atZone(java.time.ZoneId.systemDefault()).toLocalDate())
                    .weight(BigDecimal.valueOf(weight))
                    .bmi(BigDecimal.valueOf(bmi))
                    .bodyFatPercentage(BigDecimal.valueOf(10 + random.nextDouble() * 25))
                    .heightMeasurement(BigDecimal.valueOf(height))
                    .waistMeasurement(BigDecimal.valueOf(60 + random.nextDouble() * 40))
                    .hipMeasurement(BigDecimal.valueOf(80 + random.nextDouble() * 40))
                    .notes(faker.lorem().paragraph())
                    .build();

            evaluations.add(physicalEvaluationRepository.save(evaluation));
        }

        return evaluations;
    }

    private List<Routine> seedRoutines(int quantity, List<TrainmentPlan> trainmentPlans) {
        log.info("Seeding {} routines...", quantity);
        List<Routine> routines = new ArrayList<>();

        String[] routineTypes = { "Push", "Pull", "Legs", "Upper Body", "Lower Body",
                "Full Body", "Cardio", "Core", "HIIT" };

        for (int i = 0; i < quantity; i++) {
            Routine routine = Routine.builder()
                    .name(routineTypes[random.nextInt(routineTypes.length)] + " Routine " + i)
                    .description(faker.lorem().sentence(15))
                    .trainmentPlan(trainmentPlans.get(random.nextInt(trainmentPlans.size())))
                    .build();

            routines.add(routineRepository.save(routine));
        }

        return routines;
    }

    private List<RoutineExercise> seedRoutineExercises(int quantity, List<Routine> routines,
            List<Exercise> exercises) {
        log.info("Seeding {} routine exercises...", quantity);
        List<RoutineExercise> routineExercises = new ArrayList<>();

        for (int i = 0; i < quantity; i++) {
            RoutineExercise routineExercise = RoutineExercise.builder()
                    .routine(routines.get(random.nextInt(routines.size())))
                    .exercise(exercises.get(random.nextInt(exercises.size())))
                    .order(i % 10 + 1)
                    .sets(2 + random.nextInt(4)) // 2-5 sets
                    .reps(6 + random.nextInt(10)) // 6-15 reps
                    .timeSeconds(random.nextBoolean() ? 30 + random.nextInt(60) : null)
                    .restSeconds(30 + random.nextInt(90)) // 30-120 seconds
                    .targetWeight(random.nextBoolean() ? 20.0 + random.nextDouble() * 100 : null)
                    .build();

            routineExercises.add(routineExerciseRepository.save(routineExercise));
        }

        return routineExercises;
    }

    private List<Session> seedSessions(int quantity, List<Routine> routines) {
        log.info("Seeding {} sessions...", quantity);
        List<Session> sessions = new ArrayList<>();

        for (int i = 0; i < quantity; i++) {
            LocalDate sessionDate = faker.date().past(90, TimeUnit.DAYS).toInstant()
                    .atZone(java.time.ZoneId.systemDefault()).toLocalDate();

            Session session = Session.builder()
                    .routine(routines.get(random.nextInt(routines.size())))
                    .startDate(sessionDate)
                    .startTime(LocalTime.of(6 + random.nextInt(16), random.nextInt(60))) // 6am-10pm
                    .notes(faker.lorem().sentence(20))
                    .build();

            sessions.add(sessionRepository.save(session));
        }

        return sessions;
    }

    private List<AttendanceEntity> seedAttendances(int quantity, List<ClientEntity> clients,
            List<Session> sessions) {
        log.info("Seeding {} attendances...", quantity);
        List<AttendanceEntity> attendances = new ArrayList<>();

        AttendanceStatus[] statuses = AttendanceStatus.values();

        for (int i = 0; i < quantity; i++) {
            AttendanceStatus status = statuses[random.nextInt(statuses.length)];

            AttendanceEntity attendance = AttendanceEntity.builder()
                    .client(clients.get(random.nextInt(clients.size())))
                    .session(sessions.get(random.nextInt(sessions.size())))
                    .status(status)
                    .completed(status == AttendanceStatus.ATTENDED && random.nextBoolean())
                    .observations(faker.lorem().sentence(10))
                    .build();

            attendances.add(attendanceRepository.save(attendance));
        }

        return attendances;
    }

    private List<AttendanceExercise> seedAttendanceExercises(int quantity,
            List<AttendanceEntity> attendances,
            List<RoutineExercise> routineExercises) {
        log.info("Seeding {} attendance exercises...", quantity);
        List<AttendanceExercise> attendanceExercises = new ArrayList<>();

        for (int i = 0; i < quantity; i++) {
            AttendanceExercise attendanceExercise = AttendanceExercise.builder()
                    .attendance(attendances.get(random.nextInt(attendances.size())))
                    .routineExercise(routineExercises.get(random.nextInt(routineExercises.size())))
                    .completed(random.nextBoolean())
                    .notes(random.nextBoolean() ? faker.lorem().sentence(8) : null)
                    .build();

            attendanceExercises.add(attendanceExerciseRepository.save(attendanceExercise));
        }

        return attendanceExercises;
    }
}
