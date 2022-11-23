package cn.iocoder.yudao.module.driver.bean;

import com.github.s7connector.api.S7Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author 益莲科技
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class PlcS7PointVariable {
    private int dbNum;
    private int byteOffset;
    private int bitOffset;
    private int size;
    private S7Type type;
    private Class<?> fieldType;

    public PlcS7PointVariable(int dbNum, int byteOffset, int bitOffset, int size, String s7Type) {
        this.dbNum = dbNum;
        this.byteOffset = byteOffset;
        this.bitOffset = bitOffset;
        this.size = size;
        getS7TypeAndType(s7Type);

    }

    private void getS7TypeAndType(String s7Type) {
        switch (s7Type) {
            case "bool" -> {
                this.type = S7Type.BOOL;
                this.fieldType = Boolean.class;
            }
            case "byte" -> {
                this.type = S7Type.BYTE;
                this.fieldType = Byte.class;
            }
            case "int" -> {
                this.type = S7Type.INT;
                this.fieldType = Short.class;
            }
            case "dint" -> {
                this.type = S7Type.DINT;
                this.fieldType = Long.class;
            }
            case "word" -> {
                this.type = S7Type.WORD;
                this.fieldType = Integer.class;
            }
            case "dword" -> {
                this.type = S7Type.DWORD;
                this.fieldType = Long.class;
            }
            case "real" -> {
                this.type = S7Type.REAL;
                this.fieldType = Float.class;
            }
            case "date" -> {
                this.type = S7Type.DATE;
                this.fieldType = Date.class;
            }
            case "time" -> {
                this.type = S7Type.TIME;
                this.fieldType = Long.class;
            }
            case "datetime" -> {
                this.type = S7Type.DATE_AND_TIME;
                this.fieldType = Long.class;
            }
            default -> {
                this.type = S7Type.STRING;
                this.fieldType = Boolean.class;
            }
        }
    }
}
