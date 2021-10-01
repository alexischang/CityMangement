package com.company;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Status newStatus = new Status();
        Input input = newStatus.getInput();

        // 歡迎詞
        System.out.println("市長你好!歡迎來到愛好世界和平城市~\n");
        Thread.sleep(1000);
        System.out.println("本城市需要市長你跟大家一起努力，才能共同抵抗恐怖的殭屍大軍!\n");
        Thread.sleep(1000);
        System.out.println("殭屍每16小時會來襲一次，記得在此之前先做好準備\n");
        Thread.sleep(1000);
        System.out.println("如果所有人民都死了，建築也被破壞殆盡的話，就遊戲結束囉!\n");
        Thread.sleep(1000);
        System.out.println("英明的市長，快來領導人民，建築你們的城市吧!\n");
        Thread.sleep(1000);
        System.out.println("準備好了嗎 孩子們?? o(-`д´- ｡)\n");
        Thread.sleep(600);
        System.out.println("是的船長~~ o(-`д´- ｡)\n");
        Thread.sleep(600);
        System.out.println("太小聲囉~~  o(-`д´- ｡)\n");
        Thread.sleep(600);
        System.out.println("是的船長 o(-`д´- ｡)\n");
        Thread.sleep(600);
        System.out.println("喔喔喔喔喔喔喔喔喔喔喔喔喔喔喔喔喔喔喔喔喔~~");
        Thread.sleep(1000);

        // 設置初始村名數量、初始時間、初始士兵數量、殭屍

        // 顯示狀態
        newStatus.showStatus();

        // 判定是否還有村民or建築物，若均歸零則結束遊戲
        while (!newStatus.isGameOver()) {
            // 顯示操作手冊
            Thread.sleep(500);
            newStatus.showManual();
            System.out.print("\n市長請下令:");
            // 輸入指令(統一轉換為英文小寫)
            int option = input.numberInput(1, 7);
            switch (option) {
                case 1: {
                    newStatus.showStatus();
                    Thread.sleep(2000);
                    break;
                }
                case 2: {
                    newStatus.showBuildingStatus();
                    Thread.sleep(2000);
                    break;
                }
                case 3: {
                    newStatus.distribute();
                    break;
                }
                case 4: {
                    newStatus.showBuilding();
                    int buildOption;
                    while (true) {
                        System.out.println("\n閒置村民:" + newStatus.getUnit().getVillagerCount() + "人" + "\t\t當前文明等級:" + ((University) newStatus.getBuildingList().get(1)).getCivilLevel());
                        System.out.println(newStatus.getResource());
                        System.out.print("請選擇要建造的建築物:     (輸入0退出)\n");
                        buildOption = input.numberInput(0, 8);
                        if (buildOption == 0) {
                            break;
                        }
                        if (newStatus.build(buildOption)) {
                            System.out.println("開始建造" + newStatus.getBuildingList().get(buildOption - 1).getName() +
                                    " 預計" + newStatus.getBuildingList().get(buildOption - 1).getBuildNeedTime() + "小時後完成");
                        } else {
                            if (newStatus.isVillagerEnough()) {
                                System.out.println("無法建造" + newStatus.getBuildingList().get(buildOption - 1).getName() + "!");
                            }
                        }
                    }
                    break;
                }
                case 5: {
                    newStatus.showUpgrade();
                    int upgradeOption;
                    while (true) {
                        System.out.println("\n當前文明等級:" + ((University) newStatus.getBuildingList().get(1)).getCivilLevel());
                        System.out.println(newStatus.getResource());
                        System.out.print("請選擇要升級的建築物:     (輸入0退出)\n");
                        upgradeOption = input.numberInput(0, 8);
                        if (upgradeOption == 0) {
                            break;
                        }
                        if (newStatus.upgrade(upgradeOption)) {
                            System.out.println("開始升級" + newStatus.getBuildingList().get(upgradeOption - 1).getName() +
                                    " 預計" + newStatus.getBuildingList().get(upgradeOption - 1).getUpgradeNeedTime() + "小時後完成");
                        } else {
                            System.out.println("無法升級" + newStatus.getBuildingList().get(upgradeOption - 1).getName() + "!");
                        }
                    }
                    break;
                }
                case 6: {
                    System.out.println("請選擇要開關的建築物:\n");
                    System.out.println("1." + newStatus.getBuildingList().get(0).getName() + "\t當前是否開啟中:" + newStatus.getBuildingList().get(0).isOnOff());
                    System.out.println("3." + newStatus.getBuildingList().get(2).getName() + "\t當前是否開啟中:" + newStatus.getBuildingList().get(2).isOnOff());
                    System.out.println("8." + newStatus.getBuildingList().get(7).getName() + "\t當前是否開啟中:" + newStatus.getBuildingList().get(7).isOnOff());
                    int choice = input.openCloseNumber();
                    System.out.println("請選擇要開啟或關閉 1.開啟  2.關閉");
                    int openOrClose = input.numberInput(1, 2);
                    if (openOrClose == 1) {
                        if (newStatus.openBuilding(choice, true)) {
                            System.out.println("開啟成功");
                        }
                    } else {
                        if (newStatus.openBuilding(choice, false)) {
                            System.out.println("關閉成功");
                        }
                    }
                    break;
                }
                case 7: {
                    System.out.println("市長請輸入要過幾小時:");
                    // 輸入經過小時
                    int hr = input.numberInput(1, 99999);
                    // 看經過幾小時就跑幾次nextHour
                    if (hr <= 5) {
                        System.out.println(hr + "小時過後......\n");
                        Thread.sleep(500);
                    } else if (hr <= 12) {
                        System.out.println("--------時間流逝中，請稍後----------\n");
                        Thread.sleep(1200);
                    } else {
                        System.out.println("心情小語:在非洲，每過60秒，就會有一分鐘過去\n");
                        Thread.sleep(1000);
                        System.out.println("勤洗手，戴口罩，殭屍病毒遠離我\n");
                        Thread.sleep(1200);
                        System.out.println("天黑了就趕緊回家，免得出門在外被殭屍病毒傳染\n");
                    }
                    newStatus.nextHours(hr);
                }
                break;
            }
        }
        System.out.println("\nGame Over!" +
                "\n城市已被摧毀，市長請下次再加油吧! இдஇ");
    }
}