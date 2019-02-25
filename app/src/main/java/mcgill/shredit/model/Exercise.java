/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4262.30c9ffc7c modeling language!*/

package mcgill.shredit.model;

// line 15 "../../../../../../ShreditModel.ump"
public class Exercise
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Exercise Attributes
  private int id;
  private String name;
  private String description;
  private String muscleGroup;

  //Exercise Associations
  private Equipment equipment;

  //Exercise Associations
  private Equipment equipement;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Exercise(int aId, String aName, String aDescription, String aMuscleGroup, Equipment aEquipment)
  {
    id = aId;
    name = aName;
    description = aDescription;
    muscleGroup = aMuscleGroup;
    if (!setEquipment(aEquipment))
    {
      throw new RuntimeException("Unable to create Exercise due to aEquipment");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setId(int aId)
  {
    boolean wasSet = false;
    id = aId;
    wasSet = true;
    return wasSet;
  }

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public boolean setDescription(String aDescription)
  {
    boolean wasSet = false;
    description = aDescription;
    wasSet = true;
    return wasSet;
  }

  public boolean setMuscleGroup(String aMuscleGroup)
  {
    boolean wasSet = false;
    muscleGroup = aMuscleGroup;
    wasSet = true;
    return wasSet;
  }

  public int getId()
  {
    return id;
  }

  public String getName()
  {
    return name;
  }

  public String getDescription()
  {
    return description;
  }

  public String getMuscleGroup()
  {
    return muscleGroup;
  }
  /* Code from template association_GetOne */
  public Equipment getEquipment()
  {
    return equipment;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setEquipment(Equipment aNewEquipment)
  {
    boolean wasSet = false;
    if (aNewEquipment != null)
    {
      equipment = aNewEquipment;
      wasSet = true;
    }
    return wasSet;
  }
  /* Code from template association_GetOne */
  public Equipment getEquipement()
  {
    return equipement;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setEquipement(Equipment aNewEquipement)
  {
    boolean wasSet = false;
    if (aNewEquipement != null)
    {
      equipement = aNewEquipement;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete()
  {
    equipment = null;
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "name" + ":" + getName()+ "," +
            "description" + ":" + getDescription()+ "," +
            "muscleGroup" + ":" + getMuscleGroup()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "equipment = "+(getEquipment()!=null?Integer.toHexString(System.identityHashCode(getEquipment())):"null");
  }
}