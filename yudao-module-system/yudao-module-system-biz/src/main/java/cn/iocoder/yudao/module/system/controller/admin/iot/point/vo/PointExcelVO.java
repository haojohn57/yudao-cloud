package cn.iocoder.yudao.module.system.controller.admin.iot.point.vo;

import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;


/**
 * 设备位号 Excel VO
 *
 * @author 益莲科技
 */
@Data
public class PointExcelVO {

    @ExcelProperty("主键ID")
    private Long id;

    @ExcelProperty("位号名称")
    private String name;

    @ExcelProperty(value = "数据类型：stringintdoublefloatlongoolean", converter = DictConvert.class)
    @DictFormat("infra_config_type") // TODO 代码优化：建议设置到对应的 XXXDictTypeConstants 枚举类中
    private String type;

    @ExcelProperty(value = "读写标识：0读，1写，2读写", converter = DictConvert.class)
    @DictFormat("system_operate_type") // TODO 代码优化：建议设置到对应的 XXXDictTypeConstants 枚举类中
    private Integer rw;

    @ExcelProperty("基础值")
    private Float base;

    @ExcelProperty("最小值")
    private Float minimum;

    @ExcelProperty("最大值")
    private Float maximum;

    @ExcelProperty("倍数")
    private Float multiple;

    @ExcelProperty("累计标识")
    private Integer accrue;

    @ExcelProperty("格式数据，Jave格式 %.3f")
    private String format;

    @ExcelProperty("单位")
    private String unit;

    @ExcelProperty(value = "是否可用", converter = DictConvert.class)
    @DictFormat("common_status") // TODO 代码优化：建议设置到对应的 XXXDictTypeConstants 枚举类中
    private Integer enable;

    @ExcelProperty("部门ID")
    private Long deptId;

    @ExcelProperty("模板ID")
    private Long profileId;

    @ExcelProperty("描述")
    private String description;

    @ExcelProperty("创建时间")
    private Date createTime;

}
