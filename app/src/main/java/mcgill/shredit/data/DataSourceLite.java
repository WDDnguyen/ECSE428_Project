package mcgill.shredit.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mcgill.shredit.model.Equipment;
import mcgill.shredit.model.Exercise;
import mcgill.shredit.model.Gym;
import mcgill.shredit.model.Workout;

public class DataSourceLite extends SQLiteOpenHelper implements DataSource {

    private static final String EQUIPMENT_TABLE = "Equipment";
    private static final String EQUIPMENT_NAME = "eq_name";

    private static final String EXERCISE_TABLE = "Exercises";
    private static final String EXERCISE_NAME = "ex_name";
    private static final String EXERCISE_DESCRIPTION = "description";
    private static final String EXERCISE_MUSCLE_GROUP = "musclegroup";

    private static final String GYM_TABLE = "Gyms";
    private static final String GYM_NAME = "g_name";
    private static final String GYM_EQUIPMENT_TABLE = "GymEquipment";

    private static final String WORKOUT_TABLE = "Workouts";
    private static final String WORKOUT_NAME = "w_name";
    private static final String WORKOUT_EXERCISE_TABLE = "WorkoutExercises";

    private static final String USER_TABLE = "Users";
    private static final String USER_USERNAME = "username";
    private static final String USER_PASSWORD = "password";

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "shredit.db";

    public DataSourceLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE Users(\n" +
                "  username VARCHAR(255) NOT NULL,\n" +
                "  password VARCHAR(255) NOT NULL,\n" +
                "  PRIMARY KEY (username));");

        db.execSQL("CREATE TABLE Equipment(\n" +
                "  eq_name VARCHAR(255) NOT NULL,\n" +
                "  PRIMARY KEY (eq_name)\n" +
                ");");

        db.execSQL("CREATE TABLE Exercises(\n" +
                "  ex_name VARCHAR(255) NOT NULL,\n" +
                "  description VARCHAR(255) NOT NULL,\n" +
                "  muscleGroup VARCHAR(255) NOT NULL,\n" +
                "  eq_name VARCHAR(255) NOT NULL REFERENCES Equipment(eq_name),\n" +
                "  PRIMARY KEY (ex_name)\n" +
                ");");

        db.execSQL("CREATE TABLE Gyms(\n" +
                "  g_name VARCHAR(255) NOT NULL,\n" +
                "  username VARCHAR(255) NOT NULL REFERENCES Users(username),\n" +
                "  PRIMARY KEY (g_name, username)\n" +
                ");");

        db.execSQL("CREATE TABLE GymEquipment(\n" +
                "  g_name VARCHAR(255) NOT NULL,\n" +
                "  username VARCHAR(255) NOT NULL,\n" +
                "  eq_name VARCHAR(255) NOT NULL REFERENCES Equipment(eq_name),\n" +
                "  FOREIGN KEY (g_name, username) REFERENCES Gyms(g_name, username),\n" +
                "  PRIMARY KEY (g_name, username, eq_name)\n" +
                ");");

        db.execSQL("CREATE TABLE Workouts(\n" +
                "  w_name VARCHAR(255) NOT NULL,\n" +
                "  username VARCHAR(255) NOT NULL REFERENCES Users(username),\n" +
                "  PRIMARY KEY (w_name, username)\n" +
                ");");

        db.execSQL("CREATE TABLE WorkoutExercises(\n" +
                "  w_name VARCHAR(255) NOT NULL,\n" +
                "  username VARCHAR(255) NOT NULL,\n" +
                "  ex_name VARCHAR(255) NOT NULL REFERENCES Exercises(ex_name),\n" +
                "  FOREIGN KEY (w_name, username) REFERENCES Workouts(w_name, username),\n" +
                "  PRIMARY KEY (w_name, username, ex_name)\n" +
                ");");

        db.execSQL("INSERT INTO Users(username, password)\n" +
                "VALUES ('User1', 'password123');");

        db.execSQL("INSERT INTO Equipment(eq_name)\n" +
                "VALUES ('Dumbbells'),\n" +
                " ('Bench'),\n" +
                " ('Stationary Bike');");

        db.execSQL("INSERT INTO Exercises(ex_name, description, muscleGroup, eq_name)\n" +
                "VALUES ('Dumbbell curls', 'Lift a dumbbell', 'Biceps', 'Dumbbells'),\n" +
                " ('Dumbbell flies', 'Lift a dumbbell', 'Chest', 'Dumbbells'),\n" +
                " ('Bench press', 'Lift a barbell', 'Chest', 'Bench');");

