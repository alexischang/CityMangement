package com.company;

public class Zombie {
    private String name;
    private int attack;
    private int type;
    //加入圖片屬性

    // 將建構子設定為private來強迫外界只能使用Builder來創建
    private Zombie(String name, int attack, int type) {
        this.name = name;
        this.attack = attack;
        this.type = type;//1.陸地 2.飛行
    }

    public String getName() {
        return name;
    }

    public int getAttack() {
        return attack;
    }

    public int getType() {
        return type;
    }

    public static class Builder {
        // Builder中也會有一份相同的屬性用於創建
        private String name;
        private int attack;
        private int type;
        //加入圖片屬性

        public Builder(String name, int attack, int type) {
            // 因為name跟seat不管哪一個Student來說都是必備的屬性
            // 因此我們也將他設計於Builder的建構子中
            this.name = name;
            this.attack = attack;
            this.type = type;//1.陸地 2.飛行
        }

        public Builder setName(String name){
            this.name = name;
            return this;// 利用回傳自己來達成鏈式調用的效果
        }

        public Builder setAttack(int attack) {
            this.attack = attack;
            return this;
        }

        public Builder setType(int type) {//1.陸地 2.飛行
            this.type = type;
            return this;
        }

        public Zombie build() {
            // 最後使用build方法我們就能得到Student的實體
            Zombie zombieArmy = new Zombie(name, attack, type);
            zombieArmy.name = name;// 此處只是簡易的示範，實際上用到參考資料型態時可能要考量空值問題
            zombieArmy.attack = attack;// 直接assign也並非最理想的做法
            zombieArmy.type = type;

            return zombieArmy;
        }
    }
}




