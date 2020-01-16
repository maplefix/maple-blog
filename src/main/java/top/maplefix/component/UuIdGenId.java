package top.maplefix.component;

import tk.mybatis.mapper.genid.GenId;

import java.util.UUID;

/**
 * @author : Maple
 * @description : 实现tk的id生成策略（tk只提供接口未实现）
 * @date : Created in 2020/1/15 14:33
 */
public class UuIdGenId implements GenId<String> {

    @Override
    public String genId(String table, String column) {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
