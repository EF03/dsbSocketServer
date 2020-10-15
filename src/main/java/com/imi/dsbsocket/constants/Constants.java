package com.imi.dsbsocket.constants;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

public class Constants {

	private Constants() {

	}

	// 前台TOKEN 密鑰
	public static String FRONT_SECRET_KEY = "123456789";
	// 前台 簽檔來源
	public static String ISSUER = "dsb";
	/**
	 * 提交訂單成功筆數
	 */
	public static int SUCCESS_ORDER_NUM = 0;
	/**
	 * 提交訂單失敗筆數
	 */
	public static int FAIL_ORDER_NUM = 0;
	/**
	 * 回調訂單成功筆數
	 */
	public static int SUCCESS_CALLBACK_NUM = 0;
	/**
	 * 回調訂單成功，但不需更新DB的筆數
	 */
	public static int SUCCESS_CALLBACK_INVALID_NUM = 0;
	/**
	 * 回調訂單失敗筆數
	 */
	public static int FAIL_CALLBACK_NUM = 0;

	/**
	 * 每秒提交訂單成功筆數
	 */
	public static ConcurrentSkipListMap<String, Integer> SUCCESS_ORDER_LIST_NUM_IN_SECOND = new ConcurrentSkipListMap<String, Integer>();

	/**
	 * 每秒提交游戏訂單成功筆數
	 */
	public static ConcurrentSkipListMap<String, Integer> SUCCESS_GP_ORDER_LIST_NUM_IN_SECOND = new ConcurrentSkipListMap<String, Integer>();
	/**
	 * 每秒回調訂單成功筆數
	 */
	public static ConcurrentSkipListMap<String, Integer> SUCCESS_CALLBACK_NUM_IN_SECOND = new ConcurrentSkipListMap<String, Integer>();
	/**
	 * 每秒回調訂單成功，但不需更新DB的筆數
	 */
	public static ConcurrentSkipListMap<String, Integer> SUCCESS_CALLBACK_INVALID_NUM_IN_SECOND = new ConcurrentSkipListMap<String, Integer>();

	/**
	 * 每秒提交訂單成功單號
	 */
	public static ConcurrentSkipListMap<String, ArrayList<String>> SUCCESS_ORDER_LIST_IN_SECOND = new ConcurrentSkipListMap<String, ArrayList<String>>();

	// System Config Parameters
	/** 自动刷新订单查询天数 */
	public static String AUTO_RELOAD_ORDER_QUERY_DAY;
	/** 黑名单提示信息 */
	public static String BLACKLIST_MSG = "";
	/** 后台IP限制 */
	public static String CAN_LIMIT_ADMIN_IP = "";
	/** 新订单提醒 */
	public static String CAN_NEW_ORDER_ALERT = "";
	/** 订单错误提醒 */
	public static String CAN_ERROR_ORDER_ALERT = "";
	/** 自动隐藏未支付订单 */
	public static String CAN_HIDDEN_UNPAID_ORDER = "";
	/** 公告訊息 */
	public static String CUSTOMER_NOTICE_MSG = "";
	/** 商户信息变更 */
	public static String IS_LOCK_MERCHANT = "";
	/** 將檔案傳送到遠端主機 */
	public static String IS_SEND_TO_REMOTE = "";
	/** 是否顯示公告訊息 */
	public static String IS_SHOW_CUSTOMER_NOTICE = "";
	/** 充值金额小数点（0.01-0.99）控制开关 */
	public static String IS_RECHARGE_RANDOM_DECIMAL = "";
	/** 订单列表默认查询状态;-1=全部, 0-待確認,1-未支付, 2-已支付-未添加, 3-已支付-已添加, 4-已支付-帳號錯誤 */
	public static String ORDER_LIST_QUERY_STATUS_DEFAULT = "";
	/** 預設刷新秒數 */
	public static String GRID_RELOAD_SECOND_DEFAULT = "";
	/** 充值额度最小金额 */
	public static String RECHARGE_MIN_AMOUNT = "";
	/** 充值额度最大金额 */
	public static String RECHARGE_MAX_AMOUNT = "";
	/** 本網站對外的IP;提供給支付平台通知支付成功用 */
	public static String WEB_SITE_IP = "";
	/** 网站根目录 */
	public static String WEB_SITE_CONTEXT = "";
	/** 是否顯示訂單詳情欄位;此欄位不提供編輯，而是看ORDER_FIELD_LIST是否有N，若有N則此欄位為Y */
	public static String SHOW_ORDER_DETAIL = "";
	/** 簽章檔案存放目錄 */
	public static String SIGNATURE_KEY_PATH = "";
	/** 遠端簽章檔案存放目錄 */
	public static String SIGNATURE_KEY_PATH_REMOTE = "";
	/** 提交訂單主機的目錄 */
	public static String SUBMIT_ORDER_CONTEXT = "";
	/** 提交訂單主機的IP */
	public static String SUBMIT_ORDER_IP = "";
	/** 編輯時鎖定的秒數 */
	public static int EDIT_LOCK_SECOND = 0;
	/** 订单提醒秒数 */
	public static int NEW_ORDER_RELOAD_SECOND = 0;
	/** IP白名单列表 */
	public static List<String> LIMIT_ADMIN_IP = new ArrayList<String>();
	/** 列表刷新秒数 */
	public static List<Integer> GRID_RELOAD_SECOND = new ArrayList<Integer>();

