package company.gametest9th.utils;

public class Path {

    /**
     *  檔案的流程
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
     *  Resources資料夾 => Img資料夾
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
                super(flow, "/Zombie");
            }

            //TODO
            public String zombieNormal(){
                return this + "/monster3.png";
            }

            public String zombieBig(){
                return this + "/monster3.png";
            }

            public String zombieTypeI(){
                return this + "/monster3.png";
            }

            public String zombieTypeII() {
                return this + "/monster3.png";
            }

            public String zombieKing(){
                return this + "/monster3.png";
            }

            public String zombieWitch(){
                return this + "/monster3.png";
            }

            public String zombieFlying(){
                return this + "/monster3.png";
            }

            public String zombieFlyingBig(){
                return this + "/monster3.png";
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

            public String start() { return this + "/start.png";};

            public String exit() { return this + "/exit.png";};

            public String box() { return this + "/box.png";}

            public String horizontalBar() { return this + "/HBar.png";}

            public String verticalBar() { return this + "/VBar.png";}

            public String whiteGrayOpacity() { return this + "/whiteGrayOpacitySeventy.png";}

            public String ingBuild(){return this+"/ingBuild.png";}

            public String greenOpacity() { return this + "/greenOpacitySixty.png";}

            public String redOpacity() { return this + "/redOpacityFifty.png";}
        }

        public static class Building extends Flow {

            private Building(Flow flow) {
                super(flow, "/Building");

            }

            public String House() { return this + "/house.png";}

            public String Lab() { return this + "/lab.png";}

            public String SawMill() { return this + "/sawmill.png";}

            public String SteelMill() { return this + "/steelmill.png";};

            public String Barracks() { return this + "/barracks.png";};







            public String GasMill() { return this + "/gasmill.png";}

            public String AirplanemIll() { return this + "/airplanemIll.png";}

            public String Base() { return this + "/Base.png";};

            public String Arsenal(){ return this + "/arsenal.png";}
        }




        // Resource => img => background
        public static class Background extends Flow {
            private Background(Flow flow) {
                super(flow, "/backgrounds");
            }

            public String background(){
                return this + "/bg4.jpg";
            }

            public String grassBG() { return this + "/grassBG.png";}

            public String foundation(){ return this + "/foundation.png";}

            public String road(){ return this + "/road1.png";}
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

        public Building building(){
            return new Building(this);
        }

        public Background background() {
            return new Background(this);
        }

    }


    /**
     * 音效資料夾
     */

    public static class Sound extends Flow {
        private Sound() {
            super(new Resources(), "/sounds");
        }
    }


    /**
     * 把圖片路徑往上傳
     * @return 圖片檔的路徑
     */
    public Img img() {
        return new Img();
    }


    /**
     * 把聲音路徑往上傳
     * @return 音效檔的路徑
     */
    public Sound sound() {
        return new Sound();
    }
}
