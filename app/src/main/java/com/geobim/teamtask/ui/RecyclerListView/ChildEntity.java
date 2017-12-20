package com.geobim.teamtask.ui.RecyclerListView;

/**
 * 子项数据的实体类
 */
public class ChildEntity {

    private String key;
    private Object value;

    public ChildEntity(String key,Object value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
