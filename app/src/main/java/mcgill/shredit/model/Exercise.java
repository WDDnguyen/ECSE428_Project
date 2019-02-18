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
  private String muscleGroup;
  private String name;
  private String description;
  private int id;

  //Exercise Associations
  private Equipment equipement;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Exercise(String aMuscleGroup, String aName, String aDescription, int aId, Equipment aEquipement)
  {
    muscleGroup = aMuscleGroup;
    name = aName;
    description = aDescription;
    id = aId;
    if (!setEquipement(aEquipement))
    {
      throw new RuntimeException("Unable to create Exercise due to aEquipement");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setMuscleGroup(String aMuscleGroup)
  {
    boolean wasSet = false;
    muscleGroup = aMuscleGroup;
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

  public boolean setId(int aId)
  {
    boolean wasSet = false;
    id = aId;
    wasSet = true;
    return wasSet;
  }

  public String getMuscleGroup()
  {
    return muscleGroup;
  }

  public String getName()
  {
    return name;
  }

  public String getDescription()
  {
    return description;
  }

  public int getId()
  {
    return id;
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
    equipement = null;
  }


  public String toString()
  {
    return super.toString() + "["+
            "muscleGroup" + ":" + getMuscleGroup()+ "," +
            "name" + ":" + getName()+ "," +
            "description" + ":" + getDescription()+ "," +
            "id" + ":" + getId()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "equipement = "+(getEquipement()!=null?Integer.toHexString(System.identityHashCode(getEquipement())):"null");
  }
}