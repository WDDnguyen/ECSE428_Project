/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4262.30c9ffc7c modeling language!*/

package mcgill.shredit.model;
import java.util.*;

// line 21 "../../../../../../ShreditModel.ump"
public class Workout
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Workout Attributes
  private String name;
  private int id;

  //Workout Associations
  private List<Exercise> exercises;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Workout(String aName, int aId)
  {
    name = aName;
    id = aId;
    exercises = new ArrayList<Exercise>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public boolean setId(int aId)
  {
    boolean wasSet = false;
    id = aId;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }

  public int getId()
  {
    return id;
  }
  /* Code from template association_GetMany */
  public Exercise getExercise(int index)
  {
    Exercise aExercise = exercises.get(index);
    return aExercise;
  }

  public List<Exercise> getExercises()
  {
    List<Exercise> newExercises = Collections.unmodifiableList(exercises);
    return newExercises;
  }

  public int numberOfExercises()
  {
    int number = exercises.size();
    return number;
  }

  public boolean hasExercises()
  {
    boolean has = exercises.size() > 0;
    return has;
  }

  public int indexOfExercise(Exercise aExercise)
  {
    int index = exercises.indexOf(aExercise);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfExercises()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addExercise(Exercise aExercise)
  {
    boolean wasAdded = false;
    if (exercises.contains(aExercise)) { return false; }
    exercises.add(aExercise);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeExercise(Exercise aExercise)
  {
    boolean wasRemoved = false;
    if (exercises.contains(aExercise))
    {
      exercises.remove(aExercise);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addExerciseAt(Exercise aExercise, int index)
  {  
    boolean wasAdded = false;
    if(addExercise(aExercise))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfExercises()) { index = numberOfExercises() - 1; }
      exercises.remove(aExercise);
      exercises.add(index, aExercise);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveExerciseAt(Exercise aExercise, int index)
  {
    boolean wasAdded = false;
    if(exercises.contains(aExercise))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfExercises()) { index = numberOfExercises() - 1; }
      exercises.remove(aExercise);
      exercises.add(index, aExercise);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addExerciseAt(aExercise, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    exercises.clear();
  }


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "id" + ":" + getId()+ "]";
  }
}