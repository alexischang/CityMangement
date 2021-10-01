package com.company;

/**
 * @author kisso
 */
public class AirFactory extends Building {
    private int aircraftGenRate;//飛機生產速度

    public AirFactory() {
        //設定參數，相關選項未設定(檢查是否會報錯)
        //建築編號
        this.setNumber(8);
        //建築名稱
        this.setName("飛機廠");
        //建築生命值
        setLife(50);
        //建造所需資源
        this.setBuildResource(new Resource(15, 5, 5));
        //建造所需時間
        setInitialBuildTime(2);
        //建築剩餘時間
        setBuildNeedTime(getInitialBuildTime());
        //建造需要文明等級
        this.setNeedCivilLevel(2);
        //自動生產消耗資源
        this.setEffectResource(new Resource(0, 0, 5 * getBuildingLevel()));
        //升級需要文明等級
        this.setUpNeedCivilLevel(3);
        //升級需要資源
        setUpgradeResource(new Resource(30, 15, 10));
        //升級所需時間
        setUpgradeResetTime(30);
        //升級剩餘時間
        setUpgradeNeedTime(getUpgradeResetTime());
        //生產飛機頻率
    }

    @Override
    public int getRate() {
        //飛機生產速度=房屋等級*1
        if (this.getBuildCheck() == BuildCheck.UNBUILDABLE) {
            aircraftGenRate = this.getBuildingLevel();
            return aircraftGenRate;
        }
        return 0;
    }

    // * 待驗證是否移植到建構子
    // @Override
    // public Resource getEffectResource() {
    //     //每次生產飛機所消耗的資源
    //     return new Resource(0, 0, 5 * getBuildingLevel());
    // }

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
                " )\t功能：每" + getGenFrequency() + "小時消耗" + getEffectResource().getGas() + "瓦斯" +
                "，產生" + this.getBuildingLevel() + "台戰鬥機");
    }

    //印出升級資訊
    public void printUpgrade() {
        System.out.println(getNumber() + "." + getName() +
                "\t:(升級成本: 木材 " + getUpgradeResource().getWood() +
                " 鋼鐵 " + getUpgradeResource().getSteel() +
                " 瓦斯 " + getUpgradeResource().getGas() +
                " 所需文明等級: " + getUpNeedCivilLevel() +
                " )\t升級功能:每" + getGenFrequency() + "小時消耗" + getEffectResource().getGas() * (getBuildingLevel() + 1) + "瓦斯" +
                "，產生" + (this.getBuildingLevel() + 1) + "台戰鬥機");
    }
}