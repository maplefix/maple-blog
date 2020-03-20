package top.maplefix.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Maple
 * @description dashboard折线图数据
 * @date 2020/3/13 16:17
 */
@Data
public class LineChartData<T> implements Serializable {

    public static final String BLOG_LINE = "blog";
    public static final String NOTE_LINE = "note";
    public static final String BOOK_LINE = "book";
    public static final String VISITOR_LINE = "visitor";
    /**
     * 期望值,即前一段时间的值
     */
    List<T> expectedData;
    /**
     * 实际值,即当前时间的值
     */
    List<T> actualData;
    /**
     * X轴数据
     */
    List<String> axisData;
}

