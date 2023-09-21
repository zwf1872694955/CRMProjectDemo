package com.zwf.crm.pojo;

/**
 * @author Mr Zeng
 * @version 1.0
 * @date 2023-09-15 14:38
 */
public class ModuleModel  {
    private Integer id;
    private String name;
    private Integer pId;

    private boolean checked=false;  //是否被选中  true表示被选中 false表示未选中

    private boolean open=true;  //默认展开所有的树形结构

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }
}