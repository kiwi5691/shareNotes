package cn.sharenotes.wxapi.service.Impl;

import cn.sharenotes.core.redis.RedisManager;
import cn.sharenotes.core.service.PostContentService;
import cn.sharenotes.db.domain.Categories;
import cn.sharenotes.db.domain.Logs;
import cn.sharenotes.db.mapper.CategoriesMapper;
import cn.sharenotes.db.mapper.LogsMapper;
import cn.sharenotes.db.model.dto.CategoryDTO;
import cn.sharenotes.db.model.dto.PostDTO;
import cn.sharenotes.wxapi.service.LogService;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Method;
import java.util.*;

/**
 * @author kiwi
 */
@Service
@Slf4j
public class LogServiceImpl implements LogService {

    @Autowired
    LogsMapper logsMapper;

    @Autowired
    RedisManager redisManager;

    @Autowired
    PostContentService postContentService;

    @Autowired
    CategoriesMapper categoriesMapper;

    @Override
    public Logs getLogInfo(ProceedingJoinPoint point, Logs logs) {

        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();

        cn.sharenotes.wxapi.annotation.Log logAnnotation = method.getAnnotation(cn.sharenotes.wxapi.annotation.Log.class);
        String value = logAnnotation.value();

        // 请求的方法参数值
        Object[] args = point.getArgs();

        // 请求的方法参数名称
        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
        String[] paramNames = u.getParameterNames(method);

        if (args != null && paramNames != null) {
            Map<Object, Object> paramsMap = new HashMap<>();
            paramsMap = handleParams(args, Arrays.asList(paramNames));
            value = handleAop(paramsMap, value);
        }

        if (logAnnotation != null) {
            // 注解上的描述
            logs.setContent(value);
        }

        return logs;
    }

    @Override
    public Integer addLog(Logs logs) {
        return logsMapper.insert(logs);
    }

    private Map<Object, Object> handleParams(Object[] args, List paramNames) {
        Map<Object, Object> params = new HashMap<>();

        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof Map) {
                Set set = ((Map) args[i]).keySet();
                List<Object> list = new ArrayList<>();
                List<Object> paramList = new ArrayList<>();
                for (Object key : set) {
                    list.add(((Map) args[i]).get(key));
                    paramList.add(key);
                }
                return handleParams(list.toArray(), paramList);
            } else {
                if (args[i] instanceof MultipartFile) {
                    MultipartFile file = (MultipartFile) args[i];
                    params.put(paramNames.get(i), file.getName());
                } else {
                    params.put(paramNames.get(i), args[i]);
                }
            }
        }
        return params;
    }

    public String handleAop(Map<Object, Object> map, String description) {

        String logValue = null;
        if (description.contains("目录")) {
            JSONObject body = JSONObject.fromObject(map.get("body"));
            Integer menuId = null;
            String menuName = null;
            if (description.contains("删除")) {
                String menuType = (String) body.get("menu_id");
                if (menuType.equals("tab1")) {
                    menuName = "公共目录";
                    menuId = 1;
                } else {
                    menuName = "私人目录";
                    menuId = 2;
                }
                List<CategoryDTO> categoryDTOS = (List<CategoryDTO>) redisManager.getList("OWNER_MENUID" + ":menuIds :" + menuId + "userId:" + map.get("userId"));
                for (CategoryDTO categoryDTO : categoryDTOS) {
                    if (body.get("cateId").equals(categoryDTO.getId().toString())) {
                        logValue = "删除" + menuName + "下的<" + categoryDTO.getName() + ">目录";
                    }
                }
            } else {
                Boolean menuType = (Boolean) body.get("isPcOrPr");
                if (menuType) {
                    menuName = "公共目录";
                } else {
                    menuName = "私人目录";
                }
                if (description.contains("添加")) {
                    logValue = "添加目录<" + body.get("name") + ">到<" + menuName+">";
                } else {
                    logValue = "修改<" + menuName + ">下的<" + body.get("name") + ">目录";
                }
            }
        } else if (description.contains("文章")) {
            JSONObject body = JSONObject.fromObject(map.get("body"));
            if (description.contains("删除")) {
                Integer cateId = postContentService.findCateIdByPostId(Integer.parseInt(body.get("postId").toString()));
                Categories categories = categoriesMapper.selectByPrimaryKey(cateId);
                List<PostDTO> postDTOS = (List<PostDTO>) redisManager.getList("OWNER_POSTS_BY_CATID" + ":cate :" + cateId);
                for(PostDTO postDTO : postDTOS){
                    if (body.get("postId").equals(postDTO.getId().toString())) {
                        logValue = "删除<" + categories.getName() + ">目录下的<" + postDTO.getTitle() + ">文章";
                    }
                }
            } else {
                Integer cateId = Integer.parseInt(body.get("categoryId").toString());
                Categories categories = categoriesMapper.selectByPrimaryKey(cateId);
                if (description.contains("添加")) {
                    logValue = "添加<" + body.get("title") + ">文章到<" + categories.getName()+">目录";
                } else {
                    logValue = "修改<" + categories.getName() + ">目录下的<" + body.get("title") + ">文章";
                }
            }
        }
        return logValue;
    }
}
