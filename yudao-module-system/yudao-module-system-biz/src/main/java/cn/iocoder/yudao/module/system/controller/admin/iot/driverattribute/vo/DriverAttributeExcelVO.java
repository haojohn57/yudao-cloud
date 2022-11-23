package cn.iocoder.yudao.module.system.controller.admin.iot.driverattribute.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 连接配置信息 Excel VO
 *
 * @author 益莲科技
 */
@Data
public class DriverAttributeExcelVO {

    @ExcelProperty("主键ID")
    private Long id;

    @ExcelProperty("显示名称")
    private String displayName;

    @ExcelProperty("名称")
    private String name;

    @ExcelProperty("类型")
    private String type;

    @ExcelProperty("默认值")
    private String value;

    @ExcelProperty("驱动ID")
    private Long driverId;

    @ExcelProperty("描述")
    private String description;

    @ExcelProperty("创建时间")
    private Date createTime;

}
