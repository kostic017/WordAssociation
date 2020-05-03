package vr.kostic017.wordassociation.consts;

public class Config {
    public static final String FILE_NAME = "config.properties";

    public static class Keys {
        public static final String API_BASE_URL = "api.base.url";
    }

    public static class Values {
        public static final int POINTS_PER_SOLUTION =  10;
        public static final int COUNT_DOWN_TIME = 5 * 60 * 1000;
        public static final int COUNT_DOWN_INTERVAL = 1000;
    }
}
