package mcgill.shredit.model;

import java.util.ArrayList;

public class Gym {
    private String name;
    private int id;
    ArrayList<Equipment> equipments;

    public Gym(String name, int id, ArrayList<Equipment> equipments){
        this.name = name;
        this.id = id;
        this.equipments = equipments;
    }

    public void setName(String newName){
        this.name = newName;
    }

    public void setId(int newId){
        this.id = newId;
    }

    public void addEquipment(Equipment equipment){
        equipments.add(equipment);
    }

    public void removeEquipment(Equipment equipment){
        equipments.remove(equipment);
    }

    public String getName(){
        return this.name;
    }

    public int getId(){
        return this.id;
    }

    public ArrayList<Equipment> getEquipments(){
        return this.equipments;
    }

    public void setEquipments(ArrayList<Equipment> equipments) {
        this.equipments = equipments;
    }
}

