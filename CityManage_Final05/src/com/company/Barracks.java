package com.company;
import static com.company.Global.*;
/**
 * @author kisso
 */
public class Barracks extends Building {
    private int armyGenRate; //生產士兵速率


    public Barracks() {
        super(BARRACKS, "軍營 ", 30, new Resource(20, 10, 0), 2, new Resource(30, 15, 0), 5, 0, 2);
        setEffectResource(new Resource(2, 2, 0));
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
    public StringBuilder buildInfo(){
        StringBuilder sb = new StringBuilder();
        sb.append(getNumber() + "." + getName() +
            "\t:(建造成本: 木材 " + getBuildResource().getWood() +
            " 鋼鐵 " + getBuildResource().getSteel() +
            " 瓦斯 " + getBuildResource().getGas() +
            " 所需文明等級: " + getNeedCivilLevel() +
            " )\t功能：每" + getGenFrequency() + "小時消耗" + getEffectResource().getWood() + "木材" +
            " " + getEffectResource().getSteel() + "鋼鐵" +
            "，產生" + getBuildingLevel() + "個士兵");
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
                " )\t升級功能:每" + getGenFrequency() + "小時消耗" + getEffectResource().getWood() + "木材" +
                " " + getEffectResource().getSteel() + "鋼鐵" +
                "，產生" + (this.getBuildingLevel() + 1) + "個士兵");
        return sb;
    }
}