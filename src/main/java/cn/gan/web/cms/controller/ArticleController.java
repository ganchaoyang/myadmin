package cn.gan.web.cms.controller;

import cn.gan.framework.constans.ErrorCode;
import cn.gan.framework.facade.BaseResponse;
import cn.gan.web.cms.bean.CmsArticle;
import cn.gan.web.cms.service.CmsArticleService;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("cms/article")
public class ArticleController {

    @Autowired
    private CmsArticleService cmsArticleService;


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @RequiresUser
    public BaseResponse<String> add(@RequestBody CmsArticle cmsArticle, HttpSession session){
        cmsArticle.setType(CmsArticle.Type.ARTICLE);
        cmsArticle.setStatus(CmsArticle.Status.DRAFT);
        cmsArticle.setOpBy((String) session.getAttribute("me"));
        int result = cmsArticleService.addCmsArticle(cmsArticle);
        if (result == 1) {
            return BaseResponse.success("添加文章成功！");
        } else {
            return BaseResponse.error(ErrorCode.SYSTEM_ERROR, "添加文章失败了！");
        }
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @RequiresUser
    public BaseResponse<PageInfo<CmsArticle>> queryPaging(@RequestParam(
            required = true, defaultValue = "1") Integer pageNum, @RequestParam(
                    required = true, defaultValue = "10") int pageSize){
        PageInfo<CmsArticle> pageInfo = cmsArticleService.findByPaging(pageNum, pageSize);
        System.out.println(JSON.toJSONString(pageInfo));
        return BaseResponse.success(pageInfo);
    }


    @RequestMapping(value = "/data/{id}", method = RequestMethod.GET)
    @RequiresUser
    public BaseResponse<CmsArticle> query(@PathVariable("id") String id){
        CmsArticle article = cmsArticleService.findById(id);
        return BaseResponse.success(article);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.PUT)
    @RequiresUser
    public BaseResponse<String> edit(@RequestBody CmsArticle article){
        int res = cmsArticleService.updateIgnoreNull(article);
        if (res == 1) {
            return BaseResponse.success("更新文章成功！");
        } else {
            return BaseResponse.error(ErrorCode.SYSTEM_ERROR, "更新文章失败！");
        }
    }

}
