package top.maplefix.component;

import tk.mybatis.mapper.genid.GenId;

import java.util.UUID;

/**
 * @author : Maple
 * @description : 实现tk的id生成策略（tk只提供接口未实现）
 * @date : Created in 2019/7/24 10:00
 * @editor:
 * @version: v2.1
 */
public class UuIdGenId implements GenId<String> {

    @Override
    public String genId(String s, String s1) {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
