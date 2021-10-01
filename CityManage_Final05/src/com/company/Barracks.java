package com.company;

/**
 * @author kisso
 */
public class Barracks extends Building {
    private int armyGenRate; //生產士兵速率


    public Barracks() {
        //建築編號
        setNumber(3);
        //建築名稱
        setName("軍營 ");
        //建築生命值
        setLife(30);
        //建造所需資源
        setBuildResource(new Resource(20, 10, 0));
        //建造所需時間
        setInitialBuildTime(2);
        //建築剩餘時間
        setBuildNeedTime(getInitialBuildTime());
        //升級所需資源
        setUpgradeResource(new Resource(30, 15, 0));
        //升級所需時間
        this.setUpgradeResetTime(5);
        //升級剩餘時間
        this.setUpgradeNeedTime(getUpgradeResetTime());
        //自動生產消耗資源
        setEffectResource(new Resource(2, 2, 0));
        //升級需要文明等級
        setUpNeedCivilLevel(2);
        //生產士兵頻率
        setGenFrequency(2);
    }

    @Override
    public int getRate() {
        if (this.getBuildCheck() == BuildCheck.UNBUILDABLE) {
            armyGenRate = getBuildingLevel() * 2 - 1;
            return armyGenRate;
        }
        return 0;
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
                " )\t功能：每" + getGenFrequency() + "小時消耗" + getEffectResource().getWood() + "木材" +
                " " + getEffectResource().getSteel() + "鋼鐵" +
                "，產生" + getBuildingLevel() + "個士兵");
    }

    //印出升級資訊
    @Override
    public void printUpgrade() {
        System.out.println(getNumber() + "." + getName() +
                "\t:(升級成本: 木材 " + getUpgradeResource().getWood() +
                " 鋼鐵 " + getUpgradeResource().getSteel() +
                " 瓦斯 " + getUpgradeResource().getGas() +
                " 所需文明等級: " + getUpNeedCivilLevel() +
                " )\t升級功能:每" + getGenFrequency() + "小時消耗" + getEffectResource().getWood() + "木材" +
                " " + getEffectResource().getSteel() + "鋼鐵" +
                "，產生" + (this.getBuildingLevel() + 1) + "個士兵");
    }
}