package cn.gan.web.sys.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SysPerm {

    private String id;

    private String parentId;

    private String name;

    private String code;

    private String note;

    private boolean disabled;

    private boolean hasChildren;

    private String href;

    private String type;

    private Date createTime;

    private Date updateTime;

    private String opBy;

    private List<SysPerm> children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
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

    public boolean isHasChildren() {
        return hasChildren;
    }

    public void setHasChildren(boolean hasChildren) {
        this.hasChildren = hasChildren;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<SysPerm> getChildren() {
        return children;
    }

    public void setChildren(List<SysPerm> children) {
        this.children = children;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public static List<SysPerm> toTrees(List<SysPerm> perms){
        // 存储最终的结果。
        List<SysPerm> result = new ArrayList<>();
        if (perms == null || perms.isEmpty()){
            return new ArrayList<SysPerm>();
        }
        // 将List转换成Map.
        Map<String, SysPerm> tempMap = perms.stream()
                .collect(Collectors.toMap(one -> one.getId(), one -> one));
        perms.forEach(one -> {
            if (one.getParentId() != null && tempMap.containsKey(one.getParentId())){
                SysPerm parent = tempMap.get(one.getParentId());
                if (parent.getChildren() == null){
                    parent.setChildren(new ArrayList<>());
                }
                parent.getChildren().add(one);
            }else { // 此时这个节点属于一个根节点。
                result.add(one);
            }
        });
        return result;
    }
}