        db.execSQL("INSERT INTO Gyms(g_name, username)\n" +
                "VALUES ('myGym', 'User1');");

        db.execSQL("INSERT INTO GymEquipment(g_name, username, eq_name)\n" +
                "VALUES ('myGym', 'User1', 'Dumbbells');");

        db.execSQL("INSERT INTO Workouts(w_name, username)\n" +
                "VALUES ('myWorkout', 'User1');");

        db.execSQL("INSERT INTO WorkoutExercises(w_name, username, ex_name)\n" +
                "VALUES ('myWorkout', 'User1', 'Dumbbell curls'),\n" +
                " ('myWorkout', 'User1', 'Dumbbell flies');");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}


    @Override
    public List<Equipment> getEquipmentList() {
        ArrayList<Equipment> equipmentList= new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor c = null;

        String query = String.format("SELECT DISTINCT %s FROM %s;",
                EQUIPMENT_NAME, EQUIPMENT_TABLE);

        try {
            db = this.getWritableDatabase();
            c = db.rawQuery(query, null);
            if (c.moveToFirst()) {
                do {
                    equipmentList.add(new Equipment(c.getString(c.getColumnIndex(EQUIPMENT_NAME))));
                } while (c.moveToNext());
            }
        } catch (Exception e) {System.out.println(e.getMessage());}
        finally {
            c.close();
            db.close();
            return equipmentList;
        }
    }

    @Override
    public List<Exercise> getExerciseList(String muscleGroup, String username, String gymName) {
        ArrayList<Exercise> exerciseList= new ArrayList<>();
        HashMap<String, Equipment> equipmentSet = new HashMap<>();

        String query = String.format("SELECT DISTINCT %s, %s, %s, ex_t.%s\n"
                        + "FROM %s AS ex_t\n"
                        +"INNER JOIN %s AS eq_t ON ex_t.%s=eq_t.%s",
                EXERCISE_NAME, EXERCISE_DESCRIPTION, EXERCISE_MUSCLE_GROUP, EQUIPMENT_NAME,
                EXERCISE_TABLE,
                EQUIPMENT_TABLE, EQUIPMENT_NAME, EQUIPMENT_NAME);

        if(username != null && gymName != null) {
            query += String.format("\nINNER JOIN %s AS ge_t ON eq_t.%s=ge_t.%s",
                    GYM_EQUIPMENT_TABLE, EQUIPMENT_NAME, EQUIPMENT_NAME);
        }

        if (muscleGroup != null) {
            query += String.format("\nWHERE %s='%s'", EXERCISE_MUSCLE_GROUP, muscleGroup);
            if (username != null && gymName != null) {
                query += String.format(" AND %s='%s' AND %s='%s'",
                        GYM_NAME, gymName, USER_USERNAME, username);
            }

        } else if (username != null && gymName != null) {
            query += String.format("\nWHERE %s='%s' AND %s='%s'",
                    GYM_NAME, gymName, USER_USERNAME, username);
        }

        query += ";";

        SQLiteDatabase db = null;
        Cursor c = null;

        try {
            db = this.getWritableDatabase();
            c = db.rawQuery(query, null);
            if (c.moveToFirst()) {
                 do {
                    if (!equipmentSet.containsKey(c.getString(c.getColumnIndex(EQUIPMENT_NAME)))) {
                        equipmentSet.put(c.getString(c.getColumnIndex(EQUIPMENT_NAME)), new Equipment(
                                c.getString(c.getColumnIndex(EQUIPMENT_NAME))));
                    }
                    exerciseList.add(new Exercise(
                            c.getString(c.getColumnIndex(EXERCISE_NAME)),
                            c.getString(c.getColumnIndex(EXERCISE_DESCRIPTION)),
                            c.getString(c.getColumnIndex(EXERCISE_MUSCLE_GROUP)),
                            equipmentSet.get(c.getString(c.getColumnIndex(EQUIPMENT_NAME)))));
                } while (c.moveToNext());
            }
        } catch (Exception e) {System.out.println(e.getMessage());}
        finally {
            c.close();
            db.close();
            return exerciseList;
        }
    }

    public List<Gym> getGymList(String username) {

        String query = String.format("SELECT DISTINCT eq_t.%s, ge_t.%s\n"
                        + "FROM %s AS eq_t\n"
                        + "INNER JOIN %s AS ge_t ON eq_t.%s=ge_t.%s\n"
                        + "INNER JOIN %s AS g_t ON ge_t.%s=g_t.%s AND ge_t.%s=g_t.%s\n"
                        + "WHERE ge_t.%s='%s';",
                EQUIPMENT_NAME, GYM_NAME,
                EXERCISE_TABLE,
                GYM_EQUIPMENT_TABLE, EQUIPMENT_NAME, EQUIPMENT_NAME,
                GYM_TABLE, GYM_NAME, GYM_NAME, USER_USERNAME, USER_USERNAME,
                USER_USERNAME, username);

        HashMap<String, Gym> gymSet = new HashMap<>();
        HashMap<String, Equipment> equipmentSet = new HashMap<>();
        ArrayList<Gym> gymList = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor c = null;

        try {
            
            db = this.getWritableDatabase();
            c = db.rawQuery(query, null);
            if (c.moveToFirst()) {
                do {
                    String equipmentName = c.getString(c.getColumnIndex(EQUIPMENT_NAME));
                    String gymName = c.getString(c.getColumnIndex(GYM_NAME));
                    if (!equipmentSet.containsKey(equipmentName)) {
                        equipmentSet.put(equipmentName, new Equipment(
                                equipmentName));
                    }
                    if (!gymSet.containsKey(gymName)) {
                        Gym newGym = new Gym(gymName);
                        gymSet.put(gymName, newGym);
                        gymList.add(newGym);
                    }
                    gymSet.get(gymName).addEquipment(equipmentSet.get(equipmentName));
                } while (c.moveToNext());
            }
        } catch (Exception e) {System.out.println(e.getMessage());}
        finally {
            c.close();
            db.close();
            return gymList;
        }
    }

    public List<Workout> getWorkoutList(String username) {
        String query = String.format("SELECT DISTINCT eq_t.%s, ex_t.%s, %s, %s, w_t.%s\n"
                        + "FROM %s AS eq_t\n"
                        + "INNER JOIN %s AS ex_t ON eq_t.%s=ex_t.%s\n"
                        + "INNER JOIN %s AS we_t ON ex_t.%s=we_t.%s\n"
                        + "INNER JOIN %s AS w_t ON we_t.%s=w_t.%s AND we_t.%s=w_t.%s\n"
                        + "WHERE w_t.%s='%s';",
                EQUIPMENT_NAME, EXERCISE_NAME, EXERCISE_DESCRIPTION, EXERCISE_MUSCLE_GROUP, WORKOUT_NAME,
                EQUIPMENT_TABLE,
                EXERCISE_TABLE, EQUIPMENT_NAME, EQUIPMENT_NAME,
                WORKOUT_EXERCISE_TABLE, EXERCISE_NAME, EXERCISE_NAME,
                WORKOUT_TABLE, WORKOUT_NAME, WORKOUT_NAME, USER_USERNAME, USER_USERNAME,
                USER_USERNAME, username);

        HashMap<String, Workout> workoutSet = new HashMap<>();
        HashMap<String, Exercise> exerciseSet = new HashMap<>();
        HashMap<String, Equipment> equipmentSet = new HashMap<>();
        ArrayList<Workout> workoutList = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor c = null;

        try {
            
            db = this.getWritableDatabase();
            c = db.rawQuery(query, null);
            if (c.moveToFirst()) {
                do {
                    String workoutName = c.getString(c.getColumnIndex(WORKOUT_NAME));
                    String exerciseName = c.getString(c.getColumnIndex(EQUIPMENT_NAME));
                    String equipmentName = c.getString(c.getColumnIndex(EQUIPMENT_NAME));
                    if (!equipmentSet.containsKey(equipmentName)) {
                        equipmentSet.put(equipmentName, new Equipment(
                                c.getString(c.getColumnIndex(EQUIPMENT_NAME))));
                    }
                    if (!exerciseSet.containsKey(exerciseName)) {
                        exerciseSet.put(exerciseName, new Exercise(
                                c.getString(c.getColumnIndex(EXERCISE_NAME)),
                                c.getString(c.getColumnIndex(EXERCISE_DESCRIPTION)),
                                c.getString(c.getColumnIndex(EXERCISE_MUSCLE_GROUP)),
                                equipmentSet.get(equipmentName)));
                    }
                    if (!workoutSet.containsKey(workoutName)) {
                        Workout newWorkout = new Workout(c.getString(c.getColumnIndex(WORKOUT_NAME)));
                        workoutSet.put(workoutName, newWorkout);
                        workoutList.add(newWorkout);
                    }
                    workoutSet.get(workoutName).addExercise(exerciseSet.get(exerciseName));
                } while (c.moveToNext());
            }
        } catch (Exception e) {System.out.println(e.getMessage());}
        finally {
            c.close();
            db.close();
            return workoutList;
        }
    }

    public boolean checkPassword(String username, String password) {
        SQLiteDatabase db = null;
        Cursor c = null;
        String query = String.format("SELECT DISTINCT %s FROM %s WHERE %s='%s';",
                USER_PASSWORD, USER_TABLE, USER_USERNAME, username);
        String truePassword = null;

        try {
            
            db = this.getWritableDatabase();
            c = db.rawQuery(query, null);
            if (c.moveToFirst()) {
                truePassword = c.getString(c.getColumnIndex(USER_PASSWORD));
            }
        } catch (Exception e) {System.out.println(e.getMessage());}
        finally {
            c.close();
            db.close();
            return truePassword != null && truePassword.equals(password);
        }
    }

    @Override
    public boolean addUser(String username, String password) {
        removeUser(username);
        SQLiteDatabase db = null;
        String query = String.format("INSERT INTO %s(%s,%s)\n"
                        + "VALUES ('%s','%s');",
                USER_TABLE, USER_USERNAME, USER_PASSWORD,
                username, password);

        boolean success = false;

        try {
            
            db = this.getWritableDatabase();
            db.rawQuery(query, null);
            success = true;
        } catch (Exception e) {System.out.println(e.getMessage());}
        finally {
            db.close();
        }
        return success;
    }

    @Override
    public boolean removeUser(String username) {
        SQLiteDatabase db = null;
        String query = String.format("DELETE FROM %s\n"
                        + "WHERE %s='%s';",
                USER_TABLE,
                USER_USERNAME, username);
        boolean success = false;

        try {
            
            db = this.getWritableDatabase();
            db.rawQuery(query, null);
            success = true;
        } catch (Exception e) {System.out.println(e.getMessage());}
        finally {
            db.close();
        }
        return success;
    }

    @Override
    public boolean addEquipment(Equipment equipment) {
        removeEquipment(equipment);
        SQLiteDatabase db = null;
        String query = String.format("INSERT INTO %s(%s)\n"
                        + "VALUES ('%s');",
                EQUIPMENT_TABLE, EQUIPMENT_NAME,
                equipment.getName());


        boolean success = false;

        try {
            
            db = this.getWritableDatabase();
            db.rawQuery(query, null);
            success = true;
        } catch (Exception e) {System.out.println(e.getMessage());}
        finally {
            db.close();
        }
        return success;
    }

    @Override
    public boolean removeEquipment(Equipment equipment) {
        SQLiteDatabase db = null;
        String query = String.format("DELETE FROM %s\n"
                        + "WHERE %s='%s';",
                EQUIPMENT_TABLE,
                EQUIPMENT_NAME, equipment.getName());
        boolean success = false;

        try {
            
            db = this.getWritableDatabase();
            db.rawQuery(query, null);
            success = true;
        } catch (Exception e) {System.out.println(e.getMessage());}
        finally {
            db.close();
        }
        return success;
    }

    @Override
    public boolean addExercise(Exercise exercise) {
        removeExercise(exercise);
        SQLiteDatabase db = null;
        Cursor c = null;
        String query = String.format("INSERT INTO %s(%s,%s,%s,%s)\n"
                        + "VALUES ('%s','%s','%s','%s');",
                EXERCISE_TABLE, EXERCISE_NAME, EXERCISE_DESCRIPTION, EXERCISE_MUSCLE_GROUP,
                EQUIPMENT_NAME,
                exercise.getName(), exercise.getDescription(), exercise.getMuscleGroup(),
                exercise.getEquipment().getName());


        boolean success = false;

        try {
            
            db = this.getWritableDatabase();
            db.rawQuery(query, null);
            success = true;
        } catch (Exception e) {System.out.println(e.getMessage());}
        finally {
            db.close();
        }
        return success;
    }

    @Override
    public boolean removeExercise(Exercise exercise) {
        SQLiteDatabase db = null;
        String query = String.format("DELETE FROM %s\n"
                        + "WHERE %s='%s';",
                EXERCISE_TABLE,
                EXERCISE_NAME, exercise.getName());
        boolean success = false;

        try {
            
            db = this.getWritableDatabase();
            db.rawQuery(query, null);
            success = true;
        } catch (Exception e) {System.out.println(e.getMessage());}
        finally {
            db.close();
        }
        return success;
    }

    @Override
    public boolean addGym(String username, Gym gym) {
        removeGym(username, gym);
        SQLiteDatabase db = null;
        StringBuilder query = new StringBuilder(String.format("INSERT INTO %s(%s,%s)\n"
                        + "VALUES ('%s','%s');\n",
                GYM_TABLE, GYM_NAME, USER_USERNAME,
                gym.getName(), username));
        if (!gym.getEquipments().isEmpty()) {
            query.append(String.format("INSERT INTO %s(%s,%s,%s)\n"
                            + "VALUES",
                    GYM_EQUIPMENT_TABLE, GYM_NAME, USER_USERNAME, EQUIPMENT_NAME));
            for (Equipment equipment : gym.getEquipments()) {
                query.append(String.format(" ('%s','%s','%s'),\n",
                        gym.getName(), username, equipment.getName()));
            }
            query.replace(query.length() - 2, query.length(), ";");
        }
        boolean success = false;

        try {
            
            db = this.getWritableDatabase();
            db.rawQuery(query.toString(), null);
            success = true;
        } catch (Exception e) {System.out.println(e.getMessage());}
        finally {
            db.close();
        }
        return success;
    }

    @Override
    public boolean removeGym(String username, Gym gym) {
        SQLiteDatabase db = null;
        String query = String.format("DELETE FROM %s\n"
                        + "WHERE %s='%s' AND %s='%s';\n"
                        + "DELETE FROM %s\n"
                        + "WHERE %s='%s' AND %s='%s';",
                GYM_EQUIPMENT_TABLE,
                GYM_NAME, gym.getName(), USER_USERNAME, username,
                GYM_TABLE,
                GYM_NAME, gym.getName(), USER_USERNAME, username);

        boolean success = false;
        try {
            
            db = this.getWritableDatabase();
            db.rawQuery(query, null);
            success = true;
        } catch (Exception e) {System.out.println(e.getMessage());}
        finally {
            db.close();
        }
        return success;
    }

    @Override
    public boolean addWorkout(String username, Workout workout) {
        removeWorkout(username, workout);
        SQLiteDatabase db = null;
        StringBuilder query = new StringBuilder(String.format("INSERT INTO %s(%s,%s)\n"
                        + "VALUES ('%s','%s');\n",
                WORKOUT_TABLE, WORKOUT_NAME, USER_USERNAME,
                workout.getName(), username));
        if (!workout.getExercises().isEmpty()) {
            query.append(String.format("INSERT INTO %s(%s,%s,%s)\n"
                            + "VALUES",
                    WORKOUT_EXERCISE_TABLE, WORKOUT_NAME, USER_USERNAME, EXERCISE_NAME));
            for (Exercise exercise : workout.getExercises()) {
                query.append(String.format(" ('%s','%s','%s'),\n",
                        workout.getName(), username, exercise.getName()));
            }
            query.replace(query.length() - 2, query.length(), ";");
        }
        boolean success = false;

        try {
            
            db = this.getWritableDatabase();
            db.rawQuery(query.toString(), null);
            success = true;
        } catch (Exception e) {System.out.println(e.getMessage());}
        finally {
            db.close();
        }
        return success;
    }

    @Override
    public boolean removeWorkout(String username, Workout workout) {
        SQLiteDatabase db = null;
        Cursor c = null;
        String query = String.format("DELETE FROM %s\n"
                        + "WHERE %s='%s' AND %s='%s';\n"
                        + "DELETE FROM %s\n"
                        + "WHERE %s='%s' AND %s='%s';",
                WORKOUT_EXERCISE_TABLE,
                WORKOUT_NAME, workout.getName(), USER_USERNAME, username,
                WORKOUT_TABLE,
                WORKOUT_NAME, workout.getName(), USER_USERNAME, username);

        boolean success = false;

        try {
            
            db = this.getWritableDatabase();
            db.rawQuery(query, null);
            success = true;
        } catch (Exception e) {System.out.println(e.getMessage());}
        finally {
            db.close();
        }
        return success;
    }
}
