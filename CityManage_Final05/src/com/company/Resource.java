package com.company;

/**
 * 建造一個起始資源類別
 */
public class Resource {
    private int wood; //木材量
    private int steel; //鋼鐵量
    private int gas; //瓦斯量
    private int woodPeople = 0;//採集木材人數
    private int steelPeople = 0;//採集鋼鐵人數

    public Resource(int wood, int steel, int gas) {
        this.gas = gas;
        this.steel = steel;
        this.wood = wood;
    }

    public Resource() {

    }


    public int getWood() {
        return wood;
    }

    // public void setWood(int wood) {
    //     this.wood = wood;
    // }

    public int getSteel() {
        return steel;
    }

    // public void setSteel(int steel) {
    //     this.steel = steel;
    // }

    public int getGas() {
        return gas;
    }

    // public void setGas(int gas) {
    //     this.gas = gas;
    // }

    public int getWoodPeople() {
        return woodPeople;
    }

    public void setWoodPeople(int woodPeople) {
        this.woodPeople = woodPeople;
    }

    public int getSteelPeople() {
        return steelPeople;
    }

    public void setSteelPeople(int steelPeople) {
        this.steelPeople = steelPeople;
    }


    public void addGas(int value) {
        gas += value;
        // if (gas == 0 && value < 0) {
        //     System.out.println("已無瓦斯");
        // }
    }

    public void addWood(int value) {
        wood += value;
        // if (wood == 0 && value < 0) {
        //     System.out.println("已無木材");
        // }
    }

    public void addSteel(int value) {
        steel += value;
        // if (steel == 0 && value < 0) {
        //     System.out.println("已無鋼鐵");
        // }
    }

    public void addWoodPeople(int value) {
        woodPeople += value;
    }

    public void addSteelPeople(int value) {
        steelPeople += value;
    }

    // public void addResource(Resource resource) {
    //     wood += resource.wood;
    //     steel += resource.steel;
    //     gas += resource.gas;
    // }

    public void reduceResource(Resource resource) {
        wood -= resource.wood;
        steel -= resource.steel;
        gas -= resource.gas;
    }

    public String toString() {
        return "當前資源:  木材:" + wood + " 鋼鐵:" + steel + " 瓦斯:" + gas;
    }
}
