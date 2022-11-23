package cn.iocoder.yudao.module.system.controller.admin.iot.driverinfo.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 协议配置信息 Excel VO
 *
 * @author 益莲科技
 */
@Data
public class DriverInfoExcelVO {

    @ExcelProperty("主键ID")
    private Long id;

    @ExcelProperty("连接配置ID")
    private Long driverAttributeId;

    @ExcelProperty("值")
    private String value;

    @ExcelProperty("设备ID")
    private Long deviceId;

    @ExcelProperty("描述")
    private String description;

    @ExcelProperty("创建时间")
    private Date createTime;

}
