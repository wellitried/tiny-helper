package dao.mappers;

import event.Event;
import message.Message;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

public interface EventMapper {

    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "date", column = "date"),
            @Result(property = "emailNotification", column = "email_notification"),
            @Result(property = "pushNotification", column = "push_notification"),
            @Result(property = "message", column = "message_id",
                    javaType = Message.class, one = @One(select = "dao.mappers.MessageMapper.selectById"))
    })

    @Select("SELECT * FROM event WHERE date BETWEEN #{from} AND #{to}")
    List<Event> selectInInterval(@Param("from") Date from, @Param("to") Date to);

}
