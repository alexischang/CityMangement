package com.company;
import static com.company.Global.*;
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
        super(HOUSE, "房屋 ", 10, new Resource(10, 0, 0), 1, new Resource(30, 15, 0), 5, 0, 2);
        //生產頻率
        setGenFrequency(8); //每八小時生產
        //自動生產消耗資源
        setEffectResource(new Resource(1, 0, 0));
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

    @Override
    public StringBuilder buildInfo(){
        StringBuilder sb = new StringBuilder();
        sb.append(getNumber() + "." + getName() +
            "\t:(建造成本: 木材 " + getBuildResource().getWood() +
            " 鋼鐵 " + getBuildResource().getSteel() +
            " 瓦斯 " + getBuildResource().getGas() +
            " 所需文明等級: " + getNeedCivilLevel() +
            " )\t功能：每" + getGenFrequency() + "小時消耗" + getEffectResource().getWood() + "木材" +
            "，產生" + this.getBuildingLevel() + "個市民");
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
            "，產生" + (this.getBuildingLevel() + 1) + "個市民");
        return sb;
    }
    
}