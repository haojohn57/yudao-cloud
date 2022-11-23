package cn.iocoder.yudao.module.system.controller.admin.iot.driver.vo;

import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;


/**
 * 协议驱动 Excel VO
 *
 * @author 益莲科技
 */
@Data
public class DriverExcelVO {

    @ExcelProperty("主键ID")
    private Long id;

    @ExcelProperty("协议名称")
    private String name;

    @ExcelProperty("协议服务名称")
    private String serviceName;

    @ExcelProperty("类型，deriver、gateway...")
    private String type;

    @ExcelProperty("主机IP")
    private String host;

    @ExcelProperty("端口")
    private Integer port;

    @ExcelProperty(value = "是否可用", converter = DictConvert.class)
    @DictFormat("common_status") // TODO 代码优化：建议设置到对应的 XXXDictTypeConstants 枚举类中
    private Integer enable;

    @ExcelProperty("描述")
    private String description;

    @ExcelProperty("创建时间")
    private Date createTime;

}
