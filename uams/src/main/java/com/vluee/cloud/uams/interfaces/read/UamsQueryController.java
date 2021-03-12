package com.vluee.cloud.uams.interfaces.read;

import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

/**
 * 所有查询职责的方法， 都必须在放在readonly模式下
 */
@RestController
@Transactional(readOnly = true)
@AllArgsConstructor
public class UamsQueryController {
}
