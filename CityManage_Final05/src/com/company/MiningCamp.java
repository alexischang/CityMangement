package com.company;
import static com.company.Global.*;
/**
 * @author kisso
 */
public class MiningCamp extends Building {
    // private final int upgradeResetTime = 30;
    private int steelRate;

    public MiningCamp() {
        super(MINING_CAMP, "煉鋼廠", 10, new Resource(15, 5, 0), 1, new Resource(30, 15, 0), 30, 0, 2);
    }

    @Override
    public int getRate() {
        steelRate = this.getBuildingLevel();
        if (this.getBuildCheck() == BuildCheck.UNBUILDABLE) {
            steelRate++;
        }
        return steelRate;
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
            " )\t功能：採集鋼鐵效率增加 >> 市民每小時鋼鐵採集效率增加1");
            return sb;
    }
    
    @Override
    public StringBuilder upgradeInfo(){
        StringBuilder sb = new StringBuilder();
            sb.append(getNumber() + "." + getName() +
                "\t:(建造成本: 木材 " + getBuildResource().getWood() +
                " 鋼鐵 " + getBuildResource().getSteel() +
                " 瓦斯 " + getBuildResource().getGas() +
                " 所需文明等級: " + getNeedCivilLevel() +
                " )\t功能：採集鋼鐵效率增加 >> 市民每小時鋼鐵採集效率增加1");
        return sb;
    }
}