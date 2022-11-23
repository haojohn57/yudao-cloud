package cn.iocoder.yudao.framework.common.iot.bean.driver;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author 益莲科技
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class DriverConfiguration implements Serializable {
    private static final long serialVersionUID = 1L;

    private String type;
    private String command;
    private Object content;
    private String response;
}
