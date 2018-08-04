package dao.mappers;

import event.Event;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

public interface EventMapper {

    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "date", column = "date"),
            @Result(property = "emailNotification", column = "email_notification"),
            @Result(property = "pushNotification", column = "push_notification"),
            @Result(property = "message.id", column = "message_id")
    })

    @Select("SELECT * FROM event WHERE date BETWEEN #{from} AND #{to}")
    List<Event> selectInInterval(@Param("from") Date from, @Param("to") Date to);

}
