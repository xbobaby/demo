package com.bob.common.constant;

public interface CONSTANT {

    public interface LOGIN {
        public static final String LOGING_KEY_USERNAME = "LOGING_KEY_USERNAME";
    }

    public interface CACHEKEY {
        public static final String CACHE_USER_ID_KEY = "CACHEKEY_USER_ID_";
    }

    public interface USER_SESSION {
        public static final String USER_SESSION_KEY = "USER_SESSION_KEY";

    }

    public interface RETURN_MESSAGE_KEY {
        public static final String CODE = "code";
        public static final String MSG = "msg";
        public static final String DEFAULT_SUCCESS_CODE = "000000";
        public static final String DEFAULT_SUCCESS_MSG = "SUCCESS";
    }
}
