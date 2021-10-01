package com.company;

public class Unit {
    private int villagerCount = 20;//村民人數
    private int armyCount = 10; //士兵人數
    private int aircraftCount = 0; //飛機數量

    public int getVillagerCount() {
        return villagerCount;
    }

    public void setVillagerCount(int villagerCount) {
        this.villagerCount = villagerCount;
    }

    public int getArmyCount() {
        return armyCount;
    }

    public void setArmyCount(int armyCount) {
        this.armyCount = armyCount;
    }

    public int getAircraftCount() {
        return aircraftCount;
    }

    public void setAircraftCount(int aircraftCount) {
        this.aircraftCount = aircraftCount;
    }

    public void addVillager(int value) {
        villagerCount += value;
    }

    public void addArmy(int value) {
        armyCount += value;
    }

    public void addAircraft(int value) {
        aircraftCount += value;
    }
}
