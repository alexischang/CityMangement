package com.company;

/**
 * @author kisso
 */
public class House extends Building {
    /**
     * 升級所需時間
     * 村民生命值
     * 村民生產速度
     */
    private int villagerGenRate;
    private int villagerLife;


    public House() {
        //建築編號
        setNumber(1);
        //建築名稱
        setName("房屋 ");
        //建築生命值
        setLife(10);
        //建造所需資源
        setBuildResource(new Resource(10, 0, 0));
        //建造所需時間
        setInitialBuildTime(1);
        //建築剩餘時間
        setBuildNeedTime(getInitialBuildTime());
        //升級所需資源
        setUpgradeResource(new Resource(30, 15, 0));
        //升級所需時間
        setUpgradeResetTime(5);
        //升級剩餘時間
        setUpgradeNeedTime(getUpgradeResetTime());
        //升級需要文明等級
        setUpNeedCivilLevel(2);


        //自動生產消耗資源
        setEffectResource(new Resource(1, 0, 0));
        //生產頻率
        setGenFrequency(8); //每八小時生產
        this.villagerLife = 1;

    }


    @Override
    public int getRate() { //市民生產速率
        if (this.getBuildCheck() == BuildCheck.UNBUILDABLE) {
            villagerGenRate = this.getBuildingLevel() * 2 - 1;
            return villagerGenRate;
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
                "，產生" + this.getBuildingLevel() + "個市民");
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
                "，產生" + (this.getBuildingLevel() + 1) + "個市民");
    }
}