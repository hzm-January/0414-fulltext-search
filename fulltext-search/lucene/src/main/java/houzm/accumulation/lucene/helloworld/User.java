package houzm.accumulation.lucene.helloworld;

import lombok.AllArgsConstructor;
import lombok.Data;

import com.sun.istack.internal.NotNull;

/**
 * author: hzm_dream@163.com
 * date: 2019/1/15 17:15
 * description:
 */
@Data
@AllArgsConstructor
public class User {
    private String id;
    private String name;
    private String addr;
    private String phone;
}
