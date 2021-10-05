package com.company;

import java.util.ArrayList;

/**
 * 遊戲狀態列表(數值儲存於此，取用於此)
 */
public class Status {
    private int time;
    private Resource resource = new Resource();
    private Unit unit = new Unit();
    private ZombieArmy zombie = new ZombieArmy();
    private ArrayList<Building> buildingList = new ArrayList<Building>();
    private Input input = new Input();
    // private int buildPeople;
    private boolean isVillagerEnough = true;

    public Status() {
        buildingList.add(new House());
        buildingList.add(new University());
        buildingList.add(new Barracks());
        buildingList.add(new LumberCamp());
        buildingList.add(new MiningCamp());
        buildingList.add(new Blacksmith());
        buildingList.add(new GasCamp());
        buildingList.add(new AirFactory());
    }

    public int getTime() {
        return time;
    }

    public Resource getResource() {
        return resource;
    }

    public Unit getUnit() {
        return unit;
    }

    public ZombieArmy getZombie() {
        return zombie;
    }

    public ArrayList<Building> getBuildingList() {
        return buildingList;
    }

    public Input getInput() {
        return input;
    }

    /**
     * 過一個小時
     */
    public void nextHour() throws InterruptedException {
        //加一小
        time++;
        //增加木材
        woodIncrease();
        //增加鋼鐵
        steelIncrease();
        //增加瓦斯
        gasIncrease();
        //增加市民
        villagerIncrease();
        //增加士兵
        armyIncrease();
        //增加飛機
        airCraftIncrease();
        //減少建築所需時間
        reduceBuildingNeedTime();
        //減少建築所需升級時間
        reduceUpgradeNeedTime();
    }

    /**
     * 經過指定小時
     *
     * @param hr//指定小時
     */
    public void nextHours(int hr) throws InterruptedException {
        int beforeWood = resource.getWood();
        int beforeSteel = resource.getSteel();
        int beforeGas = resource.getGas();
        int beforeVillager = unit.getVillagerCount() + resource.getSteelPeople() + resource.getWoodPeople();
        int beforeArmy = unit.getArmyCount();
        int beforeAircraft = unit.getAircraftCount();
        for (int i = 0; i < hr; i++) {
            nextHour();
            printWarning();
            if (time % 16 == 0) {
                villagerReset();
                zombie.timePlus();
                if (zombie.getWave() > 7) { //第七波之後才有可能有飛行殭屍
                    zombie.airStrike(unit, buildingList);
                }
                Thread.sleep(1200);
                zombie.landStrike(unit, buildingList);
                Thread.sleep(1500);
                if (!isGameOver()) {
                    System.out.println("抵禦成功! 請重新分配市民\n");
                    distribute();
                } else {
                    break;
                }
            }
        }
        int differenceBetweenWood = resource.getWood() - beforeWood;
        int differenceBetweenSteel = resource.getSteel() - beforeSteel;
        int differenceBetweenGas = resource.getGas() - beforeGas;
        int differenceBetweenVillager = unit.getVillagerCount() + resource.getWoodPeople() + resource.getSteelPeople() - beforeVillager;
        int differenceBetweenArmy = unit.getArmyCount() - beforeArmy;
        int differenceBetweenAircraft = unit.getAircraftCount() - beforeAircraft;
        if (differenceBetweenWood > 0) {
            System.out.println("獲得" + differenceBetweenWood + "木材");
        }
        if (differenceBetweenSteel > 0) {
            System.out.println("獲得" + differenceBetweenSteel + "鋼鐵");
        }
        if (differenceBetweenGas > 0) {
            System.out.println("獲得" + differenceBetweenGas + "瓦斯");
        }
        if (differenceBetweenVillager > 0) {
            System.out.println("閒置市民增加" + differenceBetweenVillager + "位");
        } else if (differenceBetweenVillager < 0) {
            System.out.println("有" + (-1) * differenceBetweenVillager + "位可憐的市民被殭屍咬死了");
        }
        if (differenceBetweenArmy > 0) {
            System.out.println("已產出" + differenceBetweenArmy + "名士兵");
        } else if (differenceBetweenArmy < 0) {
            System.out.println("有" + (-1) * differenceBetweenArmy + "名英勇的士兵犧牲了");
        }
        if (differenceBetweenAircraft > 0) {
            System.out.println("已產出" + differenceBetweenAircraft + "架飛機");
        } else if (differenceBetweenAircraft < 0) {
            System.out.println("有" + (-1) * differenceBetweenAircraft + "架飛機被摧毀了");
        }
    }

