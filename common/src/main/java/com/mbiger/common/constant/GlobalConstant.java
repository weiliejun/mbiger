package com.mbiger.common.constant;

/**
 * 系统全局常量
 */
public class GlobalConstant {
    //状态有效
    public static final String STATUS_VALID = "0";
    //状态无效
    public static final String STATUS_INVALID = "1";
    //数据有效
    public static final String DATA_VALID = "0";
    //数据无效
    public static final String DATA_INVALID = "1";
    //default字面量
    public static final String DEFAULT = "default";
    // 用户免费使用服务次数
    public static final Integer SERVICE_FREE_COUNT = 5;
    // 用户上传转账凭证路径
    public static final String TRANSFER_FILES_TEMP_PATH = "/upload/transferFile/";

    public static final String MBIGER_SERVICE_DOC = "/upload/mbigerservice/doc/";
    //管理后台初始密码
    public static final String CONSOLE_PASSWORD_INIT = "123456";

    //管理后台系统超级管理角色名称
    public static final String CONSOLE_SUPER_ADMIN_ROLE = "系统超级管理员";
    // 用户申请API最大次数
    public static final Integer APPLY_API_MAX = 5;

    // 消息类型
    public static class MessageType {
        // 短信
        public static final String SMS = "sms";
        // 站内信
        public static final String WEBSITE = "website";
    }
}
