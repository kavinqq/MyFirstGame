package main;

import buildings.Building;

import java.util.*;

import static main.BuildingSystem.*;
import static main.BuildingSystem.BuildingType.*;

import main.BuildingSystem.BuildingNode;

public class Main {

    public final static Scanner SCANNER = new Scanner(System.in);

    public enum Command {
        STATUS(1),//"1.顯示資源\n" +
        WOOD(2),//木材指定幾人採
        STEEL(3),//鋼鐵指定幾人採
        BUILD(4),//建立/升級建築
        SET_SWITCH(5),//開啟關閉建築///
        NEXT_HOUR(6),//時間過 1小時
        NEXT_HALF_DAY(7),//時間過12小時
        NEXT_DAY(8),//時間過24小時
        EXIT(9);//離開遊戲
        private final int value;

        private Command(int value) {
            this.value = value;
        }

        public static Command getCommandByInt(int option) {
            for (Command command : values()) {    //Command[] values()
                if (command.value == option) {
                    return command;
                }
            }
            return STATUS;
        }
    }

    /**
     * 離開當前選單
     */
    public static final int LEAVE = -1;
    public static final int BUILD_BUILD = 1;
    public static final int BUILD_UPGRADE = 2;

    public static void main(String[] argv) {
        System.out.println();
        // 給使用者一座城市
        City city = new City();
        // 要跳過的時間
        int thisRoundTimePass;
        //選項用
        int option;
        /*
          最外面這一圈do while的功用是執行以下動作之後的判斷(遊戲是否繼續 / 死多少人 剩下多少人):
          每一次選擇指令(內層的do while)的所有事情
          例如: 選擇指令 建造 Buildings.House 和 建造 Buildings.Lab 和 一部分市民去採礦... (都在內層do..while 因為可以同時做很多事情)
          再執行 時間過 1 / 12 / 24 小時之後 便會離開內層do while到外層
         */
        do {
            thisRoundTimePass = 0;
            option = inputInt("===================第" + (City.getGameTime() + 1) + "小時===================\n請選擇你要執行的指令:\n" +
                    "1.顯示資源\n" +
                    "2.木材指定幾人採\n" +
                    "3.鋼鐵指定幾人採\n" +
                    "4.建立/升級\n" +
                    "5.開啟/關閉建築\n" +
                    "6.時間過 1小時\n" +
                    "7.時間過12小時\n" +
                    "8.時間過24小時\n" +
                    "9.離開遊戲", 1, 9);
            Command command = Command.getCommandByInt(option);
            switch (command) {
                case STATUS: {
                    city.showInfo();
                    pause();
                    break;
                }
                case WOOD: {
                    System.out.println("目前閒人有 : " + city.getFreeCitizen());
                    if (city.getFreeCitizen() <= 0) {
                        System.out.println("目前沒有閒置狀態的村民!");
                    } else {
                        int humanNum = inputInt("要指派幾位村民去伐木?", 0, city.getFreeCitizen());
                        city.assignWork(humanNum, Command.WOOD);
                    }
                    break;
                }
                case STEEL: {
                    System.out.println("目前閒人有 : " + city.getFreeCitizen());
                    if (city.getFreeCitizen() <= 0) {
                        System.out.println("目前沒有閒置狀態的村民!");
                    } else {
                        int humanNum = inputInt("要指派幾位村民去採鋼鐵?", 0, city.getFreeCitizen()); //city.getTotalFreeMan()
                        city.assignWork(humanNum, Command.STEEL);
                    }
                    break;

                }
                case BUILD: {
                    int choose = inputInt("1.建造 2.升級  (-1離開)", 1, 2);
                    System.out.println("科技等級：" + City.getTechLevel());
                    switch (choose) {
                        case BUILD_BUILD: {
                            //建造
                            //show出全部的房屋種類+功能+需要資源
                            city.showCanBuildBuilding();
                            //取得要建造的種類
                            choose = inputInt("請選擇要建造的建築(-1離開)：", HOUSE.instance().getId(), AIRPLANE_MILL.instance().getId());
                            if (choose == LEAVE) {
                                break;
                            }
                            BuildingType type = BuildingType.getBuildingTypeByInt(choose);
                            //建造成功與否
                            if (city.getBuildingNum() != city.MAX_CAN_BUILD && city.canBuildBuilding(type)) {
                                city.build(type);
                                System.out.println(type.instance().getName() + "建造中");
                            } else {
                                if (city.getBuildingNum() == city.MAX_CAN_BUILD) {
                                    System.out.println("你的城市 經過多年風風雨雨 鐵與血的灌溉\n如今 從杳無人煙之地 成了 充斥著滿滿的高樓大廈 人車馬龍的繁華之地\n你的城市 已沒有地方可以建造新的建築了");
                                }
                                if (City.getTechLevel() < type.instance().getTechLevelNeedBuild()) {
                                    System.out.println("科技等級不足");
                                }
                                if (!type.instance().isEnoughBuild(city.getResource())) {
                                    System.out.println("物資不足");
                                }
                            }
                            break;
                        }

                        case BUILD_UPGRADE: {
                            //升級
                            //show出可以升級的建築，且有可以升級的才執行選項
                            city.showCanUpgradeBuilding();
                            //選取要升級的種類
                            if (city.isNoLab() && city.isNoArsenal()) {
                                break;
                            }
                            choose = inputInt("請選擇要升級的建築種類(-1離開)：", HOUSE.instance().getId(), AIRPLANE_MILL.instance().getId());
                            if (choose == LEAVE) {
                                break;
                            }
                            BuildingType type = BuildingType.getBuildingTypeByInt(choose);
                            //如過建築鏈表中有可以升級的建築就會被顯示出來
                            if (city.canUpgradeBuilding(type)) {
                                //顯示可以升級的建築細節，取得可升級建築陣列
                                ArrayList<BuildingNode> canUpgradeTypeList = city.showAndGetCanUpgradeTypeDetail(type);
                                //若陣列不為空，代表有閒置的研究所或兵工廠可以使用
                                if (canUpgradeTypeList != null) {
                                    switch (type) {
                                        case LAB: {
                                            if (city.isUpgradingTech()) {
                                                System.out.println("科技已在升級中，請等待此次升級結束");
                                            } else {
                                                city.upgradeTechLevel();
                                                System.out.println("科技升級中");
                                            }
                                            break;
                                        }
                                        case ARSENAL: {
                                            choose = inputInt("要升級1.士兵 2.飛機", 1, 2);
                                            switch (choose) {
                                                case 1: {
                                                    if (city.isUpgradingSoldier()) {
                                                        System.out.println("士兵已在升級中，請等待此次升級結束");
                                                    } else {
                                                        city.upgradeSoldier();
                                                        System.out.println("士兵升級中");
                                                    }
                                                    break;
                                                }
                                                case 2: {
                                                    if (city.isUpgradingPlane()) {
                                                        System.out.println("飛機已在升級中");
                                                    } else {
                                                        city.upgradePlane();
                                                        System.out.println("飛機升級中");
                                                    }
                                                    break;
                                                }
                                            }
                                            break;
                                        }
                                        default: {
                                            choose = inputInt("請選擇要升級的建築(-1離開)：", 1, canUpgradeTypeList.size());
                                            if (choose == LEAVE) {
                                                break;
                                            }
                                            city.upgrade(canUpgradeTypeList.get(choose - 1));
                                            System.out.println("安排升級中");
                                        }
                                    }
                                }
                            } else {
                                if (type == LAB && city.getFreeLabNum() == 0) {
                                    System.out.println("沒有閒置的研究所");
                                }
                                if (type == ARSENAL && city.getFreeArsenalNum() == 0) {
                                    System.out.println("沒有閒置的兵工廠");
                                }
                                if (City.getTechLevel() < type.instance().getTechLevelNeedUpgrade()) {
                                    System.out.println("科技等級不足");
                                }
                                if (!type.instance().isEnoughUpgrade(city.getResource())) {
                                    System.out.println("物資不足");
                                }
                            }
                            break;
                        }
                    }
                    break;
                }
                case SET_SWITCH: {
                    int choose = inputInt("請選擇：1.開啟建築 2.關閉建築 -1返回", 1, 2);
                    if (choose == LEAVE) {
                        break;
                    }
                    switch (choose) {
                        case 1: {
                            ArrayList<BuildingNode> notWorkingBuildingList = city.getNotWorkingBuildingList();
                            if (notWorkingBuildingList.isEmpty()) {
                                System.out.println("沒有可以開啟的建築");
                                break;
                            }
                            System.out.println("目前停止運轉的建築：");
                            for (int i = 0; i < notWorkingBuildingList.size(); i++) {
                                Building building = notWorkingBuildingList.get(i).getBuilding();
                                System.out.println((i + 1) + ". " + building.buildingDetail(building.getLevel()));
                            }
                            choose = inputInt("輸入-1取消", 1, notWorkingBuildingList.size());
                            if (choose == LEAVE) {
                                break;
                            }
                            city.setStart(notWorkingBuildingList.get(choose - 1));
                            System.out.println("開啟成功");
                            break;
                        }
                        case 2: {
                            ArrayList<BuildingNode> workingBuildingList = city.getWorkingBuildingList();
                            if (workingBuildingList.isEmpty()) {
                                System.out.println("沒有可以關閉的建築");
                                break;
                            }
                            System.out.println("目前可關閉的建築：");
                            for (int i = 0; i < workingBuildingList.size(); i++) {
                                Building building = workingBuildingList.get(i).getBuilding();
                                System.out.println((i + 1) + ". " + building.buildingDetail(building.getLevel()));
                            }
                            choose = inputInt("輸入-1取消", 1, workingBuildingList.size());
                            if (choose == LEAVE) {
                                break;
                            }
                            city.setStop(workingBuildingList.get(choose - 1));
                            System.out.println("關閉成功");
                            break;
                        }
                    }
                    break;
                }
                case NEXT_HOUR: {
                    //時間流動 1小時
                    thisRoundTimePass = 1;
                    break;
                }
                case NEXT_HALF_DAY: {
                    //時間流動 12小時
                    thisRoundTimePass = 12;
                    break;
                }
                case NEXT_DAY: {
                    //時間流動 24小時
                    thisRoundTimePass = 24;
                    break;
                }
                case EXIT: {
                    System.exit(0);
                    break;
                }
            }//end switch
            city.doCityWorkAndTimePass(thisRoundTimePass);
        } while (city.isAlive());
    }

    /**
     * 自定義用來限制範圍的 input (回傳int 版本)
     * -1 為取消選擇 離開
     */
    public static int inputInt(String hint, int min, int max) {
        int input = 0;
        try {
            do {
                System.out.println(hint);
                input = SCANNER.nextInt();
                SCANNER.nextLine();
                if (input >= min && input <= max || input == LEAVE) {
                    return input;
                } else {
                    System.out.printf("請輸入範圍內的數字(%d ~ %d)或輸入-1返回!\n", min, max);
                }
            } while (true);
        } catch (InputMismatchException e) {
            System.out.println("對不起，輸入錯誤!");
            System.out.println("請重新輸入數字。");
        }
        return 1;
    }

    public static void pause() {
        System.out.println("===enter繼續===");
        SCANNER.nextLine();
    }
}