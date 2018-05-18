package cn.gan.web.cms.bean.mapper;

import cn.gan.web.cms.bean.CmsArticle;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;

@Mapper
public interface CmsArticleMapper {

    @Insert("insert into t_article(id, title, subtitle, content, content_type, mode, op_by, " +
            "views, type, status, create_time, update_time) values(#{article.id}, " +
            "#{article.title}, #{article.subtitle}, #{article.content}, #{article.contentType}, #{article.mode}, #{article.opBy}, " +
            "#{article.views}, #{article.type}, #{article.status}, #{article.createTime}, #{article.updateTime})")
    @SelectKey(statement = "select replace(UUID(), '-', '') as id", keyProperty = "article.id",
            before = true, statementType = StatementType.STATEMENT, resultType = String.class)
    int insert(@Param("article") CmsArticle article);


    @Select("select * from t_article order by update_time desc")
    List<CmsArticle> findAll();

    @Select("select * from t_article where id = #{id}")
    CmsArticle findById(String id);
}
