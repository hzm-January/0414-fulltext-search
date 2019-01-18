package houzm.accumulation.lucene.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * author: hzm_dream@163.com
 * date: 2019/1/15 17:44
 * description:
 */
public class Datas {
    public static List<User> usersList = new ArrayList<>(Arrays.asList(
            new User(UUID.randomUUID().toString(), "houzm", "america", 15261530111L, "houzm is a bad boy, he is stronger"),
            new User(UUID.randomUUID().toString(), "Tom", "japan", 15377730111L, "Tom is a good boy, his girl friend also is good"),
            new User(UUID.randomUUID().toString(), "Jack", "canada", 15355730234L, "Jack like eat fruits, and the favorite is banana"),
            new User(UUID.randomUUID().toString(), "Jerry", "china", 15355730234L,"Jerry works harder always, make a lot of job every day")
    ));
//    public Datas(){
//        usersList.add(new User(UUID.randomUUID().toString(), "houzm", "america", "15261530111"));
//        usersList.add(new User(UUID.randomUUID().toString(), "Tom", "japan", "15377730111"));
//        usersList.add(new User(UUID.randomUUID().toString(), "Jack", "canada", "15355730234"));
//        usersList.add(new User(UUID.randomUUID().toString(), "Jerry", "china", "15355730234"));
//    }

    public List<User> getUsersList() {
        return usersList;
    }
}
