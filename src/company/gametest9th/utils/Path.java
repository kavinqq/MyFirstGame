package company.gametest9th.utils;

public class Path {
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

    public static class Resources extends Flow {
        private Resources() {
            super("/company/resources");
        }
    }

    public static class Img extends Flow {
        private Img() {
            super(new Resources(), "/img");
        }

        public static class Actors extends Flow {
            private Actors(Flow flow) {
                super(flow, "/actors");
            }

            public String aircraft() {
                return this + "/airplane1.png";
            }

            public String enemy() {
                return this + "/enemy1.png";
            }
        }

        public static class Objs extends Flow {

            private Objs(Flow flow) {
                super(flow, "/objs");

            }

            public String boom() {
                return this + "/boom.png";

            }

            public String boom2() {
                return this + "/boom2.png";

            }

        }

        public static class Background extends Flow {
            private Background(Flow flow) {
                super(flow, "/backgrounds");
            }

            public String background(){
                return this + "/background.jpg";
            }
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

    public static class Sound extends Flow {
        private Sound() {
            super(new Resources(), "/sounds");
        }
    }

    public Img img() {
        return new Img();
    }

    public Sound sound() {
        return new Sound();
    }
}
