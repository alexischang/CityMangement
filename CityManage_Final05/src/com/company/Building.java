package com.company;

public class Building {
    //建築物基本屬性
    private int number;
    private String name;
    private int buildingLevel;
    private int life;
    //建造相關資訊屬性
    private Resource buildResource;
    private BuildCheck buildCheck;//enum
    private int buildNeedTime;
    private int initialBuildTime;
    private int buildTime;
    private int needCivilLevel;
    //升級相關屬性
    private Resource upgradeResource;
    private UpgradeCheck upgradeCheck;//enum
    private int upgradeNeedTime;
    private int upgradeResetTime;
    private int upNeedCivilLevel;

    //建築物功能屬性
    private boolean onOff;
    private Resource effectResource;
    private int genFrequency;

    public Building() {
        setBuildingLevel(1);
        setBuildCheck(BuildCheck.BUILDABLE);
        setNeedCivilLevel(1);
        setBuildTime(-1);
        setUpgradeCheck(UpgradeCheck.NOTUPGRADEABLE);
        setOnOff(false);
    }

    /**
     * 可否建造、升級檢查
     */
    public enum BuildCheck {
        BUILDABLE("尚未建造"),
        BUILDGOINGON("正在建造"),
        UNBUILDABLE("已建完畢");
        private String buildChecking;

        private BuildCheck(String buildChecking) {
            this.buildChecking = buildChecking;
        }

        public String getBuildChecking() {
            return buildChecking;
        }
    }

    public enum UpgradeCheck {
        UPGRADEABLE("可升級"),
        UPGRADING("升級中"),
        NOTUPGRADEABLE("不可升級");
        private String updateChecking;

        private UpgradeCheck(String updateChecking) {
            this.updateChecking = updateChecking;
        }

        public String getBuildChecking() {
            return updateChecking;
        }
    }

    /**
     * 建築需要時間 升級需要時間-1
     */
    public void reduceBuildNeedTime() {
        buildNeedTime--;
    }

    public void reduceUpgradeNeedTime() {
        upgradeNeedTime--;
    }

    //升級完成
    public void upgrade() {
        buildingLevel++;
        upgradeReset();
    }

    //可重複升級
    public void upgradeReset() {
        setUpgradeNeedTime(upgradeResetTime);
        setUpgradeCheck(UpgradeCheck.UPGRADEABLE);
        //可更改下一次升級資源
    }

    public void buildComplete(int buildTime) {
        setBuildCheck(BuildCheck.UNBUILDABLE);
        setUpgradeCheck(UpgradeCheck.UPGRADEABLE);
        setOnOff(true);
        setBuildTime(buildTime);
    }

    //被摧毀後可重新建造
    public void buildReset() {
        setBuildCheck(BuildCheck.BUILDABLE);
        setUpgradeCheck(UpgradeCheck.NOTUPGRADEABLE);
        setBuildNeedTime(initialBuildTime);
        setOnOff(false);
    }

    //生產力相關才有此功能
    //房屋 軍營 飛機工廠
    //伐木場 煉鋼廠 瓦斯場
    public int getRate() {
        return 0;
    }

    //印出建造，升級資訊
    public void printBuild() {

    }

    public void printUpgrade() {

    }


    /**
     * Getter & Setter
     */

    public int getGenFrequency() {
        return genFrequency;
    }

    public void setGenFrequency(int frequency) {
        this.genFrequency = frequency;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBuildingLevel() {
        return buildingLevel;
    }

    public void setBuildingLevel(int buildingLevel) {
        this.buildingLevel = buildingLevel;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public Resource getBuildResource() {
        return buildResource;
    }

    public void setBuildResource(Resource buildResource) {
        this.buildResource = buildResource;
    }

    public BuildCheck getBuildCheck() {
        return buildCheck;
    }

    public void setBuildCheck(BuildCheck buildCheck) {
        this.buildCheck = buildCheck;
    }

    public int getInitialBuildTime() {
        return initialBuildTime;
    }

    public void setInitialBuildTime(int initialBuildTime) {
        this.initialBuildTime = initialBuildTime;
    }

    public int getBuildNeedTime() {
        return buildNeedTime;
    }

    public void setBuildNeedTime(int buildNeedTime) {
        this.buildNeedTime = buildNeedTime;
    }

    public int getBuildTime() {
        return buildTime;
    }

    public void setBuildTime(int buildTime) {
        this.buildTime = buildTime;
    }

    public int getNeedCivilLevel() {
        return needCivilLevel;
    }

    public void setNeedCivilLevel(int needCivilLevel) {
        this.needCivilLevel = needCivilLevel;
    }

    public Resource getUpgradeResource() {
        return upgradeResource;
    }

    public UpgradeCheck getUpgradeCheck() {
        return upgradeCheck;
    }

    public void setUpgradeCheck(UpgradeCheck upgradeCheck) {
        this.upgradeCheck = upgradeCheck;
    }

    public void setUpgradeResource(Resource upgradeResource) {
        this.upgradeResource = upgradeResource;
    }

    public int getUpNeedCivilLevel() {
        return upNeedCivilLevel;
    }

    public void setUpNeedCivilLevel(int upNeedCivilLevel) {
        this.upNeedCivilLevel = upNeedCivilLevel;
    }

    public int getUpgradeNeedTime() {
        return upgradeNeedTime;
    }

    public void setUpgradeNeedTime(int upgradeNeedTime) {
        this.upgradeNeedTime = upgradeNeedTime;
    }

    public int getUpgradeResetTime() {
        return upgradeResetTime;
    }

    public void setUpgradeResetTime(int upgradeResetTime) {
        this.upgradeResetTime = upgradeResetTime;
    }

    public boolean isOnOff() {
        return onOff;
    }

    public void setOnOff(boolean onOff) {
        this.onOff = onOff;
    }

    public Resource getEffectResource() {
        return effectResource;
    }

    public void setEffectResource(Resource effectResource) {
        this.effectResource = effectResource;
    }

}

    
    

    
    
  
    
    
