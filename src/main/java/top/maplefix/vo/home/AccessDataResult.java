package top.maplefix.vo.home;

import lombok.Data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wangjg
 * @description
 * @date 2020/7/30 15:11
 */
@Data
public class AccessDataResult {
    List<AccessData> out = new ArrayList<>();
    List<AccessData> inner = new ArrayList<>();
    List<String> legendData = new ArrayList<>();
    List<String> innerLegendData = new ArrayList<>();


    public AccessDataResult complete() {
        List<AccessData> dataList = new ArrayList<>();
        dataList.addAll(out);
        dataList.addAll(inner);
        legendData = dataList.stream().sorted(Comparator.comparing(AccessData::getValue).reversed()).map(AccessData::getName).collect(Collectors.toList());
        innerLegendData = inner.stream().sorted(Comparator.comparing(AccessData::getValue).reversed()).map(AccessData::getName).collect(Collectors.toList());
        return this;
    }

}

