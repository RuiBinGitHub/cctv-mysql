package com.springboot.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppBean implements Serializable {

    private static final long serialVersionUID = 1L;

    // 保存管道坐标
    private double x;
    private double y;
    private double h;

    // 保存管道深度
    private String clevel;
    private String ilevel;
    private String depth;

}
