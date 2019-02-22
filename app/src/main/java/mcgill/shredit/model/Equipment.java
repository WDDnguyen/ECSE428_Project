/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4262.30c9ffc7c modeling language!*/

package mcgill.shredit.model;

// line 9 "../../../../../../ShreditModel.ump"
public class Equipment
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Equipment Attributes
  private int id;
  private String name;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Equipment(int aId, String aName)
  {
    id = aId;
    name = aName;
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

  public int getId()
  {
    return id;
  }

  public String getName()
  {
    return name;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "name" + ":" + getName()+ "]";
  }
}