/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4262.30c9ffc7c modeling language!*/

package mcgill.shredit.model;
import java.util.*;

// line 3 "../../../../../../ShreditModel.ump"
public class Gym
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Gym Attributes
  private String name;
  private int id;

  //Gym Associations
  private List<Equipment> equipments;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Gym(String aName, int aId)
  {
    name = aName;
    id = aId;
    equipments = new ArrayList<Equipment>();
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
  public Equipment getEquipment(int index)
  {
    Equipment aEquipment = equipments.get(index);
    return aEquipment;
  }

  public List<Equipment> getEquipments()
  {
    List<Equipment> newEquipments = Collections.unmodifiableList(equipments);
    return newEquipments;
  }

  public int numberOfEquipments()
  {
    int number = equipments.size();
    return number;
  }

  public boolean hasEquipments()
  {
    boolean has = equipments.size() > 0;
    return has;
  }

  public int indexOfEquipment(Equipment aEquipment)
  {
    int index = equipments.indexOf(aEquipment);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfEquipments()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addEquipment(Equipment aEquipment)
  {
    boolean wasAdded = false;
    if (equipments.contains(aEquipment)) { return false; }
    equipments.add(aEquipment);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeEquipment(Equipment aEquipment)
  {
    boolean wasRemoved = false;
    if (equipments.contains(aEquipment))
    {
      equipments.remove(aEquipment);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addEquipmentAt(Equipment aEquipment, int index)
  {  
    boolean wasAdded = false;
    if(addEquipment(aEquipment))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfEquipments()) { index = numberOfEquipments() - 1; }
      equipments.remove(aEquipment);
      equipments.add(index, aEquipment);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveEquipmentAt(Equipment aEquipment, int index)
  {
    boolean wasAdded = false;
    if(equipments.contains(aEquipment))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfEquipments()) { index = numberOfEquipments() - 1; }
      equipments.remove(aEquipment);
      equipments.add(index, aEquipment);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addEquipmentAt(aEquipment, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    equipments.clear();
  }


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "id" + ":" + getId()+ "]";
  }
}