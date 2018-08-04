package dao.mappers;

import event.Event;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MessageMapper {

    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "text", column = "text")
    })

    @Select("SELECT * FROM message WHERE id = #{id}")
    List<Event> selectById(@Param("id") Long id);

}
