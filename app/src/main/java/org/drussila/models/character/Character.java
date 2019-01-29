package org.drussila.models.character;

import java.util.HashMap;

public class Character {
    private Integer strength;
    private Integer constitution;
    private Integer dexterity;
    private Integer intelligence;
    private Integer wisdom;
    private Integer charisma;
    private Integer STR;
    private Integer CON;
    private Integer DEX;
    private Integer INT;
    private Integer WIS;
    private Integer CHA;
    private Double health;

    public void setHealth(Double health) {
        this.health = health;
    }

    public Double getHealth() {
        return health;
    }

    public Character(int strength, int constitution, int dexterity, int intelligence, int wisdom, int charisma) {
        STR=mod_formula(strength);
        CON=mod_formula(constitution);
        DEX=mod_formula(dexterity);
        INT=mod_formula(intelligence);
        WIS=mod_formula(wisdom);
        CHA=mod_formula(charisma);
        health = (double) 10+CON;
    }

    public Integer getStrength() {
        return strength;
    }

    public Integer getConstitution() {
        return constitution;
    }

    public Integer getDexterity() {
        return dexterity;
    }

    public Integer getIntelligence() {
        return intelligence;
    }

    public Integer getWisdom() {
        return wisdom;
    }

    public Integer getCharisma() {
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

    private Integer mod_formula(int value){
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
