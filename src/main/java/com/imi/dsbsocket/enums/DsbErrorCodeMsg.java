package com.imi.dsbsocket.enums;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Caster on 2019/10/25.
 */
public enum DsbErrorCodeMsg {
    GET_TOKEN_FAILE(1001, "取得Token錯誤.請聯繫管理員"),
    UPDATE_ORDER_FAILE(1002, "更新订单错误.請聯繫管理員"),
    FORCED_KICKING_OUT_FAILE(1003, "强制踢出错误.請聯繫管理員"),
    UPDATE_ACCOUNT_BALANCE_FAILE(1004, "更新账户余额错误.請聯繫管理員"),
    SHOW_SYSTEM_NEWS_ERROR(1005, "显示系统公告错误.請聯繫管理員"),
    ORDER_NOTIFY_FAILE(1006, "订单通知错误.請聯繫管理員"),
    CALCULATING_REBATES_FAILE(1007, "计算返点错误,請聯繫管理員"),
    EXEC_TABLE_FAIEL(1008, "异动%s失败"),
    ACCOUNT_ERROR(2001, "此账号尚未注册.请先注册后再登录"),
    LOGIN_VALIDATE_FAILE(2002, "账号或密码错误"),
    ACCOUNT_STATUS_FAILE(2003, "账号已停用"),
    PARAMETER_BLANK(2004, "请输入%s"),
    INVITATION_CODE_FAILE(2005, "邀请码不存在"),
    ACCOUNT_EXISTS_ERROR(2006, "账号已存在,不可重复注册"),
    NOT_LOGIN_ERROR(2007, "此账号尚未登录"),
    UPDATE_PWD_OLDPWD_FAIL(2008, "舊密码错误"),
    UPDATE_PWD_FORMAT_FAIL(2009, "新密码格式错误(至少6个字符且同时包含英文和数字)"),
    FUNDS_PWD_VALIDATE_FAIL(2010, "资金密码错误"),
    NUMBER_VALIDATE_FAIL(2011, "%s 只能是数字"),
    ACCOUNT_FORMAT_FAIL(2012, "账号规则不符，必须至少5个字符最多12字符，可包含字母和数字"),
    INSUFFICIENT_BALANCE_FAILE(2013, "账户余额不足,不可接单"),
    QRCODE_BLANK_FAILE(2014, "无启用中的收款码."),
    PARAMETER_LENGTH_FAILE(2015, "%s 长度错误最多仅 %s字符"),
    PAYMENT_AMOUNT_FAILE(2016, "请输入实际付款金额"),
    CHECK_PWD_ERROR(2017, "新密码与确认密码不符"),
    NUMBER_INTEGER_VALIDATE_FAIL(2018, "%s 只能是整数"),
    ACCOUNT_INSUFFICIENT_BALANCE_ERROR(2019, "账户余额不足"),
    LOCK_FAILE(2020, "锁定%s失败，请稍后再试"),
    CHANNEL_FAILE(2021, "通道返点未开启,无法接单"),
    PWD_VALIDATE_FAIL(2022, "%s密码格式错误(至少6个字符且同时包含英文和数字)"),
    ACCOUNT_OPEN_PERCENT_ERROR(2023, "%s 下级返点不得大于自身返点"),
    REPEAT_LOGIN_FAILE(2024, "偵測到不同地方登入，請重新登入!!"),
    PAYEE_ID_ERROR(2025, "下级会员有误!!"),
    UPLOAD_PARAMETER_BLANK(2026, "请上传%s"),
    SPECIAL_SYMBOL_ERROR(2027, "%s不能有特殊符号"),
    ORDER_NO_ERROR(2028, "订单号有誤!!"),
    NEW_PWD_SAME_ERROR(2029, "新密码与舊密码相同"),
    APPEAL_EXISTS_ERROR(2030, "已存在申诉记录"),
    BANK_CARD_NUMBER_LENGTH_ERROR(2031, "%s不得少于10码"),
    CUSTOMIZE_ERROR(2032, "%s"),
    QRCODE_SEETING_ERROR(2033, "请先关闭自动接单再%s收款码"),
    QRCODE_IMAGE_SIZE_ERROR(2034, "收款码图片大小超过3MB，请重新上传"),
    FAILED_MOBILE_ERROR(2035, "太频繁获取验证码，请稍后再试!!"),
    GET_MOBILE_ERROR(2036, "获取验证码失败，请洽询商务人员"),
    VERIFICATION_CODE_MATCH_ERROR(2037, "手机验证码不符合，请确认后再输入"),
    VERIFICATION_CODE_MATCH_TIME_OUT(2038, "手机验证码已超时，请重新获取");


    private int errorCode;
    private String errorMsg;

    private DsbErrorCodeMsg(int errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg(String... context) {
        if (context.length > 0) {
            // 找出有多少個%s
            int signCount = errorMsg.split("%s").length - 1;
            int supplementCount = signCount - context.length;
            // 傳入的參數個數不足，需產生新陣列，長度必須至少跟%s個數一樣(否則format會出錯)，並將null替換成""
            if (supplementCount > 0) {
                Object[] newContext = null;
                List<String> al = Arrays.asList(Arrays.copyOf(context, signCount));
                newContext = al.stream().map(s -> {
                    return (s == null ? "" : s);
                }).toArray();
                // String[] newContext = ArrayLis
                return String.format(errorMsg, newContext);
            }

            return String.format(errorMsg, context);
        } else {
            errorMsg = errorMsg.replaceAll("%s", "\"\"");
            return errorMsg;
        }
    }

//    public JSONResult createErrorInfo(String... context) {
//        String errorMsg = getErrorMsg(context);
//        return JSONResult.createErrorResult(errorCode, errorMsg);
//    }
}
