package cn.sharenotes.wxapi.system.generator;

import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentityGenerator;

import java.io.Serializable;

/**
 * @author kiwi
 * @date 2019/11/24 15:36
 */
public class SnowflakeIDGenerator  extends IdentityGenerator {
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws MappingException {
        SnowFlakeWorker idWorker = new SnowFlakeWorker(0, 0);
        Object id =  idWorker.nextId();
        if (id != null) {
            return (Serializable) id;
        }
        return super.generate(session, object);
    }
}
