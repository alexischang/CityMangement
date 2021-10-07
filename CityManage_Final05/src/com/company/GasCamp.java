package com.company;
import static com.company.Global.*;
/**
 * @author kisso
 */
public class GasCamp extends Building {
    private int gasRate;

    public GasCamp() {
        super(GAS_CAMP, "瓦斯廠", 20, new Resource(15, 5, 0), 1, new Resource(40, 20, 0), 30, 2, 3);
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
    public StringBuilder buildInfo(){
        StringBuilder sb = new StringBuilder();
        sb.append(getNumber() + "." + getName() +
            "\t:(建造成本: 木材 " + getBuildResource().getWood() +
            " 鋼鐵 " + getBuildResource().getSteel() +
            " 瓦斯 " + getBuildResource().getGas() +
            " 所需文明等級: " + getNeedCivilLevel() +
            " )\t功能：每小時瓦斯採集效率增加5");
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
                " )\t升級功能:生產瓦斯效率增加 >> 每小時瓦斯生產效率增加1");
        return sb;
    }
}