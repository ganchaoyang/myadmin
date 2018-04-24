package cn.gan.web.cms.bean.mapper;

import cn.gan.web.cms.bean.CmsArticle;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.mapping.StatementType;

@Mapper
public interface CmsArticleMapper {

    @Insert("insert into t_article(id, title, content, mode, op_by, " +
            "views, type, status, create_time, update_time) values(#{article.id}, " +
            "#{article.title}, #{article.content}, #{article.mode}, #{article.opBy}, " +
            "#{article.views}, #{article.type}, #{article.status}, #{article.createTime}, #{article.updateTime})")
    @SelectKey(statement = "select replace(UUID(), '-', '') as id", keyProperty = "article.id",
            before = true, statementType = StatementType.STATEMENT, resultType = String.class)
    int insert(CmsArticle article);

}
