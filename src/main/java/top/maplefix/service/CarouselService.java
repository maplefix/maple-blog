package top.maplefix.service;

import top.maplefix.model.Carousel;

import java.util.List;

/**
 * @author Maple
 * @description 轮播图service接口
 * @date 2020/3/18 14:12
 */
public interface CarouselService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Carousel selectCarouselById(String id);

    /**
     * 查询轮播图列表
     *
     * @param carousel 查询对象
     * @return List
     */
    List<Carousel> selectCarouselList(Carousel carousel);

    /**
     * 新增数据
     *
     * @param carousel 实例对象
     * @return 受影响行数
     */
    int insertCarousel(Carousel carousel);

    /**
     * 修改数据
     *
     * @param carousel 实例对象
     * @return 受影响行数
     */
    int updateCarousel(Carousel carousel);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 受影响行数
     */
    int deleteCarouselById(String id);
}
