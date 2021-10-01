package com.company;

import java.util.ArrayList;
import java.util.Random;

public class Zombie {

    private int wave; //殭屍第幾波
    private int airValue;
    private int landValue;
    private  final Random random = new Random();

    public int getWave() {
        return wave;
    }

    public Zombie() {
        this.wave = 0;
    }

    public int getZombieLandValue() {
        int zombieA = 5;
        int zombieB = 7;
        int zombieC = 10;
        int zombieD = 13;
        int zombieE = 17;
        int zombieF = 25;

        landValue = (wave * 3) * zombieA + (wave / 10 * 5) * zombieB + (wave / 10 * 4) * zombieC +
                (wave / 10 * 3) * zombieD + (wave / 10 * 2) * zombieE + (wave / 10) * zombieF;
        return landValue;
    }

    public int getZombieAirValue() {
        int zombieFly = 2; //飛行殭屍
        int zombieBigFly = 4; //飛行大僵屍
        airValue = (wave - 7) * zombieFly + (wave - 7) / 2 * zombieBigFly;
        return airValue;
    }

    public void timePlus() {
        wave++;
    }

    public void airStrike(Unit unit, ArrayList<Building> buildingList) throws InterruptedException {
        //殭屍空中攻擊
        Blacksmith airCraft = ((Blacksmith) buildingList.get(5));
        int airValue = getZombieAirValue();
        if (airValue >= airCraft.getAircraftLife() * unit.getAircraftCount() + unit.getVillagerCount()) {
            //1.飛機全數墜毀 + 市民全數死亡，房屋遭受攻擊
            //飛機數量歸零，市民數量歸零
            airValue -= airCraft.getAircraftLife() * unit.getAircraftCount() + unit.getVillagerCount();
            unit.setAircraftCount(0);
            unit.setVillagerCount(0);
            //未增加房屋攻擊-------------------------------------------------------------------------------------------
            while (airValue > 0) {
                ArrayList<Building> tempBuildingList = new ArrayList<>();
                for (Building building : buildingList) {
                    if (building.getBuildCheck() == Building.BuildCheck.UNBUILDABLE) {
                        tempBuildingList.add(building);
                    }
                }
                if (tempBuildingList.size() == 0) {
                    break;
                }
                Building buildingIsAttacked = tempBuildingList.get(random.nextInt(tempBuildingList.size()));
                if (airValue >= buildingIsAttacked.getLife()) {
                    airValue -= buildingIsAttacked.getLife();
                    showAttacked(buildingIsAttacked);
                    System.out.println(buildingIsAttacked.getName() + "已被殭屍摧毀!!!");
                    buildingIsAttacked.buildReset();
                    //要記得房屋毀壞要做的事
                } else {
                    showAttacked(buildingIsAttacked);
                    airValue = 0;
                    System.out.println("雖然被攻擊但建築沒事!!");
                }
            }
            //顯示哪間房屋被攻擊，多少傷害(未增加在showZombie)
            //-------------------------------------------------------------------------------------------------------

        } else if (airValue >= airCraft.getAircraftLife() * unit.getAircraftCount()) {
            //2.飛機全數墜毀，市民人數夠
            //市民人數=市民人數-(飛行僵屍素質-飛機素質*飛機數量)
            //飛機數量歸零
            unit.setVillagerCount(unit.getVillagerCount() - (airValue - airCraft.getAircraftLife() * unit.getAircraftCount()));
            unit.setAircraftCount(0);
        } else {
            //3.飛機沒全部墜毀 => 飛機數量 = (飛機素質*飛機數量-飛行僵屍素質)/飛機素質
            unit.setAircraftCount((airCraft.getAircraftLife() * unit.getAircraftCount() - airValue) / airCraft.getAircraftLife());
        }
    }

    public void landStrike(Unit unit, ArrayList<Building> buildingList) throws InterruptedException {
        //殭屍地面攻擊
        int landValue = getZombieLandValue();
        Blacksmith army = ((Blacksmith) buildingList.get(5));
        if (landValue >= army.getArmyLife() * unit.getArmyCount() + unit.getVillagerCount()) {
            //1.士兵全數死亡 + 市民全數死亡
            //房屋遭受攻擊
            //士兵數量歸零，市民數量歸零
            landValue -= army.getArmyLife() * unit.getArmyCount() + unit.getVillagerCount();
            unit.setArmyCount(0);
            unit.setVillagerCount(0);
            //未增加房屋攻擊-------------------------------------------------------------------------------------------

            while (landValue > 0) {
                ArrayList<Building> tempBuildingList = new ArrayList<>();
                for (Building building : buildingList) {
                    if (building.getBuildCheck() == Building.BuildCheck.UNBUILDABLE) {
                        tempBuildingList.add(building);
                    }
                }
                if (tempBuildingList.size() == 0) {
                    break;
                }
                Building buildingIsAttacked = tempBuildingList.get(random.nextInt(tempBuildingList.size()));
                if (landValue >= buildingIsAttacked.getLife()) { //如果random的是房屋，房屋被摧毀後，要GameOver
                    landValue -= buildingIsAttacked.getLife();
                    showAttacked(buildingIsAttacked);
                    System.out.println(buildingIsAttacked.getName() + "已被殭屍摧毀!!!");
                    buildingIsAttacked.buildReset();
                    //要記得房屋毀壞要做的事
                } else {
                    showAttacked(buildingIsAttacked);
                    landValue = 0;
                    System.out.println("雖然被攻擊但建築沒事!!");
                }
            }

            //顯示哪間房屋被攻擊，多少傷害(未增加在showZombie)
            //-------------------------------------------------------------------------------------------------------

        } else if (landValue >= army.getArmyLife() * unit.getArmyCount()) {
            //2.士兵全部死亡，，市民人數夠
            //市民人數=市民人數-(陸地僵屍素質-士兵素質*士兵數量)
            //士兵數量歸零
            unit.setVillagerCount(unit.getVillagerCount() - (landValue - army.getArmyLife() * unit.getArmyCount()));
            unit.setArmyCount(0);
            //break inner;
        } else {
            //3.士兵有存活
            //士兵數量=(士兵素質*士兵數量-陸地僵屍素質)/士兵素質
            unit.setArmyCount((army.getArmyLife() * unit.getArmyCount() - landValue) / army.getArmyLife());
        }

        //秀出僵屍戰鬥結果
        showZombie(unit);
    }

    public void showZombie(Unit unit) {
        System.out.println("\n殭屍來襲: 第" + wave + "波");
        System.out.println("當前時間: " + wave * 16);
        System.out.println("此波殭屍攻擊力:  陸地攻擊: " + landValue + "\t空中攻擊: " + airValue);
        System.out.println("當前市民: " + unit.getVillagerCount() + "\t當前士兵: " + unit.getArmyCount() + "\t當前飛機: " + unit.getAircraftCount());
        System.out.println();
    }

    public void showAttacked(Building building) throws InterruptedException {
        int repeatTimes = 3;
        for (int i = 0; i <= repeatTimes; i++) {
            Thread.sleep(1500);
            System.out.println("--------" + building.getName() + "被攻擊中--------");
        }
    }
}
