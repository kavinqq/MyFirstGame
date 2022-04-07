package company.gametest9th.utils;

public class Path {

    /**
     * 檔案的流程
     */
    public static abstract class Flow {
        private String path;

        public Flow(String path) {
            this.path = path;
        }

        public Flow(Flow flow, String path) {
            this.path = flow.path + path;
        }

        @Override
        public String toString() {
            return path;
        }
    }


    /**
     * Resources 資料夾
     */

    public static class Resources extends Flow {
        private Resources() {
            super("/company/resources");
        }
    }

    /**
     * Resources資料夾 => Img資料夾
     */

    public static class Img extends Flow {

        // Resource => img
        private Img() {
            super(new Resources(), "/img");
        }

        public class Humans extends Flow {
            private Humans(Flow flow) {
                super(flow, "/actors");
            }


            public String armySoldier() {
                return this + "/Solider.png";
            }

            //TODO
            public String airForceSoldier() {
                return this + "/Solider.png";
            }

            //TODO
            public String citizen() {
                return this + "/citizen.png";
            }

            //Or we can also use this method
            /*
            public Flow aircraft() {
                return new Flow("/airplane1.png"){};
            }
            */
        }

        public static class Zombies extends Flow {

            private Zombies(Flow flow) {
                super(flow, "/zombies");
            }

            //TODO

            public String zombieNormal() {
                return this + "/monster.png";
            }

            public String zombieBig() {
                return this + "/monster.png";
            }

            public String zombieTypeI() {
                return this + "/monster.png";

            }

            public String zombieTypeII() {
                return this + "/monster.png";
            }


            public String zombieKing() {
                return this + "/monster.png";
            }

            public String zombieWitch() {
                return this + "/monster.png";
            }

            public String zombieFlying() {
                return this + "/monster.png";
            }

            public String zombieFlyingBig() {
                return this + "/monster.png";

            }
        }


        public static class Effects extends Flow {
            private Effects(Flow flow) {
                super(flow, "/effects");
            }

            public String SwordShadow() {
                return this + "/SwordShadow.png";
            }
        }


        // Resource => img => Actors
        public static class Actors extends Flow {
            private Actors(Flow flow) {
                super(flow, "/actors");
            }


            // Resource => img => Actors => Actor2.png
            public String Actor2() {
                return this + "/Actor2.png";
            }

        }


        // Resource => img => obj
        public static class Objs extends Flow {

            private Objs(Flow flow) {
                super(flow, "/objs");
            }

            public String start() {
                return this + "/start.png";
            }

            public String exit() {
                return this + "/exit.png";
            }

            public String box() {
                return this + "/box.png";
            }

            public String whiteGrayOpacity() {
                return this + "/whiteGrayOpacitySeventy.png";
            }

            public String green() {
                return this + "/green.png";
            }

            public String red() {
                return this + "/red.png";
            }

            // UI
            public String resourceBarUI() {
                return this + "/resourceBarUI.png";
            }

            // Icon
            public String treeIcon() {
                return this + "/TreeIcon.png";
            }

            public String steelIcon() {
                return this + "/SteelResourceIcon.png";
            }

            public String citizenNumIcon() {
                return this + "/citizenNumIcon.png";
            }

            public String soldierNumIcon() {
                return this + "/soldierNumIcon.png";
            }

            public String gasIcon() {
                return this + "/gasIcon.png";
            }

            public String timeIcon() {
                return this + "/timeIcon.png";
            }

            // 資源圖片
            public String tree() {
                return this + "/Tree.png";
            }

            public String specialTree() {
                return this + "/specialTree.png";
            }

            public String steel() {
                return this + "/normalSteel.png";
            }

            public String specialSteel() {
                return this + "/specialSteel.png";
            }

            public String fog() {return this + "/fog.png";}

            public String fog2() {return this + "/fog2.jpg";}

            public String fog3() {return this + "/fog3.jpg";}

            public String chooseUnit() { return this + "/chooseUnit.png";}

            public String target() { return this + "/target.png";}

        }


        public static class Building extends Flow {

            private Building(Flow flow) {
                super(flow, "/Building");

            }

            public String House() {
                return this + "/house.png";
            }

            public String Lab() {
                return this + "/lab.png";
            }

            public String SawMill() {
                return this + "/sawmill.png";
            }

            public String SteelMill() {
                return this + "/steelmill.png";
            }

            ;

            public String Barracks() {
                return this + "/barracks.png";
            }

            ;

            public String GasMill() {
                return this + "/gasmill.png";
            }

            public String AirplanemIll() {
                return this + "/airplanemIll.png";
            }

            public String Base() {
                return this + "/BaseAndFoundation.png";
            }

            ;

            public String Arsenal() {
                return this + "/arsenal.png";
            }

            public String ingBuild() {
                return this + "/ingBuild.png";
            }

            //建築物所需Icon
            public String noWorkingIcon() {
                return this + "/stop.png";
            }

            public String workingIcon() {
                return this + "/play.png";
            }

            public String upGradeIcon() {
                return this + "/plus2.png";
            }

        }

        // Resource => img => background
        public static class Background extends Flow {

            private Background(Flow flow) {
                super(flow, "/backgrounds");
            }

            public String background() {
                return this + "/bg4.jpg";
            }

            public String hintUI() {
                return this + "/HintUI.png";
            }

            public String gameStart() {
                return this + "/gameStart.png";
            }

            public String foundation() {
                return this + "/foundationNew.png";
            }

            public String buttonFoundation() {
                return this + "/buttonFoundation.png";
            }

            public String buildingOptionBg() {
                return this + "/buttonBg.png";
            }

            public String road() {
                return this + "/road1.png";
            }

            public String area() {
                return this + "/area.jpg";
            }

            public String grass() {
                return this + "/grass.png";
            }

            public String airport() {
                return this + "airport.png";
            }

            public String darkBackground() {
                return this + "/darkbj4.png";
            }

            public String tarmac() {
                return this + "/tarmac.png";
            }

            public String magic(){
                return this+"/magic.png";
            }
        }

        public Actors actors() {
            return new Actors(this);
        }

        public Humans humans() {
            return new Humans(this);
        }

        public Zombies zombies() {
            return new Zombies(this);
        }

        public Objs objs() {
            return new Objs(this);
        }

        public Building building() {
            return new Building(this);
        }

        public Background background() {
            return new Background(this);
        }

        public Effects effects() {
            return new Effects(this);
        }

    }


    /**
     * 音效資料夾
     */

    public static class Sound extends Flow {
        private Sound() {
            super(new Resources(), "/sounds");
        }

        public String mainSceneBGM() {
            return this + "/MainSceneBGM.wav";
        }

        public String readyToWork() {
            return this + "/ReadyToWork.wav";
        }

        public String what() {
            return this + "/What.wav";
        }

        public String what2() {
            return this + "/What2.wav";
        }

        public String building() {
            return this + "/buildingSound.wav";
        }

        public String constructionComplete() {
            return this + "/constructionComplete.wav";
        }

        public String soldierWhat3() { return this + "/SoldierWhat3.wav";}

        public String soldierWhat4() { return this + "/SoldierWhat4.wav";}

        public String underAttack() {
            return this + "/underAttack.wav";
        }

        public String fight(){ return this + "/fight.wav";}
    }


    /**
     * 把圖片路徑往上傳
     *
     * @return 圖片檔的路徑
     */
    public Img img() {
        return new Img();
    }


    /**
     * 把聲音路徑往上傳
     *
     * @return 音效檔的路徑
     */

    public Sound sound() {
        return new Sound();
    }

}
