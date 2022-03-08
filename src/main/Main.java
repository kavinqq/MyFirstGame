package main;

import buildings.Building;

import java.util.*;

import static main.BuildingsCollection.*;
import static main.BuildingsCollection.BuildingType.*;

import main.BuildingsCollection.BuildingNode;

public class Main {

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
        //預售屋用來查看資訊
        PreSaleBuildings preSaleBuildings = new PreSaleBuildings();
        // 要跳過的時間
        int thisRoundTimePass;
        /*
          最外面這一圈do while的功用是執行以下動作之後的判斷(遊戲是否繼續 / 死多少人 剩下多少人):
          每一次選擇指令(內層的do while)的所有事情
          例如: 選擇指令 建造 Buildings.House 和 建造 Buildings.Lab 和 一部分市民去採礦... (都在內層do..while 因為可以同時做很多事情)
          再執行 時間過 1 / 12 / 24 小時之後 便會離開內層do while到外層
         */
        do {
            thisRoundTimePass = 0;
            //內層的do while 的作用: 除了執行指令 5/6/7(三個時間流動相關指令) 和 離開遊戲 以外 都不會離開 內層do..while
            do {
                int option = inputInt("\n第" + (city.getGameTime() + 1) + "回合\n請選擇你要執行的指令:\n" +
                        "1.顯示資源\n" +
                        "2.木材指定幾人採\n" +
                        "3.鋼鐵指定幾人採\n" +
                        "4.建立/升級/查看建築/\n" +
                        "5.開啟/關閉建築\n" +///
                        "6.時間過 1小時\n" +
                        "7.時間過12小時\n" +
                        "8.時間過24小時\n" +
                        "9.離開遊戲", 1, 9);
                Command command = Command.getCommandByInt(option);
                switch (command) {
                    case STATUS: {
                        city.showInfo();
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
                        int choose = inputInt("1.建造 2.升級 (-1離開)\n", HOUSE.instance().getId(), AIRPLANE_MILL.instance().getId());
                        System.out.println("科技等級：" + City.getTechLevel());
                        System.out.println(city.getResource());
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
                                if (city.canBuildBuilding(type)) {
                                    city.build(type);
                                } else {
                                    if (city.getBuildingNum() == city.MAX_CAN_BUILD) {
                                        System.out.println("你的城市 經過多年風風雨雨 鐵與血的灌溉\n如今 從杳無人煙之地 成了 充斥著滿滿的高樓大廈 人車馬龍的繁華之地\n你的城市 已沒有地方可以建造新的建築了");
                                    } else if (City.getTechLevel() < type.instance().getTechLevelNeedBuild()) {
                                        System.out.println("科技等級不足");
                                    } else {
                                        System.out.println("物資不足");
                                    }
                                }
                                break;
                            }

                            case BUILD_UPGRADE: {
                                //升級
                                //show出可以升級的建築
                                city.showCanUpgradeBuilding();
                                //選取要升級的種類
                                choose = inputInt("請選擇要升級的建築種類(-1離開)：", HOUSE.instance().getId(), AIRPLANE_MILL.instance().getId());
                                if (choose == LEAVE) {
                                    break;
                                }
                                BuildingType type = BuildingType.getBuildingTypeByInt(choose);
                                if (city.canUpgradeBuilding(type)) {
                                    //顯示可以升級的建築細節，取得可升級建築陣列
                                    ArrayList<BuildingNode> canUpgradeTypeList = city.showCanUpgradeTypeDetail(type);
                                    //若陣列不為空，代表有閒置的研究所或兵工廠可以使用
                                    if (canUpgradeTypeList != null) {
                                        switch (type) {
                                            case LAB: {
                                                city.upgradeTechLevel();
                                                break;
                                            }
                                            case ARSENAL: {
                                                choose = inputInt("要升級1.士兵 2.飛機", 1, 2);
                                                switch (choose) {
                                                    case 1: {
                                                        city.upgradeSoldier();
                                                    }
                                                    case 2: {
                                                        city.upgradePlane();
                                                    }
                                                }
                                                break;
                                            }
                                            default:{
                                                choose = inputInt("請選擇要升級的建築(-1離開)：", 1, canUpgradeTypeList.size());
                                                if (choose == LEAVE) {
                                                    break;
                                                }
                                                city.upgrade(canUpgradeTypeList.get(choose - 1));
                                            }
                                        }
                                    }
                                } else {
                                    if (type == LAB && city.getFreeLabNum() == 0) {
                                        System.out.println("沒有閒置的研究所");
                                    }else if (type == ARSENAL && city.getFreeArsenalNum() == 0) {
                                        System.out.println("沒有閒置的兵工廠");
                                    }else if (City.getTechLevel() < type.instance().getTechLevelNeedUpgrade()) {
                                        System.out.println("科技等級不足");
                                    }else{
                                        System.out.println("無此建築或物資不足");
                                    }
                                }
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
            } while (thisRoundTimePass == 0);
        } while (city.doCityWorkAndTimePass(thisRoundTimePass));
    }

    /**
     * 自定義用來限制範圍的 input (回傳int 版本)
     * -1 為取消選擇 離開
     */
    public static int inputInt(String hint, int min, int max) {
        Scanner sc = new Scanner(System.in);
        int input = 0;
        try {
            do {
                System.out.println(hint);
                input = sc.nextInt();
                if (input >= min && input <= max || input == -1) {
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
}