    /**
     * 木材增加
     */
    public void woodIncrease() {
        int addingWoodNumber = resource.getWoodPeople() * ((LumberCamp) buildingList.get(3)).getRate();
        if (addingWoodNumber > 0) {
            resource.addWood(addingWoodNumber);
        }
    }

    /**
     * 鋼鐵增加
     */
    public void steelIncrease() {
        int addingSteelNumber = resource.getSteelPeople() * ((MiningCamp) buildingList.get(4)).getRate();
        if (addingSteelNumber > 0) {
            resource.addSteel(addingSteelNumber);
        }
    }

    /**
     * 瓦斯增加
     */
    public void gasIncrease() {
        if (buildingList.get(6).isOnOff() &&
                buildingList.get(6).getBuildCheck() == Building.BuildCheck.UNBUILDABLE) {
            int addingGasNumber = ((GasCamp) buildingList.get(6)).getRate();
            resource.addGas(addingGasNumber);
        }
    }

    /**
     * 市民增加
     */
    public void villagerIncrease() {
        House house = (House) buildingList.get(0);
        int rate = house.getRate();
        int frequency = house.getGenFrequency();
        if (isEnough(house.getEffectResource()) &&
                house.isOnOff() &&
                house.getBuildCheck() == Building.BuildCheck.UNBUILDABLE &&
                (time - house.getBuildTime()) % frequency == 0) {
            unit.addVillager(rate);
            resource.reduceResource(house.getEffectResource());
            house.setBuildTime(time);
        }
    }

    /**
     * 士兵增加
     */
    public void armyIncrease() {
        Barracks barracks = (Barracks) buildingList.get(2);
        int rate = barracks.getRate();
        int frequency = barracks.getGenFrequency();
        if (isEnough(barracks.getEffectResource()) &&
                barracks.isOnOff() &&
                barracks.getBuildCheck() == Building.BuildCheck.UNBUILDABLE &&
                (time - barracks.getBuildTime()) % frequency == 0) {
            unit.addArmy(rate);
            resource.reduceResource(barracks.getEffectResource());
            barracks.setBuildTime(time);
        }
    }

    /**
     * 飛機增加
     */
    public void airCraftIncrease() {
        AirFactory airFactory = (AirFactory) buildingList.get(7);
        int rate = airFactory.getRate();
        int frequency = airFactory.getGenFrequency();
        if (isEnough(new Resource(0, 0, 5 * airFactory.getBuildingLevel())) &&
                airFactory.isOnOff() &&
                airFactory.getBuildCheck() == Building.BuildCheck.UNBUILDABLE &&
                (time - airFactory.getBuildTime()) % frequency == 0) {
            unit.addAircraft(rate);
            resource.reduceResource(airFactory.getEffectResource());
            airFactory.setBuildTime(time);
        }
    }

    /**
     * 減少建築所需時間
     */
    public void reduceBuildingNeedTime() {
        for (Building building : buildingList) {
            if (building.getBuildCheck() == Building.BuildCheck.BUILDGOINGON) {
                building.reduceBuildNeedTime();
                if (building.getBuildNeedTime() == 0) {
                    building.buildComplete(time); //build time存進蓋好的時間
                    unit.addVillager(1);
                    System.out.println(building.getName() + "已建造完畢");
                }
            }
        }
    }

