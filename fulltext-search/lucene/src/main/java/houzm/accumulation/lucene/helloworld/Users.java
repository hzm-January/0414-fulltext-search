package houzm.accumulation.lucene.helloworld;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * author: hzm_dream@163.com
 * date: 2019/1/15 17:44
 * description:
 */
public class Users {
    private List<User> usersList = new ArrayList<>(6);
    public Users(){
        usersList.add(new User(UUID.randomUUID().toString(), "houzm", "america", "15261530111"));
        usersList.add(new User(UUID.randomUUID().toString(), "Tom", "japan", "15377730111"));
        usersList.add(new User(UUID.randomUUID().toString(), "Jack", "canada", "15355730234"));
        usersList.add(new User(UUID.randomUUID().toString(), "Jerry", "china", "15355730234"));
    }

    public List<User> getUsersList() {
        return usersList;
    }
}
