package mcgill.shredit.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import mcgill.shredit.model.*;

public final class Repository {

    private static final String EQUIPMENT_TABLE = "Equipment";
    private static final String EQUIPMENT_ID = "eqid";
    private static final String EQUIPMENT_NAME = "name";

    private static final String EXERCISE_TABLE = "Exercises";
    private static final String EXERCISE_ID = "exid";
    private static final String EXERCISE_NAME = "name";
    private static final String EXERCISE_DESCRIPTION = "description";
    private static final String EXERCISE_MUSCLE_GROUP = "musclegroup";

    private static final String GYM_TABLE = "Gyms";
    private static final String GYM_ID = "gid";
    private static final String GYM_NAME = "name";
    private static final String GYM_EQUIPMENT_TABLE = "GymEquipment";

    private static final String WORKOUT_TABLE = "Workouts";
    private static final String WORKOUT_ID = "wid";
    private static final String WORKOUT_NAME = "name";
    private static final String WORKOUT_EXERCISE_TABLE = "WorkoutExercise";

    private static final String USER_TABLE = "Users";
    private static final String USER_USERNAME = "username";
    private static final String USER_PASSWORD = "password";

    private static Repository instance;

    private DBService dbs;

    private Repository() {
        dbs = new DBService();
    }

    public static Repository getInstance() {
        if (instance == null)
            instance = new Repository();
        return instance;
    }

    public List<Equipment> getEquipmentList() {
        ArrayList<Equipment> equipmentList= new ArrayList<>();
        ResultSet rs;


        String query = String.format("SELECT %s, %s FROM %s;",
                EQUIPMENT_ID, EQUIPMENT_NAME, EQUIPMENT_TABLE);

        try {
            rs = dbs.query(query);
            if (rs != null) {
                while (rs.next()) {
                    equipmentList.add(new Equipment(
                            rs.getInt(EQUIPMENT_ID),
                            rs.getString(EQUIPMENT_NAME)));
                }
            }
        } finally {
            return equipmentList;
        }
    }

    public List<Exercise> getExerciseList(String muscleGroup, int gymID) {
        ArrayList<Exercise> exerciseList= new ArrayList<>();
        HashMap<Integer, Equipment> equipmentSet = new HashMap<>();
        ResultSet rs;

        String query = String.format("SELECT %s, %s, %s, %s, ex_t.%s, %s FROM AS ex_t %s "
                +"INNER JOIN %s AS eq_t ON ext_t.%s=eq_t.%s",
                EXERCISE_ID, EXERCISE_NAME, EXERCISE_DESCRIPTION, EXERCISE_MUSCLE_GROUP,
                EQUIPMENT_ID, EQUIPMENT_NAME, EXERCISE_TABLE, EQUIPMENT_TABLE, EQUIPMENT_ID, EQUIPMENT_ID);

        if(gymID >= 0) {
            query += String.format(" INNER JOIN %s AS ge_t ON ex_t.%s=ge_t.%s",
                    GYM_EQUIPMENT_TABLE, EXERCISE_ID, EXERCISE_ID);
        }

        if (muscleGroup != null) {
           query += String.format(" WHERE %s='%s'", EXERCISE_MUSCLE_GROUP, muscleGroup);
           if (gymID >= 0) {
               query += String.format(" AND %s=%d", GYM_ID, gymID);
           }

        } else if (gymID >= 0) {
            query += String.format(" WHERE %s='%s'", GYM_ID, gymID);
        }

        query += ";";

        try {
            rs = dbs.query(query);
            if (rs != null) {
                while (rs.next()) {
                    if (!equipmentSet.containsKey(rs.getInt(EQUIPMENT_ID))) {
                        equipmentSet.put(rs.getInt(EQUIPMENT_ID), new Equipment(
                                rs.getInt(EQUIPMENT_ID),
                                rs.getString(EQUIPMENT_NAME)
                        ));
                    }
                    exerciseList.add(new Exercise(
                            rs.getInt(EXERCISE_ID),
                            rs.getString(EXERCISE_NAME),
                            rs.getString(EXERCISE_DESCRIPTION),
                            rs.getString(EXERCISE_MUSCLE_GROUP),
                            equipmentSet.get(rs.getInt(EQUIPMENT_ID))));
                }
            }
        } finally {
            return exerciseList;
        }
    }

