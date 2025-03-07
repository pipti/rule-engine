package cn.ruleengine.web.vo.system;

import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author dingqianwen
 * @date 2021/1/15
 * @since 1.0.0
 */
@Data
public class CacheMonitorResponse {

    private Properties info;

    private Object dbSize;

    private List<CommandStats> commandStats;

    @Data
    public static class CommandStats {
        private String name;
        private String value;
    }

}
