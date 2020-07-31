package top.maplefix.vo.home;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author wangjg
 * @description key/value对
 * @date 2020/7/30 15:07
 */
@Data
@AllArgsConstructor
public class KeyValue {
    private String key;
    private Long value;
}
