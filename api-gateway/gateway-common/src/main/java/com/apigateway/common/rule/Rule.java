package com.apigateway.common.rule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * 规则对象
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Rule implements Comparable<Rule>, Serializable {

    /**
     * 全局唯一的规则id
     */
    private String id;

    /**
     * 规则名称
     */
    private String name;

    /**
     * 规则协议
     */
    private String protocol;
    /**
     * 规则优先级
     */
    private Integer order;

    private Set<FilterConfig> filterConfigs = new HashSet<>();




    /**
     * 像规则里面提供一些新增的配置方法
     */
    public boolean addFilterConfig(FilterConfig filterConfig){
       return filterConfigs.add(filterConfig);
    }

    /**
     * 通过指定的配置id获取指定的配置信息
     */
    public Optional<FilterConfig> getFilterConfigById(String id){
        return  filterConfigs.stream().filter(item -> item.id.equals(id)).findFirst();
    }

    @Override
    public int compareTo(Rule o) {
        int orderCompare = Integer.compare(this.getOrder(),o.getOrder());
        if(orderCompare == 0){
            return getId().compareTo(o.getId());
        }
        return orderCompare;
    }

    /**
     * 通过传入的filterid 判断是否存在FilterConfig
     * @param id
     * @return
     */
    public boolean hashId(String id){
        return filterConfigs.stream().anyMatch(filterConfig -> filterConfig.id.equals(id));
    }

    @Override
    public boolean equals(Object o) {
        if(this == o){
            return true;
        }
        if(o == null || getClass() != o.getClass()){
            return false;
        }

        Rule other = (Rule) o;
        return id.equals(other.id);

    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Data
    public static class FilterConfig implements Comparable{
        /**
         * 规则配置ID
         */
        private String id;

        /**
         * 配置信息
         */
        private String config;


        @Override
        public int compareTo(Object o) {
            return 0;
        }

        @Override
        public boolean equals(Object o) {
            if(this == o){
                return true;
            }
            if(o == null || getClass() != o.getClass()){
                return false;
            }

            FilterConfig other = (FilterConfig) o;
            return id.equals(other.id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }
    }

}
