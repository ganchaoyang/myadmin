package cn.gan.web.cms.controller;

import cn.gan.web.cms.bean.CmsArticle;
import cn.gan.web.cms.service.CmsArticleService;
import cn.gan.web.sys.bean.Result;
import com.alibaba.fastjson.JSON;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("cms/article")
public class ArticleController {

    @Autowired
    private CmsArticleService cmsArticleService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @RequiresUser
    public Result<String> add(@RequestBody CmsArticle cmsArticle, HttpSession session){
        System.out.println(JSON.toJSONString(cmsArticle));
        cmsArticle.setType(CmsArticle.Type.ARTICLE);
        cmsArticle.setStatus(CmsArticle.Status.DRAFT);
        cmsArticle.setOpBy((String) session.getAttribute("me"));
        int result = cmsArticleService.addCmsArticle(cmsArticle);
        if (result == 1) {
            return Result.success("添加文章成功！");
        } else {
            return Result.error("添加文章失败了！");
        }
    }

}
