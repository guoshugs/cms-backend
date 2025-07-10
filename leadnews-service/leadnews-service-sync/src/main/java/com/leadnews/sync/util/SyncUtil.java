package com.leadnews.sync.util;

import java.time.format.DateTimeFormatter;

/**
 * @version 1.0
 * @description 说明
 * @package com.leadnews.sync.util
 */
public class SyncUtil {
    // 格式化输出
    public static final DateTimeFormatter FORMATTER_YMDHMS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static volatile boolean sync_status = false;
}
