package top.maplefix.controller.system;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.maplefix.annotation.OLog;
import top.maplefix.common.BaseResult;
import top.maplefix.constant.Constant;
import top.maplefix.controller.common.BaseController;
import top.maplefix.enums.BusinessType;
import top.maplefix.model.Carousel;
import top.maplefix.service.CarouselService;
import top.maplefix.vo.page.TableDataInfo;

import java.util.List;

/**
 * @author Maple
 * @description 轮播图数据接口
 * @date 2020/3/18 14:10
 */
@RestController
@RequestMapping("/system/carousel")
@Slf4j
public class CarouselController extends BaseController {
    
    @Autowired
    private CarouselService carouselService;

    @PreAuthorize("@permissionService.hasPermission('system:carousel:list')")
    @GetMapping("/list")
    public TableDataInfo list(Carousel carousel) {
        startPage();
        List<Carousel> list = carouselService.selectCarouselList(carousel);
        return getDataTable(list);
    }

    @PreAuthorize("@permissionService.hasPermission('system:carousel:query')")
    @GetMapping(value = "/{id}")
    public BaseResult getInfo(@PathVariable Long id) {
        return BaseResult.success(carouselService.selectCarouselById(id));
    }

    @PreAuthorize("@permissionService.hasPermission('system:carousel:add')")
    @OLog(module = "轮播图管理", businessType = BusinessType.INSERT)
    @PostMapping
    public BaseResult add(@RequestBody @Validated Carousel carousel) {
        return toResult(carouselService.insertCarousel(carousel));
    }

    @PreAuthorize("@permissionService.hasPermission('system:carousel:edit')")
    @OLog(module = "轮播图管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public BaseResult edit(@RequestBody Carousel carousel) {
        return toResult(carouselService.updateCarousel(carousel));
    }

    @PreAuthorize("@permissionService.hasPermission('system:carousel:edit')")
    @OLog(module = "轮播图管理", businessType = BusinessType.UPDATE)
    @PutMapping("/{id}/display/{display}")
    public BaseResult changeDisplay(@PathVariable Long id, @PathVariable Boolean display) {
        Carousel carousel = new Carousel();
        carousel.setDisplay(Constant.DISPLAY);
        carousel.setId(id);
        return toResult(carouselService.updateCarousel(carousel));
    }

    @PreAuthorize("@permissionService.hasPermission('system:carousel:edit')")
    @OLog(module = "轮播图管理", businessType = BusinessType.UPDATE)
    @PutMapping("/{id}/target/{target}")
    public BaseResult changeTarget(@PathVariable Long id, @PathVariable Boolean target) {
        Carousel carousel = new Carousel();
        carousel.setTarget(Constant.TRUE);
        carousel.setId(id);
        return toResult(carouselService.updateCarousel(carousel));
    }

    @PreAuthorize("@permissionService.hasPermission('system:carousel:remove')")
    @OLog(module = "轮播图管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    public BaseResult remove(@PathVariable Long id) {
        return toResult(carouselService.deleteCarouselById(id));
    }
}
