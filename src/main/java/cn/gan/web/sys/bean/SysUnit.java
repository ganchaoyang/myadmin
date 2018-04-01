package cn.gan.web.sys.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SysUnit {

    private String id;

    private String parentId;

    private String path;

    private String name;

    private String aliasName;

    private String unitCode;

    private String note;

    private String address;

    private String telePhone;

    private String email;

    private String website;

    private boolean hasChildren;

    private Date createTime;

    private Date updateTime;

    private String opBy;

    private List<SysUnit> children;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelePhone() {
        return telePhone;
    }

    public void setTelePhone(String telePhone) {
        this.telePhone = telePhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public boolean isHasChildren() {
        return hasChildren;
    }

    public void setHasChildren(boolean hasChildren) {
        this.hasChildren = hasChildren;
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

    public List<SysUnit> getChildren() {
        return children;
    }

    public void setChildren(List<SysUnit> children) {
        this.children = children;
    }

    public static List<SysUnit> toTrees(List<SysUnit> units){
        // 存储最终的结果。
        List<SysUnit> result = new ArrayList<>();
        if (units == null || units.isEmpty()){
            return new ArrayList<SysUnit>();
        }
        // 将List转换成Map.
        Map<String, SysUnit> tempMap = units.stream()
                .collect(Collectors.toMap(one -> one.getId(), one -> one));
        units.forEach(one -> {
            if (one.getParentId() != null && tempMap.containsKey(one.getParentId())){
                SysUnit parent = tempMap.get(one.getParentId());
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
