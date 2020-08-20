package top.maplefix.utils.page;

import top.maplefix.utils.ServletUtils;
import top.maplefix.utils.StringUtils;

/**
 * @author Maple
 * @description 分页数据处理
 * @date 2020/1/22 16:32
 */
public class PageSupport {

    private PageSupport() { }

    /**
     * 当前记录起始索引
     */
    public static final String PAGE_NUM = "pageNum";

    /**
     * 每页显示记录数
     */
    public static final String PAGE_SIZE = "pageSize";

    /**
     * 排序列
     */
    public static final String ORDER_BY_COLUMN = "orderByColumn";

    /**
     * 排序的方向 "desc" 或者 "asc".
     */
    public static final String IS_ASC = "isAsc";

    /**
     * 封装分页对象
     */
    public static PageModel getPageModel() {
        PageModel pageModel = new PageModel();
        pageModel.setPageNum(ServletUtils.getParameterToInt(PAGE_NUM));
        pageModel.setPageSize(ServletUtils.getParameterToInt(PAGE_SIZE));
        pageModel.setOrderByColumn(StringUtils.isEmpty(ServletUtils.getParameter(ORDER_BY_COLUMN)) ? "createDate" : ServletUtils.getParameter(ORDER_BY_COLUMN));
        pageModel.setIsAsc(StringUtils.isEmpty(ServletUtils.getParameter(IS_ASC)) ? "desc" : ServletUtils.getParameter(IS_ASC));
        return pageModel;
    }

    public static PageModel buildPageRequest() {
        return getPageModel();
    }
}
