package mcgill.shredit.data;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mcgill.shredit.data.DataSource;
import mcgill.shredit.model.Equipment;
import mcgill.shredit.model.Exercise;
import mcgill.shredit.model.Gym;
import mcgill.shredit.model.User;
import mcgill.shredit.model.Workout;

public class DataSourceStub implements DataSource {

    private HashMap<String, Equipment> equipment;
    private HashMap<String, Exercise> exercises;
    private HashMap<String, HashMap<String, Gym>> gyms;
    private HashMap<String, HashMap<String, Workout>> workouts;
    private HashMap<String, User> users;

    public DataSourceStub() {
        equipment = new HashMap<>();
        exercises = new HashMap<>();
        gyms = new HashMap<>();
        workouts = new HashMap<>();
        users = new HashMap<>();
    }

    @Override
    public List<Equipment> getEquipmentList() {
        return new ArrayList<>(equipment.values());
    }

    @Override
    public List<Exercise> getExerciseList(String muscleGroup, String username, String gymName) {
        List<Exercise> list = new ArrayList<>(exercises.values());
        if(muscleGroup != null && !muscleGroup.isEmpty()) {
            int index = 0;
            while(index < list.size()) {
                if(muscleGroup.equals(list.get(index).getMuscleGroup())) {
                    index++;
                }
                else {
                    list.remove(index);
                }
            }
        }
        if(username != null && !username.isEmpty()
                && gymName != null && !gymName.isEmpty()) {
            int index = 0;
            List<Equipment> gymEquipment = gyms.get(username).get(gymName).getEquipments();
            while(index < list.size()) {
                if(gymEquipment.contains(list.get(index).getEquipment())) {
                    index++;
                }
                else {
                    list.remove(index);
                }
            }
        }
        return list;
    }

    @Override
    public List<Gym> getGymList(String username) {
        return new ArrayList<>(gyms.get(username).values());
    }

    @Override
    public List<Workout> getWorkoutList(String username) {
        return new ArrayList<>(workouts.get(username).values());
    }

    @Override
    public boolean checkPassword(String username, String password) {
        User user = users.get(username);
        if (user != null){
            if(user.getPassword().equals(password)){
                return true;
            }
        }

        return false;

    }

    @Override
    public boolean addUser(String username, String password) {
        users.put(username, new User(username, password));
        gyms.put(username, new HashMap<String, Gym>());
        workouts.put(username, new HashMap<String, Workout>());
        return true;
    }

    @Override
    public boolean addEquipment(Equipment equipment) {
        this.equipment.put(equipment.getName(), equipment);
        return true;
    }

    @Override
    public boolean removeEquipment(Equipment equipment) {
        if (!this.equipment.containsKey(equipment.getName())) {
            return false;
        }
        this.equipment.remove(equipment.getName());
        return true;
    }

    @Override
    public boolean addExercise(Exercise exercise) {
        exercises.put(exercise.getName(), exercise);
        return true;
    }

    @Override
    public boolean removeExercise(Exercise exercise) {
        if (!exercises.containsKey(exercise.getName())) {
            return false;
        }
        exercises.remove(exercise.getName());
        return true;
    }

    @Override
    public boolean addGym(String username, Gym gym) {
        if(!gyms.containsKey(username)) {
            return false;
        }
        gyms.get(username).put(gym.getName(), gym);
        return true;
    }

    @Override
    public boolean removeGym(String username, Gym gym) {
        if (!gyms.containsKey(username)) {
            return false;
        }
        HashMap<String, Gym> userGyms = gyms.get(username);
        if (userGyms.containsKey(gym.getName())) {
            return false;
        }
        userGyms.remove(gym.getName());
        return true;
    }

    @Override
    public boolean addWorkout(String username, Workout workout) {
        if(!workouts.containsKey(username)) {
            return false;
        }
        workouts.get(username).put(workout.getName(), workout);
        return true;
    }

    @Override
    public boolean removeWorkout(String username, Workout workout) {
        if (!workouts.containsKey(username)) {
            return false;
        }
        HashMap<String, Workout> userWorkouts = workouts.get(username);
        if (userWorkouts.containsKey(workout.getName())) {
            return false;
        }
        userWorkouts.remove(workout.getName());
        return true;
    }

    @Override
    public boolean removeUser(String username) {
        if(!users.containsKey(username)) {
            return true;
        }
        users.remove(username);
        gyms.remove(username);
        workouts.remove(username);
        return true;
    }

}
