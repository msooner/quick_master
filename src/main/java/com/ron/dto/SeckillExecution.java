package com.ron.dto;

import com.ron.entity.SeckillOrder;
import com.ron.common.enums.HttpStatusEnum;

/**
 * 封装执行秒杀后的结果
 *
 * @auther Ron
 * @date 2019/10/8
 */
public class SeckillExecution {

    private Long seckillId;

    //秒杀执行结果状态
    private int state;

    //状态表示
    private String stateInfo;

    //秒杀成功的订单对象
    private SeckillOrder seckillOrder;

    public SeckillExecution(Long seckillId, HttpStatusEnum httpStatusEnum, SeckillOrder seckillOrder) {
        this.seckillId = seckillId;
        this.state = httpStatusEnum.getState();
        this.stateInfo = httpStatusEnum.getStateInfo();
        this.seckillOrder = seckillOrder;
    }

    public SeckillExecution(Long seckillId, HttpStatusEnum httpStatusEnum) {
        this.seckillId = seckillId;
        this.state = httpStatusEnum.getState();
        this.stateInfo = httpStatusEnum.getStateInfo();
    }

    public Long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(Long seckillId) {
        this.seckillId = seckillId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public SeckillOrder getSeckillOrder() {
        return seckillOrder;
    }

    public void setSeckillOrder(SeckillOrder seckillOrder) {
        this.seckillOrder = seckillOrder;
    }

    @Override
    public String toString() {
        return "SeckillExecution{" +
                "seckillId=" + seckillId +
                ", state=" + state +
                ", stateInfo='" + stateInfo + '\'' +
                ", seckillOrder=" + seckillOrder +
                '}';
    }
}
