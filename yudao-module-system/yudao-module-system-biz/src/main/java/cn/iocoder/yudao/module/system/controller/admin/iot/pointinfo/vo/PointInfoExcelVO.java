package cn.iocoder.yudao.module.system.controller.admin.iot.pointinfo.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 位号配置信息 Excel VO
 *
 * @author 益莲科技
 */
@Data
public class PointInfoExcelVO {

    @ExcelProperty("主键ID")
    private Long id;

    @ExcelProperty("位号配置ID")
    private Long pointAttributeId;

    @ExcelProperty("值")
    private String value;

    @ExcelProperty("设备ID")
    private Long deviceId;

    @ExcelProperty("位号ID")
    private Long pointId;

    @ExcelProperty("描述")
    private String description;

    @ExcelProperty("创建时间")
    private Date createTime;

}
