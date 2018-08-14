package dao.mappers;

import message.Message;
import org.apache.ibatis.annotations.*;

public interface MessageMapper {

    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "text", column = "text")
    })

    @Select("SELECT * FROM message WHERE id = #{id}")
    Message selectById(@Param("id") Long id);

    @Update("UPDATE message SET sent = TRUE WHERE id = #{id}")
    void setSentAsTrue(Message message);
}
