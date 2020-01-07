package top.maplefix.utils;

import lombok.Data;
import org.apache.poi.ss.formula.functions.T;

import java.io.Serializable;
import java.util.List;

/**
 * @author : Maple
 * @description : 重写github的分页实体
 * @date : Created in 2019/7/24 23:07
           Edited in 2019/10/30 17:17
 * @version : v1.0
 */
@Data
public class PageData implements Serializable {

    private static final long serialVersionUID = 3332764125501265898L;
    /**
     * 当前页
     */
    private Integer currPage;

    /**
     * 每页记录数
     */
    private Integer pageSize;

    /**
     * 总记录数
     */
    private long totalCount;

    /**
     * 总页数
     */
    private Integer totalPage;

    /**
     * 数据列表
     */
    private List<T> list;


    public PageData() { }

    /** 封装PageInfo分页对象
     * 注：PageHelper在只有一页数据时显示pageSize和Pages为0
     * @param pageInfo
     */
    public PageData(com.github.pagehelper.PageInfo pageInfo) {

        this.currPage = pageInfo.getPageNum();
        this.pageSize = pageInfo.getPageSize() == 0 ? 1 : pageInfo.getPageSize();
        this.totalCount = pageInfo.getTotal();
        this.totalPage = pageInfo.getPages() == 0 ? 1 : pageInfo.getPages();
        this.list = pageInfo.getList();
    }

    public Integer getCurrPage() {
        return currPage;
    }

    public void setCurrPage(Integer currPage) {
        this.currPage = currPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
