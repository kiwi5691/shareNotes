package cn.sharenotes.db.utils;

import cn.sharenotes.db.model.dto.GroupDto;
import cn.sharenotes.db.model.dto.GroupDtoKey;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class ForMateFriendUtil {
    public  static  String friendList(Map<GroupDtoKey, List<GroupDto>> groupDtoMap){
        StringBuffer result = new StringBuffer();
        for (GroupDtoKey groupKey:
             groupDtoMap.keySet()) {
            result.append("{id:"+groupKey.getId()+",region:"+groupKey.getRegion()+",");
            log.info("huhuhuu"+groupDtoMap.get(groupKey).toString());
            List<GroupDto> groupDtos = new ArrayList<>();
            groupDtos =   groupDtoMap.get(groupKey);
            log.info("huhuhuu"+groupDtos.get(0).toString());
            result.append("items:[{");

            for (int i=0;i<groupDtos.size();i++) {

                GroupDto groupDto= groupDtos.get(i);

                result.append("{id:"+groupDto.getId()+",name:"+groupDto.getNickname()+",avatar:"+groupDto.getAvatar()+"}");
                result.append(",");
            }
            result.delete(result.lastIndexOf(","),result.lastIndexOf(","));
            result.append("}]}");
        }
        return  result.toString();
    }

}
