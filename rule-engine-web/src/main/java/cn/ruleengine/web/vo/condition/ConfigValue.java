package cn.ruleengine.web.vo.condition;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author 丁乾文
 * @create 2021/1/1
 * @since 1.0.0
 */
@Data
public class ConfigValue {

    private Integer tempType;

    @NotNull
    private Integer type;

    @NotBlank
    private String value;

    private String valueName;

    /**
     * 固定值变量 值
     * value为变量的id
     */
    private String variableValue;

    @NotBlank
    private String valueType;

}
