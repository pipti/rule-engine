package cn.ruleengine.web.controller.ruleset;

import cn.ruleengine.web.service.ruleset.RuleSetService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author 丁乾文
 * @create 2020/12/29
 * @since 1.0.0
 */
@Api(tags = "规则集控制器")
@RestController
@RequestMapping("ruleEngine/ruleSet")
public class RuleSetController {

    @Resource
    private RuleSetService ruleSetService;

}
