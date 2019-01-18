package houzm.accumulation.lucene.data;

import lombok.AllArgsConstructor;
import lombok.Data;

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
    private Long phone;
    private String descript;
}