    public List<Gym> getGymList(String username) {

        String query = String.format("SELECT %s.%s, %s, %s.%s, %s, "
                +"FROM %s INNER JOIN %s INNER JOIN %s WHERE %s='%s';",
                EQUIPMENT_TABLE, EQUIPMENT_ID, EQUIPMENT_NAME, GYM_TABLE, GYM_ID, GYM_NAME,
                EXERCISE_TABLE, GYM_EQUIPMENT_TABLE, GYM_TABLE, USER_USERNAME, username);

        HashMap<Integer, Gym> gymSet = new HashMap<>();
        HashMap<Integer, Equipment> equipmentSet = new HashMap<>();
        ArrayList<Gym> gymList = new ArrayList<>();

        try {
            ResultSet rs = dbs.query(query);
            if (rs != null) {
                while (rs.next()) {
                    int equipmentID = rs.getInt(EQUIPMENT_ID);
                    int gymID = rs.getInt(GYM_ID);
                    if (!equipmentSet.containsKey(equipmentID)) {
                        equipmentSet.put(equipmentID, new Equipment(
                                equipmentID,
                                rs.getString(EQUIPMENT_NAME)
                        ));
                    }
                    if (!gymSet.containsKey(gymID)) {
                        Gym newGym = new Gym(gymID, rs.getString(GYM_NAME));
                        gymSet.put(gymID, newGym);
                        gymList.add(newGym);
                    }
                    gymSet.get(gymID).addEquipment(equipmentSet.get(equipmentID));
                }
            }
        } finally {
            return gymList;
        }
    }

    public List<Workout> getWorkoutList(String username) {
        String query = String.format("SELECT eq_t.%s, %s, ex_t.%s, %s, %s, %s, w_t.%s, %s "
                + "FROM %s AS eq_t INNER JOIN %s AS ex_t INNER JOIN %s AS w_t INNER JOIN %s we_t WHERE %s='%s';",
                EQUIPMENT_ID, EQUIPMENT_NAME, EXERCISE_NAME, EXERCISE_NAME, EXERCISE_DESCRIPTION,
                EXERCISE_MUSCLE_GROUP, WORKOUT_ID, WORKOUT_NAME,
                EQUIPMENT_TABLE, EXERCISE_TABLE, EQUIPMENT_ID, EQUIPMENT_ID,
                WORKOUT_EXERCISE_TABLE, EXERCISE_ID, EXERCISE_ID,
                WORKOUT_TABLE, WORKOUT_ID, WORKOUT_ID,
                USER_USERNAME, username);

        HashMap<Integer, Workout> workoutSet = new HashMap<>();
        HashMap<Integer, Exercise> exerciseSet = new HashMap<>();
        HashMap<Integer, Equipment> equipmentSet = new HashMap<>();
        ArrayList<Workout> workoutList = new ArrayList<>();

        try {
            ResultSet rs = dbs.query(query);
            if (rs != null) {
                while (rs.next()) {
                    int workoutID = rs.getInt(WORKOUT_ID);
                    int exerciseID = rs.getInt(EQUIPMENT_ID);
                    int equipmentID = rs.getInt(EQUIPMENT_ID);
                    if (!equipmentSet.containsKey(equipmentID)) {
                        equipmentSet.put(equipmentID, new Equipment(
                                equipmentID,
                                rs.getString(EQUIPMENT_NAME)
                        ));
                    }
                    if (!exerciseSet.containsKey(exerciseID)) {
                        exerciseSet.put(exerciseID, new Exercise(
                                exerciseID,
                                rs.getString(EXERCISE_NAME),
                                rs.getString(EXERCISE_DESCRIPTION),
                                rs.getString(EXERCISE_MUSCLE_GROUP),
                                equipmentSet.get(equipmentID)));
                    }
                    if (!workoutSet.containsKey(workoutID)) {
                        Workout newWorkout = new Workout(workoutID, rs.getString(WORKOUT_NAME));
                        workoutSet.put(workoutID, newWorkout);
                        workoutList.add(newWorkout);
                    }
                    workoutSet.get(workoutID).addExercise(exerciseSet.get(exerciseID));
                }
            }
        } finally {
            return workoutList;
        }
    }

    public String getPassword(String username) {
        String query = String.format("SELECT %s FROM %s WHERE %s='%s';",
                USER_PASSWORD, USER_TABLE, USER_USERNAME, username);
        String password = null;

        try {
            ResultSet rs = dbs.query(query);
            if (rs != null) {
                rs.next();
                password = rs.getString(USER_PASSWORD);
            }
        } finally {
            return password;
        }
    }

    public boolean checkPassword(String username,String password){
        //TODO
        return true;
    }
}
