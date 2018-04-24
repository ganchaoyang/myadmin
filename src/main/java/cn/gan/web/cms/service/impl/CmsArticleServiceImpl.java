package cn.gan.web.cms.service.impl;

import cn.gan.web.cms.bean.CmsArticle;
import cn.gan.web.cms.bean.mapper.CmsArticleMapper;
import cn.gan.web.cms.service.CmsArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service(value = "cmsArticleService")
public class CmsArticleServiceImpl implements CmsArticleService {

    @Autowired
    private CmsArticleMapper cmsArticleMapper;

    @Override
    public int addCmsArticle(CmsArticle article) {
        return cmsArticleMapper.insert(article);
    }
}
