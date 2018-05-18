package cn.gan.web.cms.service.impl;

import cn.gan.web.cms.bean.CmsArticle;
import cn.gan.web.cms.bean.mapper.CmsArticleMapper;
import cn.gan.web.cms.service.CmsArticleService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service(value = "cmsArticleService")
public class CmsArticleServiceImpl implements CmsArticleService {

    @Autowired
    private CmsArticleMapper cmsArticleMapper;

    @Override
    public int addCmsArticle(CmsArticle article) {
        article.setCreateTime(new Date());
        article.setUpdateTime(new Date());
        article.setViews(0);
        return cmsArticleMapper.insert(article);
    }

    @Override
    public PageInfo<CmsArticle> findByPaging(int pageNum, int pageSize) {
        Page page = PageHelper.startPage(pageNum, pageSize);
        List<CmsArticle> articles = cmsArticleMapper.findAll();
        PageInfo<CmsArticle> result = new PageInfo<>(articles);
        return result;
    }

    @Override
    public CmsArticle findById(String id) {
        return cmsArticleMapper.findById(id);
    }

    @Override
    public int updateIgnoreNull(CmsArticle article) {
        return cmsArticleMapper.updateIgnoreNull(article);
    }
}
