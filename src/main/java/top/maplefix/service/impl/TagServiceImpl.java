package top.maplefix.service.impl;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.maplefix.enums.TagType;
import top.maplefix.mapper.TagMapper;
import top.maplefix.model.BlogTagMid;
import top.maplefix.model.Tag;
import top.maplefix.service.TagService;
import top.maplefix.utils.ConvertUtils;
import top.maplefix.utils.SecurityUtils;
import top.maplefix.utils.StringUtils;

import java.util.List;
import java.util.Random;

/**
 * @author : Maple
 * @description : 博客标签接口实现类
 * @date : 2019/7/24 22:55
 */
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Override
    public List<Tag> selectTagList(Tag tag) {
        return tagMapper.selectTagList(tag);
    }

    @Override
    public int insertTag(Tag tag) {
        return tagMapper.insertTag(tag);
    }

    @Override
    public Tag selectTagById(String id) {
        return tagMapper.selectTagById(id);
    }

    @Override
    public int updateTag(Tag tag) {
        return tagMapper.updateTag(tag);
    }

    @Override
    public int deleteTagByIds(String ids) {
        return tagMapper.deleteTagByIds(ConvertUtils.toStrArray(ids), SecurityUtils.getUsername());
    }

    @Override
    public int deleteTagMid(BlogTagMid blogTagMid) {
        return tagMapper.deleteTagMid(blogTagMid);
    }

    @Override
    public Tag selectTagByTitle(String title, Integer type) {
        return tagMapper.selectTagByTitle(title, type);
    }

    @Override
    public int insertTagMid(BlogTagMid blogTagMid) {
        return tagMapper.insertTagMid(blogTagMid);
    }

    @Override
    public void updateTagMid(Integer type, String id, List<String> tagTitleList) {
        //删除该Id下的所有关联
        BlogTagMid tagMapping = BlogTagMid.builder()
                .blogId(TagType.BLOG.getType().equals(type) ? id : null)
                .bookId(TagType.BOOK.getType().equals(type) ? id : null)
                .build();
        deleteTagMid(tagMapping);

        if (ObjectUtils.isNotEmpty(tagTitleList)) {
            for (String title : tagTitleList) {
                //搜索所有的tag
                Tag tag = selectTagByTitle(title.trim(), TagType.BLOG.getType());
                if (tag != null) {
                    tagMapping.setTagId(tag.getTagId());
                    insertTagMid(tagMapping);
                } else {
                    Tag temp = new Tag(title.trim(), StringUtils.format("rgba({}, {}, {}, {})", getRandomNum(255), getRandomNum(255), getRandomNum(255), 1), TagType.BLOG.getType());
                    insertTag(temp);
                    tagMapping.setTagId(temp.getTagId());
                    insertTagMid(tagMapping);
                }
            }
        }
    }

    @Override
    public List<Tag> selectTagListByTypeAndId(Integer type, String  id) {
        return tagMapper.selectTagListByType(type, id);
    }

    /**
     * 获取随机数
     *
     * @param num 最大范围
     * @return 随机数
     */
    private int getRandomNum(int num) {
        Random random = new Random();
        return random.nextInt(num);
    }
}