    public static final int VALIDATE_AUTH_CODE_BUFFER_SECONDS = 300; // 驗證碼的緩衝時間5分鐘
	// Auto Recharge Parameters
	/** 自动充值监控系统 */
	public static String CAN_AUTO_RECHARGE = "";
	/** 自动充值站点OTP */
	public static String RECHARGE_WEBSITE_OTP = "";
	/** 机器人最後更新時間 */
	public static String ROBOT_LAST_UPDATED_TIME = "";
	/** 机器人启动控制;1-启动, 2-关闭, 3-重启 */
	public static String ROBOT_STATUS = "";
	/** 自动充值站点与机器人通讯状态 */
	public static String RECHARGE_ROBOT_STATUS = "";
	/** 自动充值错误信息 */
	public static String RECHARGE_ERROR_MSG = "";
	/** 自动充值成功信息 */
	public static String RECHARGE_SUCCESS_MSG = "";
	/** 自动充值操作账号 */
	public static String RECHARGE_ACCOUNT_NAME = "";
	/** 自动充值间隔秒数 */
	public static String RECHARGE_INTERVAL_SEC = "";
	/** 自动充值站点登录密码 */
	public static String RECHARGE_WEBSITE_LOGIN_PWD = "";
	/** 自动充值站点登录账号 */
	public static String RECHARGE_WEBSITE_LOGIN_NAME = "";
	/** 自动充值站点登录网址 */
	public static String RECHARGE_WEBSITE_URL = "";
	/** 机器人数量 */
	public static String ROBOT_QUANTITY = "";
	/** 自动充值站点平台 */
	public static String RECHARGE_WEBSITE = "";
	/** 机器人KEY */
	public static String ROBOT_KEY = "";
	/** 自动充值授权码 */
	public static String RECHARGE_AUTHORIZATION_CODE = "";

    /**
     * 订单属性表
     */
    public static List<Map<String, Object>> ORDER_FIELD_LIST = new ArrayList<Map<String, Object>>();

    /**
     * 遊戲平台属性表
     */
    public static List<Map<String, Object>> GP_ORDER_FIELD_LIST = new ArrayList<Map<String, Object>>();

    /**
     * 在線支付 订单属性表
     */
    public static List<Map<String, Object>> ONLINE_PAYMENT_FIELD_LIST = new ArrayList<Map<String, Object>>();


    //UPAY 订单属性表
    public static List<Map<String, Object>> UPAY_PAYMENT_FIELD_LIST = new ArrayList<Map<String, Object>>();

    // 自动接接口 接口变数表
	public static List<Map<String, Object>> PLATFORMPARAMS_FIELD_LIST = new ArrayList<Map<String, Object>>();

	// 自动接接口 Mypay变数表
	public static List<Map<String, Object>> MYPAYPARAMS_FIELD_LIST = new ArrayList<Map<String, Object>>();


	/** 簽章種類;3-RSA(PFX) */
	public static final String PFX = "pfx";

	/**
	 * 每天定期執行的job group
	 */
	public static final String DAILY_JOB_GROUP = "DAILY_JOB_GROUP";
	/**
	 * 只執行一次就不在執行的job group
	 */
	public static final String TRIGGER_ONCE_JOB_GROUP = "TRIGGER_ONCE_JOB_GROUP";

	/**
	 * Scheduler要delay幾秒才會啟動
	 */
	public static final int SCHEDULER_START_DELAYED = 10;

	/**
	 * user login session.key
	 */
	public static final String CLIENT_LOGIN_KEY = "CLIENT_LOGIN_KEY";
	public static final String USER_LOGIN_KEY = "USER_LOGIN_KEY";

	/**
	 * 驗證碼的session.key
	 */
	public static final String CAPTCHA_KEY = "CAPTCHA_KEY";

