package cn.sharenotes.core.aop;

import cn.sharenotes.core.aop.annotation.MethodType;
import cn.sharenotes.core.aop.cache.IndexThreadLocal;
import cn.sharenotes.db.domain.index.PostsIndex;
import cn.sharenotes.db.repository.PostsIndexRepository;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.elasticsearch.client.transport.NoNodeAvailableException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.List;

/**
 * AOP es索引添加
 *
 * @author kiwi
 */
@Slf4j
@Aspect
@Component
public class IndexAfterAspect {


    @Resource
    private PostsIndexRepository postsIndexRepository;


    @Pointcut("@annotation(cn.sharenotes.core.aop.annotation.IndexMethod)")
    public void pointcut() {
        // do nothing
    }

    @AfterReturning(returning = "val",pointcut = "@annotation(cn.sharenotes.core.aop.annotation.IndexMethod)")
    public void after(JoinPoint point,Object val)  {

        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();

        cn.sharenotes.core.aop.annotation.IndexMethod IndexMethodAnnotation = method.getAnnotation(cn.sharenotes.core.aop.annotation.IndexMethod.class);
        String value = IndexMethodAnnotation.method().getName();
        try {
            if(value.equals(MethodType.SAVE.getName())){
                indexSAVE();
            }else if(value.equals(MethodType.UPDATE.getName())){
                indexUPDATE();
            }else if(value.equals(MethodType.DELETE.getName())){
                indexDELETE();
            }else if(value.equals(MethodType.DELETES.getName())){
                indexDELETES();
            }
        }catch (NoNodeAvailableException e){
                log.error("e:"+e);
        }catch (Exception e){
            log.error("ue:"+e);
        }finally {
            if(IndexThreadLocal.get()!=null){
                IndexThreadLocal.remove();
            }else if(IndexThreadLocal.getList()!=null)
            {
            IndexThreadLocal.removeList();
            }
        }


    }

    public void indexSAVE() throws NoNodeAvailableException {
        PostsIndex postsIndex = IndexThreadLocal.get();
        postsIndexRepository.save(postsIndex);
    }

    public void indexUPDATE() throws NoNodeAvailableException {
        PostsIndex postsIndex = IndexThreadLocal.get();
        postsIndexRepository.save(postsIndex);
    }

    public void indexDELETE() throws NoNodeAvailableException {
        PostsIndex postsIndex = IndexThreadLocal.get();
        postsIndexRepository.delete(postsIndex);
    }

    public void indexDELETES() throws NoNodeAvailableException {

        List<PostsIndex> postsIndex = IndexThreadLocal.getList();
        postsIndexRepository.deleteAll(postsIndex);

    }

}
