package cn.iocoder.yudao.framework.common.exception;

import cn.hutool.core.util.StrUtil;

/**
 * 自定义 重复 异常
 *
 * @author 益莲科技
 */
public class DuplicateException extends RuntimeException {
    public DuplicateException(CharSequence template, Object... params) {
        super(StrUtil.format(template, params));
    }
}
