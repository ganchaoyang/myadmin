package cn.gan.web.cms.service.impl;

import cn.gan.web.cms.bean.CmsArticle;
import cn.gan.web.cms.bean.mapper.CmsArticleMapper;
import cn.gan.web.cms.service.CmsArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


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
}
