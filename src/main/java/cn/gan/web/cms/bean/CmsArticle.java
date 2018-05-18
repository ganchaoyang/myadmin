package cn.gan.web.cms.bean;

import java.util.Date;

public class CmsArticle {

    public enum Mode{
        PUBLIC,     // 公开
        PRIVATE     // 保密
    }

    public enum Type{
        ARTICLE,    // 普通文章。
        BLOG        // 博客
    }

    public enum Status{
        DRAFT,      // 草稿
        PUBLISHED,  // 已发布
        DELETED     // 已删除
    }

    public enum ContentType{
        MD,         // 使用MarkDown语法。
        HTML,       // 使用HTML语法。
        TEXT        // 纯Text
    }

    // 文章id,主键。
    private String id;

    // 文章标题。
    private String title;

    // 文章副标题。
    private String subtitle;

    // 文章内容。
    private String content;

    // 文章内容所使用的语法。
    private ContentType contentType;

    // 模式（公开，保密）
    private Mode mode;

    // 浏览数
    private Integer views;

    // 类型，目前有博客
    private Type type;

    private Status status;

    // 创建时间。
    private Date createTime;

    // 更新时间。
    private Date updateTime;

    // 操作人，文章的作者。
    private String opBy;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getOpBy() {
        return opBy;
    }

    public void setOpBy(String opBy) {
        this.opBy = opBy;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }
}
