package cn.ruleengine.web.vo.ruleset;

import cn.ruleengine.web.vo.condition.ConditionGroupConfig;
import cn.ruleengine.web.vo.condition.ConfigValue;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author liqian
 * @date 2021/1/15
 */
@Data
public class RuleBody {
    /**
     * 规则id
     */
    private Integer id;
    /**
     * 规则名称
     */
    @NotBlank
    private String name;
    /**
     * 规则集是有序的，默认循序执行规则集
     */
    private Integer orderNo;

    /**
     * 规则条件组
     */
    @NotNull
    @Valid
    private List<ConditionGroupConfig> conditionGroup;

    /**
     * 规则结果
     */
    @NotNull
    @Valid
    private ConfigValue action;

}
