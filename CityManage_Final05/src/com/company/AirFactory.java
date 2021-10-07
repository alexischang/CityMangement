package com.company;
import static com.company.Global.*;
/**
 * @author kisso
 */
public class AirFactory extends Building {
    private int aircraftGenRate;//飛機生產速度

    public AirFactory() {
        super(AIR_FACTORY, "飛機廠", 50, new Resource(15, 5, 5), 2, new Resource(30, 15, 10), 30, 2, 3);
        //自動生產消耗資源
        this.setEffectResource(new Resource(0, 0, 5 * getBuildingLevel()));
        //生產飛機頻率
        setGenFrequency(3);
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
    public StringBuilder buildInfo(){
        StringBuilder sb = new StringBuilder();
        sb.append(getNumber() + "." + getName() +
            "\t:(建造成本: 木材 " + getBuildResource().getWood() +
            " 鋼鐵 " + getBuildResource().getSteel() +
            " 瓦斯 " + getBuildResource().getGas() +
            " 所需文明等級: " + getNeedCivilLevel() +
            " )\t功能：每" + getGenFrequency() + "小時消耗" + getEffectResource().getGas() + "瓦斯" +
            "，產生" + this.getBuildingLevel() + "台戰鬥機");
        return sb;
    }
    @Override
    public StringBuilder upgradeInfo(){
        StringBuilder sb = new StringBuilder();
            sb.append(getNumber() + "." + getName() +
                "\t:(升級成本: 木材 " + getUpgradeResource().getWood() +
                " 鋼鐵 " + getUpgradeResource().getSteel() +
                " 瓦斯 " + getUpgradeResource().getGas() +
                " 所需文明等級: " + getUpNeedCivilLevel() +
                " )\t升級功能:每" + getGenFrequency() + "小時消耗" + getEffectResource().getGas() * (getBuildingLevel() + 1) + "瓦斯" +
                "，產生" + (this.getBuildingLevel() + 1) + "台戰鬥機");
        return sb;
    }
}