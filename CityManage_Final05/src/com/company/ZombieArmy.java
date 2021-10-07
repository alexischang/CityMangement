package com.company;

import java.util.ArrayList;
import java.util.Random;

public class ZombieArmy {

    private int wave; //殭屍第幾波
    private int airValue;//陸地殭屍數
    private int landValue;//飛行殭屍數
    private  final Random random = new Random();

    public int getWave() {
        return wave;
    }

    public ZombieArmy() {
        this.wave = 0;
    }

    Zombie.Builder builderA = new Zombie.Builder("name", 1,1);
    Zombie zombieA = builderA
            .setName("殭屍")
            .setAttack(5)
            .setType(1)//1.陸地 2.飛行
            .build();

    Zombie.Builder builderB = new Zombie.Builder("name", 1,1);
    Zombie zombieB = builderB
            .setName("大殭屍")
            .setAttack(7)
            .setType(1)//1.陸地 2.飛行
            .build();

    Zombie.Builder builderC = new Zombie.Builder("name", 1,1);
    Zombie zombieC = builderC
            .setName("I型變異體")
            .setAttack(10)
            .setType(1)//1.陸地 2.飛行
            .build();

    Zombie.Builder builderD = new Zombie.Builder("name", 1,1);
    Zombie zombieD = builderD
            .setName("II型變異體")
            .setAttack(13)
            .setType(1)//1.陸地 2.飛行
            .build();

    Zombie.Builder builderE = new Zombie.Builder("name", 1,1);
    Zombie zombieE = builderE
            .setName("屍王")
            .setAttack(17)
            .setType(1)//1.陸地 2.飛行
            .build();

    Zombie.Builder builderF = new Zombie.Builder("name", 1,1);
    Zombie zombieF = builderF
            .setName("巫妖")
            .setAttack(25)
            .setType(1)//1.陸地 2.飛行
            .build();

    public int getZombieLandValue() {

        //設置陸地殭屍攻擊力
        int A = zombieA.getAttack();
        int B = zombieB.getAttack();
        int C = zombieC.getAttack();
        int D = zombieD.getAttack();
        int E = zombieE.getAttack();
        int F = zombieF.getAttack();
//        int zombieA = 5;//殭屍
//        int zombieB = 7;//大殭屍
//        int zombieC = 10;//I型變異體
//        int zombieD = 13;//II型變異體
//        int zombieE = 17;//屍王
//        int zombieF = 25;//巫妖

        //殭屍來襲的攻擊力 wave*n , n=第幾波
        landValue = (wave * 3) * A + (wave / 10 * 5) * B + (wave / 10 * 4) * C +
                (wave / 10 * 3) * D + (wave / 10 * 2) * E + (wave / 10) * F;
        return landValue;
    }

    Zombie.Builder builderFly = new Zombie.Builder("name", 1,1);
    Zombie zombieFly = builderFly
            .setName("飛行殭屍")
            .setAttack(2)
            .setType(2)//1.陸地 2.飛行
            .build();

    Zombie.Builder builderBigFly = new Zombie.Builder("name", 1,1);
    Zombie zombieBigFly = builderBigFly
            .setName("飛行大僵屍")
            .setAttack(4)
            .setType(2)//1.陸地 2.飛行
            .build();

    public int getZombieAirValue() {

        //設置飛行殭屍攻擊力
        int Fly = zombieFly.getAttack(); //飛行殭屍
        int BigFly = zombieBigFly.getAttack(); //飛行大僵屍
//        int zombieFly = 2; //飛行殭屍
//        int zombieBigFly = 4; //飛行大僵屍

        //殭屍來襲的攻擊力 wave*n , n=第幾波
        airValue = (wave - 7) * Fly + (wave - 7) / 2 * BigFly;//
        return airValue;
    }

    public void timePlus() {//每一波過去後++ ?
        wave++;
    }

    public void airStrike(Unit unit, ArrayList<Building> buildingList) throws InterruptedException {
        //殭屍空中攻擊
        Arsenal airCraft = ((Arsenal) buildingList.get(5));
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

    //殭屍地面攻擊
    public void landStrike(Unit unit, ArrayList<Building> buildingList) throws InterruptedException {
        //放入 單位(內有村民、士兵、飛機 數量)、所有建築物目前狀態
        int landValue = getZombieLandValue();//拿出這一波的殭屍攻擊力
        Arsenal army = ((Arsenal) buildingList.get(5));//拿出現在的軍營(內有士兵、飛機的生命與等級)
        if (landValue >= army.getArmyLife() * unit.getArmyCount() + unit.getVillagerCount()) {
            //1.士兵全數死亡 + 市民全數死亡
            //房屋遭受攻擊
            //士兵數量歸零，市民數量歸零
            landValue -= army.getArmyLife() * unit.getArmyCount() + unit.getVillagerCount();
            unit.setArmyCount(0);
            unit.setVillagerCount(0);
            //未增加房屋攻擊-------------------------------------------------------------------------------------------

            //如果攻擊力還有剩(11>10)
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
                if (landValue >= buildingIsAttacked.getLife()) {
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
