package com.example.administrator.changeskin.attr;

/**
 * 皮肤指定属性及其对应的值/类型的实体类封装
 * qqliu
 * 2016/9/24.
 */
public class SkinAttr {
    /***
     * 对应View的属性,如：textColor
     */
    public String mAttrName;

    /***
     * 属性值对应的reference id值，类似R.color.XX
     */
    public int mAttrValueRefId;

    /***
     * 属性值refrence id对应的名称，如R.color.redColor，则此值为redColor
     */
    public String mAttrValueRefName;

    /***
     * 属性值refrence id对应的类型，如R.color.XX，则此值为color
     */
    public String mAttrValueTypeName;

    public SkinAttr() {
        //empty
    }

    //mipmap:icon_home:src
    public SkinAttr(String resType, String resName, String typeName) {
        mAttrValueTypeName = resType;
        mAttrValueRefName = resName;
        mAttrName = typeName;
        //others is empty
    }

    @Override
    public String toString() {
        return "SkinAttr{" +
                "mAttrName='" + mAttrName + '\'' +
                ", mAttrValueRefId=" + mAttrValueRefId +
                ", mAttrValueRefName='" + mAttrValueRefName + '\'' +
                ", mAttrValueTypeName='" + mAttrValueTypeName + '\'' +
                '}';
    }
}
