package Main;

import java.util.*;

public class Main
{
    public enum Command{
        Status(1),
        Wood(2),
        Steel(3),
        Build(4),
        NextHour(5),
        NextHalfDay(6),
        NextDay(7),
        Exit(8);
        private final int value;
        private Command(int value){
            this.value = value;
        }

        public static Command getCommandByInt(int option){
            for(Command command: values()){    //Command[] values()
                if(command.value == option){
                    return command;
                }
            }
            return Status;
        }
    }

    public static void main(String[] argv){
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
        do{
            thisRoundTimePass=0;
            //內層的do while 的作用: 除了執行指令 5/6/7(三個時間流動相關指令) 和 離開遊戲 以外 都不會離開 內層do..while
            do {
                int option = inputInt("\n第"+(city.getGameTime()+1)+"回合\n請選擇你要執行的指令:\n" +
                        "1.顯示資源\n" +
                        "2.木材指定幾人採\n" +
                        "3.鋼鐵指定幾人採\n" +
                        "4.建立/升級建築\n" +
                        "5.時間過1小時\n" +
                        "6.時間過12小時\n" +
                        "7.時間過24小時\n" +
                        "8.離開遊戲", 1 , 8);
                Command command = Command.getCommandByInt(option);
                switch (command) {
                    case Status:{
                        city.showInfo();
                        break;
                    }
                    case Wood:{
                        System.out.println("目前閒人有 : " + city.getFreeCitizen());
                        if(city.getFreeCitizen() <= 0){
                            System.out.println("目前沒有閒置狀態的村民!");
                        }else{
                            int humanNum = inputInt("要指派幾位村民去伐木?",0, city.getFreeCitizen());
                            city.assignWork(humanNum, Command.Wood);
                        }
                        break;
                    }
                    case Steel:{
                        System.out.println("目前閒人有 : " + city.getFreeCitizen());
                        if(city.getFreeCitizen() <= 0){
                            System.out.println("目前沒有閒置狀態的村民!");
                        }else{
                            int humanNum = inputInt("要指派幾位村民去採鋼鐵?",0, city.getFreeCitizen() ); //city.getTotalFreeMan()
                            city.assignWork(humanNum, Command.Steel);
                        }
                        break;

                    }
                    case Build:{
                        int opt = inputInt("請選擇要 1.建造 2.升級: ",1,2);

                        if(opt == 1){

                            //建造
                            if(city.isFull()){
                                System.out.println("你的城市 經過多年風風雨雨 鐵與血的灌溉\n如今 從杳無人煙之地 成了 充斥著滿滿的高樓大廈 人車馬龍的繁華之地\n你的城市 已沒有地方可以建造新的建築了");
                                break;
                            }else{
                                System.out.printf("目前資源\t\t\t%d木材\t%d鋼鐵\n" ,city.getResource().getTotalWood(),city.getResource().getTotalSteel());

                                for (int i = 0; i < preSaleBuildings.getPreBuildings().length ; i++) {
                                    /*
                                        先去預售建築物類別(PreSaleBuildings) ← 概念就是建築物的Demo
                                        取得建築物的ID → 透過ID → 1.取得名字 2.取得建造要花多少木頭 鋼鐵
                                     */
                                    System.out.printf("%d.%s\t建造成本:\t%d木材\t%d鋼鐵 ",
                                            preSaleBuildings.getPreBuildingByIndex(i).getId(),
                                            preSaleBuildings.getPreBuildingByIndex(i).getName(),
                                            preSaleBuildings.getPreBuildingByIndex(i).getWoodCostCreate(),
                                            preSaleBuildings.getPreBuildingByIndex(i).getSteelCostCreate()
                                    );
                                    // 木頭不足
                                    if (city.getResource().getTotalWood() < preSaleBuildings.getPreBuildingByIndex(i).getWoodCostCreate()){
                                        System.out.printf("\t缺%d個木頭 ",
                                                preSaleBuildings.getPreBuildingByIndex(i).getWoodCostCreate()-city.getResource().getTotalWood());
                                    }
                                    //鋼鐵不足
                                    if (city.getResource().getTotalSteel() < preSaleBuildings.getPreBuildingByIndex(i).getSteelCostCreate()){
                                        System.out.printf("\t缺%d個鋼鐵 ",
                                                preSaleBuildings.getPreBuildingByIndex(i).getSteelCostCreate()-city.getResource().getTotalSteel());
                                    }
                                    //文明等級不足，無法建造兵工廠 (因為規則寫: 兵工廠要City文明等級2才可以建造)
                                    if(city.getTechLevel() < 2 && preSaleBuildings.getPreBuildingByIndex(i).getId() == 6){
                                        System.out.print("\t目前文明等級不足2,無法建造兵工廠 ");
                                    }
                                    System.out.println("");
                                }


                                opt = inputInt("",1,6);
                                //預先建造一個房子
                                city.preBuild(opt);
                                if(city.canUpgradeBuilding(city.getBuildingCount())){
                                    //如果成功安排建造
                                    System.out.printf("%s 已安排建造\n",city.getBuilding(city.getBuildingCount() - 1).getName());
                                    city.sortBuildingArrByID(); //已更新igc
                                }else{
                                    System.out.printf("%s 無法安排建造\n",city.getBuilding(city.getBuildingCount()).getName());
                                }
                            }

                        }else{   //升級
                            if(city.getNumOfLab()>0){

                                //給使用者觀看,顯示可以升級的建築
                                System.out.println("顯示可以升級的建築");
                                preSaleBuildings.zeros();
                                for(int i=0; i < city.getBuildingCount(); i++){
                                    //可升級建築統計:
                                    //if(是否可以升級or建造)
                                    if(city.checkOnlyForCanUpgrade(i)){
                                        preSaleBuildings.add(city.getBuilding(i).getId());
                                    }
                                }
                                for(int i = 0; i < preSaleBuildings.getPreBuildings().length; i++){
                                    //可升級的建築:
                                    if(preSaleBuildings.getPreBuildingByIndex(i).getId()==2){
                                        System.out.printf("%d.%s\t共%d單位可升級:\t需要升級成本:\t%d木材\t%d鋼鐵",
                                                preSaleBuildings.getPreBuildingByIndex(i).getId(),
                                                "文明等級",
                                                preSaleBuildings.getSum(i),
                                                preSaleBuildings.getPreBuildingByIndex(i).getWoodCostLevelUp(),
                                                preSaleBuildings.getPreBuildingByIndex(i).getSteelCostLevelUp());

                                    }else if(preSaleBuildings.getPreBuildingByIndex(i).getId()==6){
                                        System.out.printf("%d.%s\t共%d單位可升級:\t需要升級成本:\t%d木材\t%d鋼鐵",
                                                preSaleBuildings.getPreBuildingByIndex(i).getId(),
                                                "士兵等級",
                                                preSaleBuildings.getSum(i),
                                                preSaleBuildings.getPreBuildingByIndex(i).getWoodCostLevelUp(),
                                                preSaleBuildings.getPreBuildingByIndex(i).getSteelCostLevelUp());

                                    }else{
                                        System.out.printf("%d.%s\t\t共%d間可升級:\t\t需要升級成本:\t%d木材\t%d鋼鐵",
                                                preSaleBuildings.getPreBuildingByIndex(i).getId(),
                                                preSaleBuildings.getPreBuildingByIndex(i).getName(),
                                                preSaleBuildings.getSum(i),
                                                preSaleBuildings.getPreBuildingByIndex(i).getWoodCostLevelUp(),
                                                preSaleBuildings.getPreBuildingByIndex(i).getSteelCostLevelUp());
                                    }
                                    // 木頭不足
                                    if (city.getResource().getTotalWood() < preSaleBuildings.getPreBuildingByIndex(i).getWoodCostLevelUp()) {
                                        System.out.printf("\t缺%d木頭 ",
                                                preSaleBuildings.getPreBuildingByIndex(i).getWoodCostLevelUp() - city.getResource().getTotalWood());
                                    }
                                    //鋼鐵不足
                                    if (city.getResource().getTotalSteel() < preSaleBuildings.getPreBuildingByIndex(i).getSteelCostLevelUp()) {
                                        System.out.printf("\t缺%d鋼鐵 ",
                                                preSaleBuildings.getPreBuildingByIndex(i).getSteelCostLevelUp() - city.getResource().getTotalSteel());
                                    }
                                    System.out.println("");
                                }
                                //輸入建築編號
                                opt = inputInt("",1,6);
                                //紀錄是否有可以升級的建築
                                boolean isBuildingFreeToBuildExist = false;
                                for(int i = 0; i < city.getBuildingCount(); i++){
                                    //如果該建築物"沒有"在建築柱列中 且 編號正確
                                    if(!city.getBuilding(i).isReadyToUpgrade() && city.getBuilding(i).getId() == (opt)){    //已更新igc
                                        //檢查 和 安排建造 都寫在↓canUpgradeBuilding() true代表可以建造or升級
                                        if(city.canUpgradeBuilding(i)){
                                            isBuildingFreeToBuildExist = true;
                                            if(opt==2){
                                                System.out.printf("%s 已安排升級\n","文明等級");
                                            }else if(opt==6){
                                                System.out.printf("%s 已安排升級\n","士兵等級");
                                            }else{
                                                System.out.printf("%s 已安排升級\n",city.getBuilding(i).getName());
                                            }
                                            //city.sortBuildingArrByID();
                                            break;  //已更新igc
                                        }else{
                                            isBuildingFreeToBuildExist = true;
                                            if(opt==2){
                                                System.out.println("目前 文明 因材料不足 尚不可升級");
                                            }else if(opt==6){
                                                System.out.println("目前 士兵 因材料不足 尚不可升級");
                                            }else{
                                                System.out.printf("%s 無法安排升級\n",city.getBuilding(i).getName());
                                            }
                                        }
                                    }

                                }
                                if(!isBuildingFreeToBuildExist){
                                    if(opt==2){   //已更新igc
                                        System.out.println("目前沒有可以升級 文明 的建築");
                                    }else if(opt==6){ //已更新igc
                                        System.out.println("目前沒有可以升級 士兵 的建築");
                                    }else{
                                        System.out.printf("目前沒有可以升級的 %s\n",preSaleBuildings.getPreBuildingByIndex(opt - 1).getName());  //目前還沒建造該建築 or 該建築都在升級/建造中  所以出現此題式
                                    }
                                }
                            }else{
                                System.out.println("研究所尚未建造 無法進行升級");
                            }
                        }
                        break;
                    }
                    case NextHour:{
                        //時間流動 1小時
                        thisRoundTimePass = 1;
                        break;
                    }
                    case NextHalfDay:{
                        //時間流動 12小時
                        thisRoundTimePass = 12;
                        break;
                    }
                    case NextDay:{
                        //時間流動 24小時
                        thisRoundTimePass = 24;
                        break;
                    }
                    case Exit:{
                        System.exit(0);
                        break;
                    }
                }//end switch
            }while(thisRoundTimePass == 0);
        }while(city.doCityWorkAndTimePass(thisRoundTimePass));
    }
    /**
     * 自定義用來限制範圍的 input (回傳int 版本)
     */
    public static int inputInt(String hint, int min, int max)
    {
        Scanner sc = new Scanner(System.in);
        int input = 0;
        try{
            do {
                System.out.println(hint);
                input = sc.nextInt();
                if (input >= min && input <= max) {
                    return input;
                } else {
                    System.out.printf("請輸入範圍內的數字(%d ~ %d)!\n", min, max);
                }
            }while(true);
        }catch(InputMismatchException e){
            System.out.println("對不起，輸入錯誤!");
            System.out.println("請重新輸入數字。");
        }
        return 1;
    }
}