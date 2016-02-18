package com.tianfang.train.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA. User: Administrator Date: 14-8-11 Time: 下午4:55 To change this template use File |
 * Settings | File Templates.
 */
public class MenuRespDto implements Serializable
{

    private TrainingMenuRespDto parant;
    private List<TrainingMenuRespDto> leaf;

    public TrainingMenuRespDto getParant() {
        return parant;
    }

    public void setParant(TrainingMenuRespDto parant) {
        this.parant = parant;
    }

    public List<TrainingMenuRespDto> getLeaf() {
        return leaf;
    }

    public void setLeaf(List<TrainingMenuRespDto> leaf) {
        this.leaf = leaf;
    }

}
