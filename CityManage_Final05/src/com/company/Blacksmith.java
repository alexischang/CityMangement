package com.company;

/**
 * @author kisso
 */
public class Blacksmith extends Building {
    private int armyLife; //士兵生命值
    private int armyLevel;//士兵等級

    private int aircraftLife;//飛機生命值
    private int aircraftLevel;//飛機等級

    private boolean armyIsUpgrading = false; //判斷是不是在升級士兵科技       *****
    private boolean airCraftIsUpgrading = false; //判斷是不是在升級飛機科技   ******

    public Blacksmith() {
        //建築編號
        this.setNumber(6);
        //建築名稱
        this.setName("兵工廠");
        //建築生命值
        setLife(30);
        //建造所需資源
        this.setBuildResource(new Resource(30, 10, 0));
        //建造所需時間
        setInitialBuildTime(3);
        //建築剩餘時間
        setBuildNeedTime(getInitialBuildTime());
        //建造需要文明等級
        this.setNeedCivilLevel(2);
        //升級所需資源
        this.setUpgradeResource(new Resource(70, 40, 0));
        //升級需要文明等級
        this.setUpNeedCivilLevel(2);

        //升級所需時間
        this.setUpgradeResetTime(48);
        //升級剩餘時間
        this.setUpgradeNeedTime(getUpgradeResetTime());

        //起始士兵等級是1
        armyLevel = 1;
        //起始飛機等級是1
        aircraftLevel = 1;


    }

    //升級士兵>>士兵等級+1
    //士兵生命值改動    
    public void upgradeArmy() {
        this.armyLevel++;
        upgradeReset();
        // setUpgradeCheck(Building.UpgradeCheck.UPGRADEABLE);
        setArmyIsUpgrading(false);
    }

    public int getArmyLevel() {
        return armyLevel;
    }

    public int getArmyLife() {
        armyLife = armyLevel * 3 - 1;
        return armyLife;
    }



    //升級飛機>>飛機等級+1
    //飛機生命值改動
    public void upgradeAircraft() {
        this.aircraftLevel++;
        upgradeReset();
        // setUpgradeCheck(Building.UpgradeCheck.UPGRADEABLE);
        setAirCraftIsUpgrading(false);
    }

    public int getAircraftLevel() {
        return aircraftLevel;
    }

    public int getAircraftLife() {
        //飛機生命值=飛機等級*2
        aircraftLife = aircraftLevel * 2;
        return aircraftLife;
    }

    public boolean isArmyIsUpgrading() {
        return armyIsUpgrading;
    }

    public void setArmyIsUpgrading(boolean armyIsUpgrading) {
        this.armyIsUpgrading = armyIsUpgrading;
    }

    public boolean isAirCraftIsUpgrading() {
        return airCraftIsUpgrading;
    }

    public void setAirCraftIsUpgrading(boolean airCraftIsUpgrading) {
        this.airCraftIsUpgrading = airCraftIsUpgrading;
    }

    /**
     * 印出建造資訊
     *
     * @return 印出建造資訊
     */
    @Override
    public void printBuild() {
        System.out.println(getNumber() + "." + getName() +
                "\t:(建造成本: 木材 " + getBuildResource().getWood() +
                " 鋼鐵 " + getBuildResource().getSteel() +
                " 瓦斯 " + getBuildResource().getGas() +
                " 所需文明等級: " + getNeedCivilLevel() +
                " )\t功能：可以升級士兵及飛機");
    }

    //印出升級資訊  /*******/
    @Override
    public void printUpgrade() {
        System.out.println(getNumber() + "." + getName() +
                "\t:(升級成本: 木材 " + getUpgradeResource().getWood() +
                " 鋼鐵 " + getUpgradeResource().getSteel() +
                " 瓦斯 " + getUpgradeResource().getGas() +
                " 所需文明等級: " + getUpNeedCivilLevel() +
                " )\t升級選項:可以選擇升級士兵科技或飛機科技");
    }
}