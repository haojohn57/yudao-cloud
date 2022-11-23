package cn.iocoder.yudao.framework.common.iot.bean.property;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 益莲科技
 */
@Setter
@Getter
public class ThreadProperty {
    private String prefix;
    private int corePoolSize;
    private int maximumPoolSize;
    private int keepAliveTime;
}
