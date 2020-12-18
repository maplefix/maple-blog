package top.maplefix.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.maplefix.mapper.CarouselMapper;
import top.maplefix.model.Carousel;
import top.maplefix.service.CarouselService;
import top.maplefix.utils.DateUtils;

import java.util.List;

/**
 * @author Maple
 * @description 轮播图service实现类
 * @date 2020/3/18 14:12
 */
@Service
public class CarouselServiceImpl implements CarouselService {

    @Autowired
    CarouselMapper carouselMapper;

    @Override
    public Carousel selectCarouselById(Long id) {
        return carouselMapper.selectCarouselById(id);
    }

    @Override
    public List<Carousel> selectCarouselList(Carousel carousel) {
        return carouselMapper.selectCarouselList(carousel);
    }

    @Override
    public int insertCarousel(Carousel carousel) {
        carousel.setCreateDate(DateUtils.getTime());
        return carouselMapper.insertCarousel(carousel);
    }

    @Override
    public int updateCarousel(Carousel carousel) {
        carousel.setUpdateDate(DateUtils.getTime());
        return carouselMapper.updateCarousel(carousel);
    }

    @Override
    public int deleteCarouselById(Long id) {
        return carouselMapper.deleteCarouselById(id);
    }
}