    /**
     * 減少升級所需時間
     */
    public void reduceUpgradeNeedTime() {
        for (int i = 0; i < buildingList.size(); i++) {
            Building building = buildingList.get(i);
            if (building.getUpgradeCheck() == Building.UpgradeCheck.UPGRADING) {
                building.reduceUpgradeNeedTime();
                if (building.getUpgradeNeedTime() == 0 && i != 5) {
                    building.upgrade();
                    System.out.println(building.getName() + "已升級完畢\n");
                } else if (building.getUpgradeNeedTime() == 0 && i == 5) { //arsenal 有兩個科技故單獨處理
                    if (((Blacksmith) building).isArmyIsUpgrading()) {
                        ((Blacksmith) building).upgradeArmy();
                        System.out.println("士兵科技已升級完畢\n");
                    }
                    if (((Blacksmith) building).isAirCraftIsUpgrading()) {
                        ((Blacksmith) building).upgradeAircraft();
                        System.out.println("飛機科技已升級完畢\n");
                    }
                }
            }
        }
    }

    /**
     * (主動)分配人力
     *
     * @param
     * @param
     * @return
     */
    public void distribute() {
        villagerReset();
        System.out.println("\n可分配人員:" + unit.getVillagerCount() + "\n");
        System.out.println("你想派幾個人去採集木頭呢?:");
        int woodPeople = input.numberInput(0, 99999);
        System.out.println("你想派幾個人去採集鋼鐵呢?:");
        int steelPeople = input.numberInput(0, 99999);
        if (unit.getVillagerCount() >= woodPeople + steelPeople) {
            // resource.addWoodPeople(woodPeople);
            resource.setWoodPeople(woodPeople);
            resource.setSteelPeople(steelPeople);
            unit.addVillager((woodPeople + steelPeople) * -1);
            System.out.println("是的!船長~\n");
        } else {
            System.out.println("你數學好像不太好喔...請重新分配\n");
            distribute();
        }
    }

    /**
     * (主動)開關建築
     *
     * @param whichBuilding
     * @return
     */
    public boolean openBuilding(int whichBuilding, boolean isOn) {
        boolean isOpened = false;
        Building building = buildingList.get(whichBuilding - 1);
        if (building.getBuildCheck() == Building.BuildCheck.UNBUILDABLE) {
            building.setOnOff(isOn);
            building.setBuildTime(time);
            isOpened = true;
        } else {
            System.out.println("你沒有此建築");
        }
        return isOpened;
    }

    /**
     * 召集市民回歸
     */
    public void villagerReset() {
        unit.addVillager(resource.getWoodPeople() + resource.getSteelPeople());
        resource.setSteelPeople(0);
        resource.setWoodPeople(0);
    }

    /**
     * (主動)選擇建造
     *
     * @param //建築物編號
     * @return//是否可以建造(並變成建造中)
     */
    public boolean build(int opt) {
        int civilLevel = ((University) buildingList.get(1)).getCivilLevel();
        boolean isBuildable = false;
        isVillagerEnough = true;
        if (civilLevel >= buildingList.get(opt - 1).getNeedCivilLevel()) {
            if (buildingList.get(opt - 1).getBuildCheck() == Building.BuildCheck.BUILDABLE) {
                if (isEnough(buildingList.get(opt - 1).getBuildResource())) {
                    if (unit.getVillagerCount() > 0) {
                        unit.addVillager(-1);
                        resource.reduceResource(buildingList.get(opt - 1).getBuildResource());
                        buildingList.get(opt - 1).setBuildCheck(Building.BuildCheck.BUILDGOINGON);
                        isBuildable = true;
                    } else {
                        isVillagerEnough = false;
                        System.out.println("閒置人員不足，建造建築至少需要一人");
                        distribute();
                    }
                } else {
                    System.out.print("資源不足  ");
                }
            } else {
                System.out.print("建造中或已建造  ");
            }
        } else {
            System.out.print("文明等級不足  ");
        }
        return isBuildable;
    }

