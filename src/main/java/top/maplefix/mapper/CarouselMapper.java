package top.maplefix.mapper;

import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import top.maplefix.model.Carousel;

import java.util.List;

/**
 * @author Maple
 * @description 轮播图mapper
 * @date 2020/3/18 14:13
 */
public interface CarouselMapper extends Mapper<Carousel> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Carousel selectCarouselById(Long id);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param carousel 实例对象
     * @return 对象列表
     */
    List<Carousel> selectCarouselList(Carousel carousel);

    /**
     * 新增数据
     *
     * @param carousel 实例对象
     * @return 影响行数
     */
    int insertCarousel(Carousel carousel);

    /**
     * 修改数据
     *
     * @param carousel 实例对象
     * @return 影响行数
     */
    int updateCarousel(Carousel carousel);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteCarouselById(@Param("id") Long id);
}
