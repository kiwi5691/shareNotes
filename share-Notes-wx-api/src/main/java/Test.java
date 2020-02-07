import cn.sharenotes.db.DbApplication;
import cn.sharenotes.db.mapper.UserMapper;
import cn.sharenotes.wxapi.WxApiApplication;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DbApplication.class)
public class Test {


    @Autowired
    private UserMapper userMapper;

    @org.junit.Test
    public void testOne(){
        Map<String,Object> h = new HashMap<>();
        h=userMapper.selectNameAndAvatarById(3);

        for (String key : h.keySet()) {
            System.out.println("key= "+ key + " and value= " + h.get(key));
        }

    }
}
