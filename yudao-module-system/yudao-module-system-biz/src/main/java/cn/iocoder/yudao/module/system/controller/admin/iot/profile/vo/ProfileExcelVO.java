package cn.iocoder.yudao.module.system.controller.admin.iot.profile.vo;

import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;


/**
 * 设备模板 Excel VO
 *
 * @author 益莲科技
 */
@Data
public class ProfileExcelVO {

    @ExcelProperty("主键ID")
    private Long id;

    @ExcelProperty("模板名称")
    private String name;

    @ExcelProperty("公有/私有模板标识")
    private Integer share;

    @ExcelProperty("系统创建/用户创建/驱动创建")
    private Integer type;

    @ExcelProperty(value = "是否可用", converter = DictConvert.class)
    @DictFormat("common_status") // TODO 代码优化：建议设置到对应的 XXXDictTypeConstants 枚举类中
    private Integer enable;

    @ExcelProperty("部门ID")
    private Long deptId;

    @ExcelProperty("设备ID")
    private Long deviceId;

    @ExcelProperty("描述")
    private String description;

    @ExcelProperty("创建时间")
    private Date createTime;

}
