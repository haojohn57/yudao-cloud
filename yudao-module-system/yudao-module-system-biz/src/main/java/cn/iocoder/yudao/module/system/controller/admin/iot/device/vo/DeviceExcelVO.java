package cn.iocoder.yudao.module.system.controller.admin.iot.device.vo;

import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;
import cn.iocoder.yudao.module.system.enums.DictTypeConstants;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;


/**
 * 设备 Excel VO
 *
 * @author 益莲科技
 */
@Data
public class DeviceExcelVO {

    @ExcelProperty("主键ID")
    private Long id;

    @ExcelProperty("设备名称")
    private String name;

    @ExcelProperty("位号数据是否结构化")
    private Integer multi;

    @ExcelProperty(value = "设备状态", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.COMMON_STATUS)
    private Integer status;

    @ExcelProperty(value = "设备分类", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.DEVICE_CATEGORY)
    private Integer category;

    @ExcelProperty("驱动ID")
    private Long driverId;

    @ExcelProperty("部门ID")
    private Long deptId;

    @ExcelProperty("描述")
    private String description;

    @ExcelProperty("创建时间")
    private Date createTime;

}
