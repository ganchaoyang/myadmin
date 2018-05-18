package cn.gan.web.cms.bean.provider;

import cn.gan.web.cms.bean.CmsArticle;

import java.util.Map;

public class CmsArticleDaoProvider {

    public String updateIgnoreNull(Map<String, Object> map){
        CmsArticle article = (CmsArticle) map.get("article");
        StringBuilder sb = new StringBuilder("update t_article set ");
        if (article.getTitle() != null) {
            sb.append("title = #{article.title}, ");
        }
        if (article.getSubtitle() != null) {
            sb.append("subtitle = #{article.subtitle}, ");
        }
        if (article.getContent() != null) {
            sb.append("content = #{article.content}, ");
        }
        if (article.getMode() != null) {
            sb.append("mode = #{article.mode}, ");
        }
        if (article.getOpBy() != null) {
            sb.append("op_by = #{article.opBy}, ");
        }
        if (article.getViews() != null) {
            sb.append("views = #{article.views}, ");
        }
        if (article.getType() != null) {
            sb.append("type = #{article.type}, ");
        }
        if (article.getStatus() != null) {
            sb.append("status = #{article.status}, ");
        }
        if (article.getContentType() != null) {
            sb.append("content_type = #{article.contentType}, ");
        }
        sb.append("update_time = now() where id = #{article.id}");
        return sb.toString();
    }

}
