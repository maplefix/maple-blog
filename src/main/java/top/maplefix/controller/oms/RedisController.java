package top.maplefix.controller.oms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import top.maplefix.common.BaseResult;
import top.maplefix.redis.RedisCacheService;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * @author Maple
 * @description redis监控controller
 * @date 2020/3/20 10:43
 */
@RestController
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    private RedisCacheService redisCacheService;
    @PreAuthorize("@permissionService.hasPermission('monitor:redis:list')")
    @GetMapping("/list")
    public BaseResult getRedisInfoList() {
        return BaseResult.success(redisCacheService.getRedisInfoList());
    }

    @PreAuthorize("@permissionService.hasPermission('monitor:redis:add')")
    @PostMapping
    public BaseResult add(@RequestParam String key, @RequestParam String value, @RequestParam Integer time) {
        redisCacheService.setCacheObject(key, value, time, TimeUnit.SECONDS);
        return BaseResult.success();
    }

    @PreAuthorize("@permissionService.hasPermission('monitor:redis:edit')")
    @PutMapping
    public BaseResult edit(@RequestParam String key, @RequestParam String value, @RequestParam Integer time) {
        redisCacheService.setCacheObject(key, value, time, TimeUnit.SECONDS);
        return BaseResult.success();
    }

    /**
     * 获取实时Key的大小
     */
    @GetMapping("keySize")
    @PreAuthorize("@permissionService.hasPermission('monitor:redis:query')")
    public BaseResult getKeySize() {
        RedisTemplate redisTemplate = redisCacheService.getRedisTemplate();
        Map<String, Object> map = new HashMap<>();
        Long size = redisTemplate.getConnectionFactory().getConnection().dbSize();
        map.put("keySize", size);
        map.put("time", new Date());
        return BaseResult.success(map);
    }

    @GetMapping("memory")
    @PreAuthorize("@permissionService.hasPermission('monitor:redis:query')")
    public BaseResult getMemory() {
        RedisTemplate redisTemplate = redisCacheService.getRedisTemplate();
        Map<String, Object> map = new HashMap<>();
        Properties memory = redisTemplate.getConnectionFactory().getConnection().info("memory");
        map.put("memory", memory.get("used_memory"));
        map.put("time", new Date());
        return BaseResult.success(map);
    }

    @DeleteMapping()
    @PreAuthorize("@permissionService.hasPermission('monitor:redis:remove')")
    public BaseResult removeAll() {
        redisCacheService.deleteObject("*");
        return BaseResult.success();
    }

    @DeleteMapping("/{key}")
    @PreAuthorize("@permissionService.hasPermission('monitor:redis:remove')")
    public BaseResult remove(@PathVariable String key) {
        redisCacheService.deleteObject(key);
        return BaseResult.success();
    }
}
