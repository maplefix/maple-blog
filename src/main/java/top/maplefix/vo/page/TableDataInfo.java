package top.maplefix.vo.page;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Maple
 * @description 表格分页数据对象
 * @date 2020/3/12 17:18
 */
@Data
public class TableDataInfo implements Serializable {
    /**
     * 总记录数
     */
    private long total;

    /**
     * 列表数据
     */
    private List<?> rows;

    /**
     * 消息状态码
     */
    private int code;

    /**
     * 消息内容
     */
    private int msg;

    /**
     * 表格数据对象
     */
    public TableDataInfo() {
    }

    /**
     * 分页
     *
     * @param list  列表数据
     * @param total 总记录数
     */
    public TableDataInfo(List<?> list, int total) {
        this.rows = list;
        this.total = total;
    }
}
