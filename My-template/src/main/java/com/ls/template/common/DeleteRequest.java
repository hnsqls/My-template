package com.ls.template.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 删除请求
 * @author hnsqls
 */
@Data
public class DeleteRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    private static final long serialVersionUID = 1L;
}