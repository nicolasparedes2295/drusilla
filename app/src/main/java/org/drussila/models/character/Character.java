package org.drussila.models.character;

import java.util.HashMap;

public class Character {
    private int strength;
    private int constitution;
    private int dexterity;
    private int intelligence;
    private int wisdom;
    private int charisma;
    private int STR=mod_formula(strength);
    private int CON=mod_formula(constitution);
    private int DEX=mod_formula(dexterity;
    private int INT=mod_formula(intelligence);
    private int WIS=mod_formula(wisdom);
    private int CHA=mod_formula(charisma);
    private int health = 10+CON;
    
    public Character(int strength, int constitution, int dexterity, int intelligence, int wisdom, int charisma) {
        this.STR = strength;
        this.CON = constitution;
        this.DEX = dexterity;
        this.INT = intelligence;
        this.WIS = wisdom;
        this.CHA = charisma;
    }

    public int getStrength() {
        return strength;
    }

    public int getConstitution() {
        return constitution;
    }

    public int getDexterity() {
        return dexterity;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public int getWisdom() {
        return wisdom;
    }

    public int getCharisma() {
        return charisma;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public void setConstitution(int constitution) {
        this.constitution = constitution;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public void setWisdom(int wisdom) {
        this.wisdom = wisdom;
    }

    public void setCharisma(int charisma) {
        this.charisma = charisma;
    }

    private int mod_formula(int value){
        HashMap<Integer, Integer> modMap = new HashMap<Integer, Integer>();
        modMap.put(1,-5);
        modMap.put(1,-4);
        modMap.put(3,-4);
        modMap.put(4,-3);
        modMap.put(5,-3);
        modMap.put(6,-2);
        modMap.put(7,-2);
        modMap.put(8,-1);
        modMap.put(9,-1);
        modMap.put(10,0);
        modMap.put(11,0);
        modMap.put(12,1);
        modMap.put(13,1);
        modMap.put(14,2);
        modMap.put(15,2);
        modMap.put(16,3);
        modMap.put(17,3);
        modMap.put(18,4);
        modMap.put(19,4);
        modMap.put(20,5);
        modMap.put(21,5);
        modMap.put(22,6);
        modMap.put(23,6);
        modMap.put(24,7);
        modMap.put(25,7);
        modMap.put(26,8);
        modMap.put(27,8);
        modMap.put(28,9);
        modMap.put(29,9);
        modMap.put(30,10);
        if(value>30){
            modMap.put(value,(10+(value*10/100)));
        }
        return modMap.get(value);
    }
}
