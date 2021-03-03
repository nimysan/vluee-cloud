package com.vluee.cloud.uams.interfaces.read;

import org.springframework.transaction.annotation.Transactional;

/**
 * 所有查询职责的方法， 都必须在放在readonly模式下
 */
@Transactional(readOnly = true)
public class UamsQueryController {
}
