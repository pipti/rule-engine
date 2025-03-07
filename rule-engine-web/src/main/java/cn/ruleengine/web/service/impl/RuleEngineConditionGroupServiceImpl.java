package cn.ruleengine.web.service.impl;


import cn.hutool.core.collection.CollUtil;
import cn.ruleengine.web.service.ConditionService;
import cn.ruleengine.web.service.RuleEngineConditionGroupService;
import cn.ruleengine.web.store.entity.*;
import cn.ruleengine.web.store.manager.RuleEngineConditionGroupConditionManager;
import cn.ruleengine.web.store.manager.RuleEngineConditionGroupManager;
import cn.ruleengine.web.store.manager.RuleEngineConditionManager;
import cn.ruleengine.web.vo.condition.ConditionGroupCondition;
import cn.ruleengine.web.vo.condition.ConditionGroupConfig;
import cn.ruleengine.web.vo.condition.group.SaveOrUpdateConditionGroup;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author dingqianwen
 * @date 2020/8/28
 * @since 1.0.0
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class RuleEngineConditionGroupServiceImpl implements RuleEngineConditionGroupService {

    @Resource
    private RuleEngineConditionGroupManager ruleEngineConditionGroupManager;
    @Resource
    private RuleEngineConditionGroupConditionManager ruleEngineConditionGroupConditionManager;
    @Resource
    private ConditionService conditionService;
    @Resource
    private RuleEngineConditionManager ruleEngineConditionManager;

    /**
     * 保存或者更新条件组
     *
     * @param saveOrUpdateConditionGroup 条件组信息
     * @return int
     */
    @Override
    public Integer saveOrUpdateConditionGroup(SaveOrUpdateConditionGroup saveOrUpdateConditionGroup) {
        RuleEngineConditionGroup engineConditionGroup = new RuleEngineConditionGroup();
        engineConditionGroup.setId(saveOrUpdateConditionGroup.getId());
        engineConditionGroup.setName(saveOrUpdateConditionGroup.getName());
        engineConditionGroup.setRuleId(saveOrUpdateConditionGroup.getRuleId());
        engineConditionGroup.setOrderNo(saveOrUpdateConditionGroup.getOrderNo());
        this.ruleEngineConditionGroupManager.saveOrUpdate(engineConditionGroup);
        return engineConditionGroup.getId();
    }

    /**
     * 保存条件组
     *
     * @param ruleId         规则id
     * @param conditionGroup 条件组信息
     */
    @Override
    public void saveConditionGroup(Integer ruleId, List<ConditionGroupConfig> conditionGroup) {
        if (CollUtil.isEmpty(conditionGroup)) {
            return;
        }
        List<RuleEngineConditionGroupCondition> ruleEngineConditionGroupConditions = new LinkedList<>();
        for (ConditionGroupConfig groupConfig : conditionGroup) {
            RuleEngineConditionGroup engineConditionGroup = new RuleEngineConditionGroup();
            engineConditionGroup.setName(groupConfig.getName());
            engineConditionGroup.setRuleId(ruleId);
            engineConditionGroup.setOrderNo(groupConfig.getOrderNo());
            this.ruleEngineConditionGroupManager.save(engineConditionGroup);
            List<ConditionGroupCondition> conditionGroupConditions = groupConfig.getConditionGroupCondition();
            if (CollUtil.isNotEmpty(conditionGroupConditions)) {
                for (ConditionGroupCondition conditionGroupCondition : conditionGroupConditions) {
                    RuleEngineConditionGroupCondition ruleEngineConditionGroupCondition = new RuleEngineConditionGroupCondition();
                    ruleEngineConditionGroupCondition.setConditionId(conditionGroupCondition.getCondition().getId());
                    ruleEngineConditionGroupCondition.setConditionGroupId(engineConditionGroup.getId());
                    ruleEngineConditionGroupCondition.setOrderNo(conditionGroupCondition.getOrderNo());
                    ruleEngineConditionGroupConditions.add(ruleEngineConditionGroupCondition);
                }
            }
        }
        if (CollUtil.isNotEmpty(ruleEngineConditionGroupConditions)) {
            this.ruleEngineConditionGroupConditionManager.saveBatch(ruleEngineConditionGroupConditions);
        }
    }

    /**
     * 删除条件组
     *
     * @param id 条件组id
     * @return true
     */
    @Override
    public Boolean delete(Integer id) {
        return this.ruleEngineConditionGroupManager.removeById(id);
    }


    /**
     * 删除规则条件组信息
     *
     * @param ruleIds 规则ids
     */
    @Override
    public void removeConditionGroupByRuleIds(List<Integer> ruleIds) {
        List<RuleEngineConditionGroup> engineConditionGroups = ruleEngineConditionGroupManager.lambdaQuery()
                .in(RuleEngineConditionGroup::getRuleId, ruleIds)
                .list();
        if (CollUtil.isNotEmpty(engineConditionGroups)) {
            List<Integer> engineConditionGroupIds = engineConditionGroups.stream().map(RuleEngineConditionGroup::getId).collect(Collectors.toList());
            if (this.ruleEngineConditionGroupManager.removeByIds(engineConditionGroupIds)) {
                // 删除条件组条件
                this.ruleEngineConditionGroupConditionManager.lambdaUpdate()
                        .in(RuleEngineConditionGroupCondition::getConditionGroupId, engineConditionGroupIds)
                        .remove();
            }
        }
    }

    /**
     * 获取条件组配置
     *
     * @param ruleId 规则id
     * @return ConditionGroupConfig list
     */
    @Override
    public List<ConditionGroupConfig> getConditionGroupConfig(Integer ruleId) {
        List<RuleEngineConditionGroup> engineConditionGroups = this.ruleEngineConditionGroupManager.lambdaQuery()
                .eq(RuleEngineConditionGroup::getRuleId, ruleId)
                .orderByAsc(RuleEngineConditionGroup::getOrderNo)
                .list();
        if (CollUtil.isEmpty(engineConditionGroups)) {
            return Collections.emptyList();
        }
        // 加载所有的用到的条件组条件
        Set<Integer> conditionGroupIds = engineConditionGroups.stream().map(RuleEngineConditionGroup::getId).collect(Collectors.toSet());
        List<RuleEngineConditionGroupCondition> ruleEngineConditionGroupConditions = this.ruleEngineConditionGroupConditionManager.lambdaQuery()
                .in(RuleEngineConditionGroupCondition::getConditionGroupId, conditionGroupIds)
                .orderByAsc(RuleEngineConditionGroupCondition::getOrderNo)
                .list();
        if (CollUtil.isEmpty(ruleEngineConditionGroupConditions)) {
            return Collections.emptyList();
        }
        Map<Integer, List<RuleEngineConditionGroupCondition>> conditionGroupConditionMaps = ruleEngineConditionGroupConditions.stream()
                .collect(Collectors.groupingBy(RuleEngineConditionGroupCondition::getConditionGroupId));
        Set<Integer> conditionIds = conditionGroupConditionMaps.values().stream().flatMap(Collection::stream).map(RuleEngineConditionGroupCondition::getConditionId)
                .collect(Collectors.toSet());
        List<RuleEngineCondition> ruleEngineConditions = this.ruleEngineConditionManager.lambdaQuery().in(RuleEngineCondition::getId, conditionIds).list();
        if (CollUtil.isNotEmpty(ruleEngineConditions)) {
            Map<Integer, RuleEngineCondition> conditionMap = ruleEngineConditions.stream().collect(Collectors.toMap(RuleEngineCondition::getId, Function.identity()));
            Map<Integer, RuleEngineElement> elementMap = this.conditionService.getConditionElementMap(conditionMap.values());
            Map<Integer, RuleEngineVariable> variableMap = this.conditionService.getConditionVariableMap(conditionMap.values());
            // 转换条件组数据
            List<ConditionGroupConfig> conditionGroup = new ArrayList<>();
            for (RuleEngineConditionGroup engineConditionGroup : engineConditionGroups) {
                ConditionGroupConfig group = new ConditionGroupConfig();
                group.setId(engineConditionGroup.getId());
                group.setName(engineConditionGroup.getName());
                group.setOrderNo(engineConditionGroup.getOrderNo());
                List<RuleEngineConditionGroupCondition> conditionGroupConditions = conditionGroupConditionMaps.get(engineConditionGroup.getId());
                if (CollUtil.isEmpty(conditionGroupConditions)) {
                    continue;
                }
                List<ConditionGroupCondition> groupConditions = new ArrayList<>(conditionGroupConditions.size());
                for (RuleEngineConditionGroupCondition conditionGroupCondition : conditionGroupConditions) {
                    ConditionGroupCondition conditionSet = new ConditionGroupCondition();
                    conditionSet.setId(conditionGroupCondition.getId());
                    conditionSet.setOrderNo(conditionGroupCondition.getOrderNo());
                    RuleEngineCondition engineCondition = conditionMap.get(conditionGroupCondition.getConditionId());
                    conditionSet.setCondition(this.conditionService.getConditionResponse(engineCondition, variableMap, elementMap));
                    groupConditions.add(conditionSet);
                }
                group.setConditionGroupCondition(groupConditions);
                conditionGroup.add(group);
            }
            return conditionGroup;
        }
        return Collections.emptyList();
    }

}
