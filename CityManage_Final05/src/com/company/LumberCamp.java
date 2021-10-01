package com.company;

/**
 * @author kisso
 */
public class LumberCamp extends Building {
    // private final int upgradeResetTime = 30;
    private int woodRate;

    public LumberCamp() {

        //建築編號
        this.setNumber(4);
        //建築名稱
        this.setName("伐木場");
        //建築生命值
        setLife(10);
        //建造所需資源
        this.setBuildResource(new Resource(15, 0, 0));
        //建造所需時間
        setInitialBuildTime(1);
        //建築剩餘時間
        setBuildNeedTime(getInitialBuildTime());
        //升級所需資源
        this.setUpgradeResource(new Resource(30, 15, 0));
        //升級所需時間
        this.setUpgradeResetTime(30);
        //升級剩餘時間
        this.setUpgradeNeedTime(getUpgradeResetTime());
        //升級需要文明等級
        this.setUpNeedCivilLevel(2);
    }

    @Override
    public int getRate() {
        woodRate = this.getBuildingLevel() * 2 + 1;
        if (this.getBuildCheck().equals(Building.BuildCheck.UNBUILDABLE)) { //Enum可以使用.equals & ==
            woodRate++;
        }
        return woodRate;
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
                " )\t功能：採集木材效率增加 >> 市民每小時木材採集效率增加1");
    }

    //印出升級資訊
    @Override
    public void printUpgrade() {
        System.out.println(getNumber() + "." + getName() +
                "\t:(升級成本: 木材 " + getUpgradeResource().getWood() +
                " 鋼鐵 " + getUpgradeResource().getSteel() +
                " 瓦斯 " + getUpgradeResource().getGas() +
                " 所需文明等級: " + getUpNeedCivilLevel() +
                " )\t升級功能:採集木材效率增加 >> 市民每小時木材採集效率增加2");
    }
}