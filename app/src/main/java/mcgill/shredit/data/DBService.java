package mcgill.shredit.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import mcgill.shredit.model.Equipment;
import mcgill.shredit.model.Exercise;
import mcgill.shredit.model.Gym;
import mcgill.shredit.model.Workout;

public class DBService implements DataSource {

    private static final String DB_USERNAME = "shreditpostgre";
    private static final String DB_PASSWORD = "shreditpostgre";
    private static final String DB_SERVER_URL = "jdbc:postgresql://shreditpostgre.caqrxjkfzeba.us-east-2.rds.amazonaws.com:5432/shreditpostgre";

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

    public DBService() {}

    private Connection getConnection() throws SQLException {
        Connection con;
        Properties connectionProps = new Properties();
        connectionProps.put("user", DB_USERNAME);
        connectionProps.put("password", DB_PASSWORD);

        con = DriverManager.getConnection(DB_SERVER_URL, connectionProps);
        return con;
    }

    @Override
    public List<Equipment> getEquipmentList() {
        ArrayList<Equipment> equipmentList= new ArrayList<>();
        Connection con = null;
        Statement stmt = null;

        String query = String.format("SELECT DISTINCT %s FROM %s;",
                EQUIPMENT_NAME, EQUIPMENT_TABLE);

        try {
            con = getConnection();
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs != null) {
                while (rs.next()) {
                    equipmentList.add(new Equipment(rs.getString(EQUIPMENT_NAME)));
                }
            }
        } catch (SQLException e) {System.out.println(e.getMessage());}
        finally {
            try {
                if (con != null) {
                    con.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {}
        }
        return equipmentList;
    }

    @Override
    public List<Exercise> getExerciseList(String muscleGroup, String username, String gymName) {
        ArrayList<Exercise> exerciseList= new ArrayList<>();
        HashMap<String, Equipment> equipmentSet = new HashMap<>();
        Connection con = null;
        Statement stmt = null;

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

        try {
            con = getConnection();
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs != null) {
                while (rs.next()) {
                    if (!equipmentSet.containsKey(rs.getString(EQUIPMENT_NAME))) {
                        equipmentSet.put(rs.getString(EQUIPMENT_NAME), new Equipment(
                                rs.getString(EQUIPMENT_NAME)));
                    }
                    exerciseList.add(new Exercise(
                            rs.getString(EXERCISE_NAME),
                            rs.getString(EXERCISE_DESCRIPTION),
                            rs.getString(EXERCISE_MUSCLE_GROUP),
                            equipmentSet.get(rs.getString(EQUIPMENT_NAME))));
                }
            }
        } catch (SQLException e) {System.out.println(e.getMessage());}
        finally {
            try {
                if (con != null) {
                    con.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {}
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
        Connection con = null;
        Statement stmt = null;

        try {
            con = getConnection();
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs != null) {
                while (rs.next()) {
                    String equipmentName = rs.getString(EQUIPMENT_NAME);
                    String gymName = rs.getString(GYM_NAME);
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
                }
            }
        } catch (SQLException e) {System.out.println(e.getMessage());}
        finally {
            try {
                if (con != null) {
                    con.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {}
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
        Connection con = null;
        Statement stmt = null;

        try {
            con = getConnection();
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs != null) {
                while (rs.next()) {
                    String workoutName = rs.getString(WORKOUT_NAME);
                    String exerciseName = rs.getString(EQUIPMENT_NAME);
                    String equipmentName = rs.getString(EQUIPMENT_NAME);
                    if (!equipmentSet.containsKey(equipmentName)) {
                        equipmentSet.put(equipmentName, new Equipment(
                                rs.getString(EQUIPMENT_NAME)));
                    }
                    if (!exerciseSet.containsKey(exerciseName)) {
                        exerciseSet.put(exerciseName, new Exercise(
                                rs.getString(EXERCISE_NAME),
                                rs.getString(EXERCISE_DESCRIPTION),
                                rs.getString(EXERCISE_MUSCLE_GROUP),
                                equipmentSet.get(equipmentName)));
                    }
                    if (!workoutSet.containsKey(workoutName)) {
                        Workout newWorkout = new Workout(rs.getString(WORKOUT_NAME));
                        workoutSet.put(workoutName, newWorkout);
                        workoutList.add(newWorkout);
                    }
                    workoutSet.get(workoutName).addExercise(exerciseSet.get(exerciseName));
                }
            }
        } catch (SQLException e) {System.out.println(e.getMessage());}
        finally {
            try {
                if (con != null) {
                    con.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {}
            return workoutList;
        }
    }

    public boolean checkPassword(String username, String password) {
        Connection con = null;
        Statement stmt = null;
        String query = String.format("SELECT DISTINCT %s FROM %s WHERE %s='%s';",
                USER_PASSWORD, USER_TABLE, USER_USERNAME, username);
        String truePassword = null;

        try {
            con = getConnection();
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs != null) {
                rs.next();
                truePassword = rs.getString(USER_PASSWORD);
            }
        } catch (SQLException e) {System.out.println(e.getMessage());}
        finally {
            try {
                if (con != null) {
                    con.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {}
            return truePassword != null && truePassword.equals(password);
        }
    }

    @Override
    public boolean userExists(String username) {
        return false;
    }

    @Override
    public boolean addUser(String username, String password) {
        removeUser(username);
        Connection con = null;
        Statement stmt = null;
        String query = String.format("INSERT INTO %s(%s,%s)\n"
                        + "VALUES ('%s','%s');",
                USER_TABLE, USER_USERNAME, USER_PASSWORD,
                username, password);

        boolean success = false;

        try {
            con = getConnection();
            stmt = con.createStatement();
            stmt.executeUpdate(query);
            success = true;
        } catch (SQLException e) {System.out.println(e.getMessage());}
        finally {
            try {
                if (con != null) {
                    con.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {}
        }
        return success;
    }

    @Override
    public boolean removeUser(String username) {
        Connection con = null;
        Statement stmt = null;
        String query = String.format("DELETE FROM %s\n"
                        + "WHERE %s='%s';",
                USER_TABLE,
                USER_USERNAME, username);
        boolean success = false;

        try {
            con = getConnection();
            stmt = con.createStatement();
            stmt.executeUpdate(query);
            success = true;
        } catch (SQLException e) {System.out.println(e.getMessage());}
        finally {
            try {
                if (con != null) {
                    con.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {}
        }
        return success;
    }

    @Override
    public boolean addEquipment(Equipment equipment) {
        removeEquipment(equipment);
        Connection con = null;
        Statement stmt = null;
        String query = String.format("INSERT INTO %s(%s)\n"
                        + "VALUES ('%s');",
                EQUIPMENT_TABLE, EQUIPMENT_NAME,
                equipment.getName());


        boolean success = false;

        try {
            con = getConnection();
            stmt = con.createStatement();
            stmt.executeUpdate(query);
            success = true;
        } catch (SQLException e) {System.out.println(e.getMessage());}
        finally {
            try {
                if (con != null) {
                    con.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {}
        }
        return success;
    }

    @Override
    public boolean removeEquipment(Equipment equipment) {
        Connection con = null;
        Statement stmt = null;
        String query = String.format("DELETE FROM %s\n"
                        + "WHERE %s='%s';",
                EQUIPMENT_TABLE,
                EQUIPMENT_NAME, equipment.getName());
        boolean success = false;

        try {
            con = getConnection();
            stmt = con.createStatement();
            stmt.executeUpdate(query);
            success = true;
        } catch (SQLException e) {System.out.println(e.getMessage());}
        finally {
            try {
                if (con != null) {
                    con.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {}
        }
        return success;
    }

    @Override
    public boolean addExercise(Exercise exercise) {
        removeExercise(exercise);
        Connection con = null;
        Statement stmt = null;
        String query = String.format("INSERT INTO %s(%s,%s,%s,%s)\n"
                        + "VALUES ('%s','%s','%s','%s');",
                EXERCISE_TABLE, EXERCISE_NAME, EXERCISE_DESCRIPTION, EXERCISE_MUSCLE_GROUP,
                EQUIPMENT_NAME,
                exercise.getName(), exercise.getDescription(), exercise.getMuscleGroup(),
                exercise.getEquipment().getName());


        boolean success = false;

        try {
            con = getConnection();
            stmt = con.createStatement();
            stmt.executeUpdate(query);
            success = true;
        } catch (SQLException e) {System.out.println(e.getMessage());}
        finally {
            try {
                if (con != null) {
                    con.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {}
        }
        return success;
    }

    @Override
    public boolean removeExercise(Exercise exercise) {
        Connection con = null;
        Statement stmt = null;
        String query = String.format("DELETE FROM %s\n"
                        + "WHERE %s='%s';",
                EXERCISE_TABLE,
                EXERCISE_NAME, exercise.getName());
        boolean success = false;

        try {
            con = getConnection();
            stmt = con.createStatement();
            stmt.executeUpdate(query);
            success = true;
        } catch (SQLException e) {System.out.println(e.getMessage());}
        finally {
            try {
                if (con != null) {
                    con.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {}
        }
        return success;
    }

    @Override
    public boolean addGym(String username, Gym gym) {
        removeGym(username, gym);
        Connection con = null;
        Statement stmt = null;
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
            con = getConnection();
            stmt = con.createStatement();
            stmt.executeUpdate(query.toString());
            success = true;
        } catch (SQLException e) {System.out.println(e.getMessage());}
        finally {
            try {
                if (con != null) {
                    con.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {}
        }
        return success;
    }

    @Override
    public boolean removeGym(String username, Gym gym) {
        Connection con = null;
        Statement stmt = null;
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
            con = getConnection();
            stmt = con.createStatement();
            stmt.executeUpdate(query);
            success = true;
        } catch (SQLException e) {System.out.println(e.getMessage());}
        finally {
            try {
                if (con != null) {
                    con.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {}
        }
        return success;
    }

    @Override
    public boolean addWorkout(String username, Workout workout) {
        removeWorkout(username, workout);
        Connection con = null;
        Statement stmt = null;
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
            con = getConnection();
            stmt = con.createStatement();
            stmt.executeUpdate(query.toString());
            success = true;
        } catch (SQLException e) {System.out.println(e.getMessage());}
        finally {
            try {
                if (con != null) {
                    con.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {}
        }
        return success;
    }

    @Override
    public boolean removeWorkout(String username, Workout workout) {
        Connection con = null;
        Statement stmt = null;
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
            con = getConnection();
            stmt = con.createStatement();
            stmt.executeUpdate(query);
            success = true;
        } catch (SQLException e) {System.out.println(e.getMessage());}
        finally {
            try {
                if (con != null) {
                    con.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {}
        }
        return success;
    }
}