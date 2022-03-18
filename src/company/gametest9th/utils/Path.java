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

        //
        private Img() {
            super(new Resources(), "/img");
        }

        public static class Actors extends Flow {
            private Actors(Flow flow) {
                super(flow, "/actors");
            }

/*             留一個當範例看
             public String aircraft() {
                return this + "/airplane1.png";
            }
*/


        }


        // Resource => img => obj
        public static class Objs extends Flow {

            private Objs(Flow flow) {
                super(flow, "/objs");

            }

            public String start() { return this + "/start.png";};

            public String exit() { return this + "/exit.png";};
        }


        // Resource => img => background
        public static class Background extends Flow {
            private Background(Flow flow) {
                super(flow, "/backgrounds");
            }

            public String background(){
                return this + "/background.jpg";
            }

            public String grassBG() { return this + "/grassBG.png";}
        }

        public Actors actors() {
            return new Actors(this);

        }

        public Objs objs() {
            return new Objs(this);

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
