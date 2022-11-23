package cn.iocoder.yudao.framework.common.exception;

import cn.hutool.core.util.StrUtil;

/**
 * 自定义 Null 异常
 *
 * @author 益莲科技
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException(CharSequence template, Object... params) {
        super(StrUtil.format(template, params));
    }
}