	/**
	 * image server
	 */
	// public static String IMAGE_SERVER = "http://192.168.0.21/oneShop/";
	public static String IMAGE_SERVER = "http://localhost:8080/oneShop/";

	// public static final String IMAGE_REAL_PATH =
	// "/home/tomcat8/apache-tomcat-8.5.9/webapps/oneShop/";
	public static String WEB_APP_REAL_PATH = null;

	public static final String RELATIVE_PATH_CLIENT = "upload/clients/";

	public static final String RELATIVE_PATH_GOODS = "upload/goods/";

	public static final String RELATIVE_PATH_PROMOTE = "upload/promote/";

	public static final String RELATIVE_PATH_SUPPLIER = "upload/suppliers/";

	/**
	 * CLIENT圖片上傳
	 */
	public static String UPLOAD_CLIENT = null;
	/**
	 * GOODS圖片上傳
	 */
	public static String UPLOAD_GOODS = null;
	/**
	 * PROMOTE圖片上傳
	 */
	public static String UPLOAD_PROMOTE = null;
	/**
	 * SUPPLIER圖片上傳
	 */
	public static String UPLOAD_SUPPLIER = null;

	/**
	 * user圖片目錄base path
	 */
	public static final String UPLOAD_FOLDER_CLIENT = IMAGE_SERVER + RELATIVE_PATH_CLIENT;

	/**
	 * goods圖片目錄base path
	 */
	public static final String UPLOAD_FOLDER_GOODS = IMAGE_SERVER + RELATIVE_PATH_GOODS;

	/**
	 * promote page圖片目錄base path
	 */
	public static final String UPLOAD_FOLDER_PROMOTE = IMAGE_SERVER + RELATIVE_PATH_PROMOTE;

	/**
	 * supplier圖片目錄base path
	 */
	public static final String UPLOAD_FOLDER_SUPPLIER = IMAGE_SERVER + RELATIVE_PATH_SUPPLIER;

	/**
	 * 分頁筆數
	 */
	public static final int CLIENT_PAGE_COUNT = 10;

	public static final String RECOMMEND_GOODS_PEROID_KEY = "RECOMMEND_GOODS_PEROID_";

	/**
	 * 是否啟動quartz，改成用web.xml的UserType來判斷
	 */
	// public static final boolean QUARTZ_START = true;

	/**
	 * lock table
	 */
	public static final String PEROID_NUMBER_LOCK = "PEROID_NUMBER_LOCK";

	public static final int OPEN_TIME_ADD_MIN = 4;

	/**
	 * font config's head part of key. example: the whole words is
	 * RECOMMEND_SEARCH_KEYWORD_1, here we'll keep RECOMMEND_SEARCH_KEYWORD.
	 */
	public static final String FRONT_RECOMMEND_SEARCH_KEYWORD = "RECOMMEND_SEARCH_KEYWORD";

	/**
	 * 日期格式化
	 */
	public static final DateTimeFormatter YYYY_MM_DD = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	/**
	 * 日期格式化成yyyy-MM-dd HH:mm:ss
	 */
	public static final DateTimeFormatter YYYY_MM_DD_HH_mm_SS = DateTimeFormatter
		.ofPattern("yyyy-MM-dd HH:mm:ss");
	/**
	 * 日期格式化成yyyy-MM-dd HH:mm:ss.SSS
	 */
	public static final DateTimeFormatter YYYY_MM_DD_HH_mm_SS_MS = DateTimeFormatter
		.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
	/**
	 * 日期格式化
	 */
	public static final DateTimeFormatter DATE_FORMAT_NO_SLASH = DateTimeFormatter.ofPattern("yyyyMMdd");

	public static final String SQL_FOR_UPDATE_WAIT_STRING = " wait 5 ";

	public static final void initWebAppRealPath(String base) {
		WEB_APP_REAL_PATH = base;

		/**
		 * CLIENT圖片上傳
		 */
		UPLOAD_CLIENT = WEB_APP_REAL_PATH + RELATIVE_PATH_CLIENT;
		/**
		 * GOODS圖片上傳
		 */
		UPLOAD_GOODS = WEB_APP_REAL_PATH + RELATIVE_PATH_GOODS;
		/**
		 * PROMOTE圖片上傳
		 */
		UPLOAD_PROMOTE = WEB_APP_REAL_PATH + RELATIVE_PATH_PROMOTE;
		/**
		 * SUPPLIER圖片上傳
		 */
		UPLOAD_SUPPLIER = WEB_APP_REAL_PATH + RELATIVE_PATH_SUPPLIER;
	}
}