    /**
     * (主動)選擇升級
     *
     * @param //建築物編號
     * @return //是否可以升級(變成升級中)
     */
    public boolean upgrade(int opt) {
        int civilLevel = ((University) buildingList.get(1)).getCivilLevel();
        boolean isUpgradable = false;
        if (opt == 2 && civilLevel == 3) {
            System.out.println("文明等級已達上限，無法升級");
        } else if (buildingList.get(opt - 1).getBuildCheck() != Building.BuildCheck.UNBUILDABLE) {
            System.out.println("無此建築");
        } else if (buildingList.get(opt - 1).getUpgradeCheck() != Building.UpgradeCheck.UPGRADEABLE) {
            System.out.println("升級中、無法再升級");
        } else if (civilLevel < buildingList.get(opt - 1).getUpNeedCivilLevel()) {
            System.out.println("文明等級不足");
        } else if (!isEnough(buildingList.get(opt - 1).getUpgradeResource())) {
            System.out.println("資源不足");
        } else if (opt == 6) {
            resource.reduceResource(buildingList.get(opt - 1).getUpgradeResource());
            buildingList.get(opt - 1).setUpgradeCheck(Building.UpgradeCheck.UPGRADING);
            isUpgradable = true;
            System.out.println("1:升級士兵 2:升級戰鬥機");
            int choose = input.numberInput(1, 2);
            if (choose == 1) {
                ((Blacksmith) buildingList.get(opt - 1)).setArmyIsUpgrading(true);
            } else {
                ((Blacksmith) buildingList.get(opt - 1)).setAirCraftIsUpgrading(true);
            }
        } else {
            resource.reduceResource(buildingList.get(opt - 1).getUpgradeResource());
            buildingList.get(opt - 1).setUpgradeCheck(Building.UpgradeCheck.UPGRADING);
            isUpgradable = true;
        }
        return isUpgradable;
    }

    /**
     * 是否夠資源
     *
     * @param r 資源
     * @return 夠或不夠
     */
    public boolean isEnough(Resource r) {
        if (resource.getWood() < r.getWood() || resource.getSteel() < r.getSteel() || resource.getGas() < r.getGas()) {
            return false;
        }
        return true;
    }

    /**
     * 遊戲結束之判定
     *
     * @return
     */
    public boolean isGameOver() {
        boolean isOver = true;
        if (resource.getSteelPeople() + resource.getWoodPeople() + unit.getVillagerCount() > 0) {  /*有市民就還沒輸*/
            isOver = false;
        }
        for (Building building : buildingList) {
            if (building.getBuildCheck() != Building.BuildCheck.BUILDABLE) { /*只要有一個建築還在就不算輸*/
                isOver = false;
            }
        }
        return isOver;
    }

    /**
     * 建築中人數
     *
     * @return
     */
    public int getBuildPeople() {
        int result = 0;
        for (Building building : buildingList) {
            if (building.getBuildCheck() == Building.BuildCheck.BUILDGOINGON) {
                result++;
            }
        }
        return result;
    }

    /**
     * 市民是否足夠建築
     *
     * @return
     */
    public boolean isVillagerEnough() {
        return isVillagerEnough;
    }

    /**
     * 顯示遊戲手冊
     */
    public void showManual() {
        System.out.println("\n《遊戲指令操作手冊》");
        System.out.println("1: 顯示城市狀態");
        System.out.println("2: 顯示建築狀態");
        System.out.println("3: 分配採集資源人數");
        System.out.println("4: 建造建築物");
        System.out.println("5: 升級建築物");
        System.out.println("6: 開關建築");
        System.out.println("7: 時間前進");
    }

    /**
     * 顯示建築功能
     */
    public void showBuilding() {
        System.out.println("《建築物編號和功能介紹》");
        for (int i = 0; i < buildingList.size(); i++) {
            buildingList.get(i).printBuild();
        }
    }

    /**
     * 顯示建築升級功能
     */
    public void showUpgrade() {
        System.out.println("《建築物編號和升級功能介紹》");
        for (int i = 0; i < buildingList.size(); i++) {
            buildingList.get(i).printUpgrade();
        }

    }

