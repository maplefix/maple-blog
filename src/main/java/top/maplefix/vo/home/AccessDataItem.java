package top.maplefix.vo.home;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wangjg
 * @description
 * @date 2020/7/30 15:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccessDataItem {
    private String name;
    private long value = 0;
    private String filter;
}
