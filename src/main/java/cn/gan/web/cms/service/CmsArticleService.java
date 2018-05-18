package cn.gan.web.cms.service;

import cn.gan.web.cms.bean.CmsArticle;
import com.github.pagehelper.PageInfo;

public interface CmsArticleService {

    int addCmsArticle(CmsArticle article);

    PageInfo<CmsArticle> findByPaging(int pageNum, int pageSize);

    CmsArticle findById(String id);

}