    /**
     * 顯示當前建築狀態
     */
    public void showBuildingStatus() {
        System.out.println("《目前建築狀態》");
        for (int i = 0; i < buildingList.size(); i++) {
            Building build = buildingList.get(i);
            System.out.print("編號:" + build.getNumber() +
                    "\t建築名稱:" + build.getName() +
                    "\t建築等級:" + build.getBuildingLevel() +
                    "\t建築狀態:" + build.getBuildCheck().getBuildChecking() +
                    "\t 建築剩餘時間:" + build.getBuildNeedTime() +
                    "\t升級狀態:" + build.getUpgradeCheck().getBuildChecking() +
                    "\t升級剩餘時間:" + build.getUpgradeNeedTime());
            if (i == 0 || i == 2 || i == 7) {
                System.out.print("\t是否開啟:" + build.isOnOff());
                if (build.getBuildCheck() == Building.BuildCheck.UNBUILDABLE && build.isOnOff() == true) {
                    System.out.print("\t 產出倒數時間:" + (build.getBuildTime() + build.getGenFrequency() - time));
                }
                System.out.println();
            } else {
                System.out.println();
            }
        }
    }

    /**
     * 顯示當前城市狀態
     */
    public void showStatus() {
        System.out.println("\n《目前城市狀態》");
        System.out.println("時間: 第" + time + "小時" +
                "\t文明等級: " + ((University) buildingList.get(1)).getCivilLevel());
        System.out.println("市民總數: " +
                (unit.getVillagerCount() +
                        resource.getWoodPeople() +
                        resource.getSteelPeople() +
                        getBuildPeople()) +
                "\t市民生產速率: " + ((House) buildingList.get(0)).getRate());
        System.out.println("總士兵數: " + unit.getArmyCount() +
                "\t士兵生產速率: " + ((Barracks) buildingList.get(2)).getRate() +
                "\t士兵等級: " + ((Blacksmith) buildingList.get(5)).getArmyLevel() +
                "\t士兵數值: " + ((Blacksmith) buildingList.get(5)).getArmyLife() +
                "\n總飛機數: " + unit.getAircraftCount() +
                "\t飛機生產速率: " + ((AirFactory) buildingList.get(7)).getRate() +
                "\t飛機等級: " + ((Blacksmith) buildingList.get(5)).getAircraftLevel() +
                "\t飛機數值: " + ((Blacksmith) buildingList.get(5)).getAircraftLife());
        System.out.println("\n閒置市民: " + unit.getVillagerCount() +
                "\t建造人數: " + getBuildPeople() +
                "\n現有木材: " + resource.getWood() +
                "\t採集木材人數: " + resource.getWoodPeople() + "人" +
                "\t採集木材效率: (+" + ((LumberCamp) buildingList.get(3)).getRate() + "木材 / 每小時)");
        System.out.println("現有鋼鐵: " + resource.getSteel() +
                "\t採集鋼鐵人數: " + resource.getSteelPeople() + "人" +
                "\t採集鋼鐵效率: (+" + ((MiningCamp) buildingList.get(4)).getRate() + "鋼鐵 / 每小時)");
        System.out.println("現有瓦斯: " + resource.getGas() +
                "\t生產瓦斯效率: (+" + ((GasCamp) buildingList.get(6)).getRate() + "瓦斯 / 每小時)");
        System.out.println();
    }

    /**
     * 提供殭屍來襲之警示
     *
     * @throws InterruptedException
     */
    public void printWarning() throws InterruptedException {
        if (time % 16 == 5) {
            Thread.sleep(1200);
            System.out.println("適時的調配資源分布，會更有效對付邪惡的殭屍們\n");
        }
        if (time % 16 == 10) {
            Thread.sleep(1200);
            System.out.println("城市外面似乎有什麼騷動.....\n");
        }
        if (time % 16 == 13) {
            Thread.sleep(1200);
            System.out.println("那是什麼.....怎麼越來越近....\n");
        }
        if (time % 16 == 14) {
            Thread.sleep(1200);
            System.out.println(".........(奔跑聲)..........\n");
        }
        if (time == 15) {
            Thread.sleep(1500);
            System.out.println("來了，牠們來了，快點進入戰備狀態\n");
        }
        if (time % 16 == 15 && time > 15 && time < 100) {
            Thread.sleep(1500);
            System.out.println("牠們回來了，快點進入戰備狀態\n");
        }
        if (time % 16 == 15 && time > 100) {
            Thread.sleep(1500);
            System.out.println("我們不是牠們的奴隸，快點進入戰備狀態\n");
        }
    }
}
