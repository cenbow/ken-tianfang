package com.tianfang.admin.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA. User: Administrator Date: 14-8-11 Time: 下午4:55 To change this template use File |
 * Settings | File Templates.
 */
public class MenuRespDto implements Serializable
{

    private SportMenuRespDto parant;
    private List<SportMenuRespDto> leaf;

    public SportMenuRespDto getParant() {
        return parant;
    }

    public void setParant(SportMenuRespDto parant) {
        this.parant = parant;
    }

    public List<SportMenuRespDto> getLeaf() {
        return leaf;
    }

    public void setLeaf(List<SportMenuRespDto> leaf) {
        this.leaf = leaf;
    }

}
