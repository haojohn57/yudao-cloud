package cn.iocoder.yudao.framework.common.iot.dto;

import cn.iocoder.yudao.framework.common.core.Converter;
import cn.iocoder.yudao.framework.common.iot.bean.Pages;
import cn.iocoder.yudao.framework.common.iot.bean.point.PointValue;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

/**
 * @author 益莲科技
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
public class PointValueDto implements Serializable, Converter<PointValue, PointValueDto> {
    private static final long serialVersionUID = 1L;

    private Long deviceId;
    private Long pointId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Pages page;

    @Override
    public void convertDtoToDo(PointValue pointValue) {
        BeanUtils.copyProperties(this, pointValue);
    }

    @Override
    public void convertDoToDto(PointValue pointValue) {
        BeanUtils.copyProperties(pointValue, this);
    }
}
