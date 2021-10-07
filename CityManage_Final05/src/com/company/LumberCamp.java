package com.company;
import static com.company.Global.*;
/**
 * @author kisso
 */
public class LumberCamp extends Building {
    // private final int upgradeResetTime = 30;
    private int woodRate;

    public LumberCamp() {
        super(LUMBER_CAMP, "伐木場", 10, new Resource(15, 0, 0), 1, new Resource(30, 15, 0), 30, 0, 2);
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
    public StringBuilder buildInfo(){
        StringBuilder sb = new StringBuilder();
        sb.append(getNumber() + "." + getName() +
            "\t:(建造成本: 木材 " + getBuildResource().getWood() +
            " 鋼鐵 " + getBuildResource().getSteel() +
            " 瓦斯 " + getBuildResource().getGas() +
            " 所需文明等級: " + getNeedCivilLevel() +
            " )\t功能：採集木材效率增加 >> 市民每小時木材採集效率增加1");
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
                " )\t升級功能:採集木材效率增加 >> 市民每小時木材採集效率增加2");
        return sb;
    }
}