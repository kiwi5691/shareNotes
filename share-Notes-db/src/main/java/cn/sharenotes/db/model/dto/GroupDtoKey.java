package cn.sharenotes.db.model.dto;

import lombok.Data;

import java.util.List;

/**
 * 这里加的是id和字母
 *
 * @author hu
 */
@Data
public class GroupDtoKey implements Comparable<GroupDtoKey>{
    int id;
    char region;

    public GroupDtoKey(int id, char region) {
        this.id = id;
        this.region = region;
    }

    @Override
    public String toString() {
        return "[{" +
                "id:" + id +
                ", region:" + region +
                "}" ;
    }


    @Override
    public int compareTo(GroupDtoKey o) {
        return this.id - o.getId();
    }
}
