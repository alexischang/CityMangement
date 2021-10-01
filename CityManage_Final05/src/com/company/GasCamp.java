package com.company;

/**
 * @author kisso
 */
public class GasCamp extends Building {
    private int gasRate;

    public GasCamp() {

        //建築編號
        this.setNumber(7);
        //建築名稱
        this.setName("瓦斯廠");
        //建築生命值
        setLife(20);
        //建造所需資源
        this.setBuildResource(new Resource(15, 5, 0));
        //建造所需時間
        setInitialBuildTime(1);
        //建築剩餘時間
        setBuildNeedTime(getInitialBuildTime());
        //建造需要文明等級
        this.setNeedCivilLevel(2);
        //升級所需資源
        this.setUpgradeResource(new Resource(40, 20, 0));
        //升級所需時間
        this.setUpgradeResetTime(30);
        //升級剩餘時間
        this.setUpgradeNeedTime(getUpgradeResetTime());
        //升級需要文明等級
        this.setUpNeedCivilLevel(3);
    }

    @Override
    public int getRate() {
        if (getBuildCheck() == BuildCheck.UNBUILDABLE) {
            gasRate = this.getBuildingLevel() + 4;
            return gasRate;
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
                " )\t功能：每小時瓦斯採集效率增加5");
    }

    //印出升級資訊
    @Override
    public void printUpgrade() {
        System.out.println(getNumber() + "." + getName() +
                "\t:(升級成本: 木材 " + getUpgradeResource().getWood() +
                " 鋼鐵 " + getUpgradeResource().getSteel() +
                " 瓦斯 " + getUpgradeResource().getGas() +
                " 所需文明等級: " + getUpNeedCivilLevel() +
                " )\t升級功能:生產瓦斯效率增加 >> 每小時瓦斯生產效率增加1");
    }
}