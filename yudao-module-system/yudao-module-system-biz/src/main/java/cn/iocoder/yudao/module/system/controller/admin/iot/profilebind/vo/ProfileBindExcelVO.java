package cn.iocoder.yudao.module.system.controller.admin.iot.profilebind.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 设备与模版映射关联 Excel VO
 *
 * @author 益莲科技
 */
@Data
public class ProfileBindExcelVO {

    @ExcelProperty("主键ID")
    private Long id;

    @ExcelProperty("模版ID")
    private Long profileId;

    @ExcelProperty("设备ID")
    private Long deviceId;

    @ExcelProperty("描述")
    private String description;

    @ExcelProperty("创建时间")
    private Date createTime;

}
