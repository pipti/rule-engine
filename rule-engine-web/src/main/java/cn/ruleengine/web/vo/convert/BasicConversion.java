package cn.ruleengine.web.vo.convert;

import cn.ruleengine.web.store.entity.*;
import cn.ruleengine.web.vo.condition.ConfigValue;
import cn.ruleengine.web.vo.condition.group.condition.SaveOrUpdateConditionGroupCondition;
import cn.ruleengine.web.vo.decisiontable.DecisionTableDefinition;
import cn.ruleengine.web.vo.element.GetElementResponse;
import cn.ruleengine.web.vo.generalrule.GeneralRuleDefinition;
import cn.ruleengine.web.vo.menu.ListMenuResponse;
import cn.ruleengine.web.vo.generalrule.DefaultAction;
import cn.ruleengine.web.vo.ruleset.RuleSetDefinition;
import cn.ruleengine.web.vo.user.UserData;
import cn.ruleengine.web.vo.user.UserResponse;
import cn.ruleengine.web.vo.variable.GetVariableResponse;
import cn.ruleengine.web.vo.workspace.ListWorkspaceResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author 丁乾文
 * @create 2020/12/12
 * @since 1.0.0
 */
@Mapper
public interface BasicConversion {

    BasicConversion INSTANCE = Mappers.getMapper(BasicConversion.class);

    /**
     * RuleEngineMenu to ListMenuResponse
     *
     * @param ruleEngineMenu ruleEngineMenu
     * @return ListMenuResponse
     */
    ListMenuResponse convert(RuleEngineMenu ruleEngineMenu);

    /**
     * ruleEngineElement to GetElementResponse
     *
     * @param ruleEngineElement ruleEngineElement
     * @return GetElementResponse
     */
    GetElementResponse convert(RuleEngineElement ruleEngineElement);

    /**
     * ruleEngineVariable to GetVariableResponse
     *
     * @param ruleEngineVariable ruleEngineVariable
     * @return GetVariableResponse
     */
    GetVariableResponse convert(RuleEngineVariable ruleEngineVariable);

    /**
     * ruleEngineRule to RuleDefinition
     *
     * @param ruleEngineRule ruleEngineRule
     * @return RuleDefinition
     */
    GeneralRuleDefinition convert(RuleEngineRule ruleEngineRule);

    /**
     * value to DefaultAction
     *
     * @param value value
     * @return DefaultAction
     */
    DefaultAction convert(ConfigValue value);

    /**
     * ruleEngineUser to UserData
     *
     * @param ruleEngineUser ruleEngineUser
     * @return UserData
     */
    UserData convert(RuleEngineUser ruleEngineUser);

    /**
     * userData to UserResponse
     *
     * @param userData userData
     * @return UserResponse
     */
    UserResponse convert(UserData userData);

    /**
     * saveOrUpdateConditionGroup to  RuleEngineConditionGroupCondition
     *
     * @param saveOrUpdateConditionGroup saveOrUpdateConditionGroup
     * @return RuleEngineConditionGroupCondition
     */
    RuleEngineConditionGroupCondition convert(SaveOrUpdateConditionGroupCondition saveOrUpdateConditionGroup);

    /**
     * ruleEngineWorkspaces to ListWorkspaceResponse
     *
     * @param ruleEngineWorkspaces ruleEngineWorkspaces
     * @return ListWorkspaceResponse
     */
    List<ListWorkspaceResponse> convert(List<RuleEngineWorkspace> ruleEngineWorkspaces);

    /**
     * ruleEngineWorkspace to ListWorkspaceResponse
     *
     * @param ruleEngineWorkspace ruleEngineWorkspace
     * @return ListWorkspaceResponse
     */
    ListWorkspaceResponse convert(RuleEngineWorkspace ruleEngineWorkspace);

    /**
     * RuleEngineDecisionTable to DecisionTableDefinition
     *
     * @param ruleEngineDecisionTable ruleEngineDecisionTable
     * @return DecisionTableDefinition
     */
    DecisionTableDefinition convert(RuleEngineDecisionTable ruleEngineDecisionTable);

    /**
     * ruleEngineRuleSet to RuleSetDefinition
     *
     * @param ruleEngineRuleSet ruleEngineRuleSet
     * @return RuleSetDefinition
     */
    RuleSetDefinition convert(RuleEngineRuleSet ruleEngineRuleSet);
}
