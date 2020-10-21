package com.imi.dsbsocket.enums;

/**
 * @author Ron
 * @date 2020/10/20 下午 12:00
 */
public enum BaccaratStatus {

    BETTING_TIME(1, "下注时间"),
    OPENING_TIME(2, "开牌时间"),
    AWARDING_TIME(3, "派奖时间"),
    PREPARING_TIME(4, "准备时间"),
    SHUFFLING_TIME(5, "洗牌时间"),

    ;


    private int baccaratStatus;
    private String msg;

    BaccaratStatus(int baccaratStatus, String msg) {
        this.baccaratStatus = baccaratStatus;
        this.msg = msg;
    }

    public int getBaccaratCode() {
        return baccaratStatus;
    }

    public void setBaccaratCode(int baccaratStatus) {
        this.baccaratStatus = baccaratStatus;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    //    public int getErrorCode() {
//        return errorCode;
//    }
//
//    public String getErrorMsg(String... context) {
//        if (context.length > 0) {
//            // 找出有多少個%s
//            int signCount = errorMsg.split("%s").length - 1;
//            int supplementCount = signCount - context.length;
//            // 傳入的參數個數不足，需產生新陣列，長度必須至少跟%s個數一樣(否則format會出錯)，並將null替換成""
//            if (supplementCount > 0) {
//                Object[] newContext = null;
//                List<String> al = Arrays.asList(Arrays.copyOf(context, signCount));
//                newContext = al.stream().map(s -> {
//                    return (s == null ? "" : s);
//                }).toArray();
//                // String[] newContext = ArrayLis
//                return String.format(errorMsg, newContext);
//            }
//
//            return String.format(errorMsg, context);
//        } else {
//            errorMsg = errorMsg.replaceAll("%s", "\"\"");
//            return errorMsg;
//        }
//    }
}
