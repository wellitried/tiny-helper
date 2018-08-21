package dao.mappers;

import dao.SessionService;
import event.Event;
import message.Message;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.session.SqlSession;

import java.util.Date;
import java.util.List;

public interface EventMapper {

    @Select("SELECT * FROM event WHERE date BETWEEN #{from} AND #{to}")
    @Results(id = "event", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "date", column = "date"),
            @Result(property = "emailNotification", column = "email_notification"),
            @Result(property = "pushNotification", column = "push_notification"),
            @Result(property = "message", column = "message_id",
                    javaType = Message.class, one = @One(select = "dao.mappers.MessageMapper.selectById"))
    })
    List<Event> selectInInterval(@Param("from") Date from, @Param("to") Date to);


    @Select("SELECT * FROM event WHERE id = #{id}")
    @ResultMap("event")
    Event selectById(@Param("id") Long id);


    @Insert("INSERT INTO event(" +
            "date, " +
            "email_notification, " +
            "push_notification, " +
            "message_id" +
            ") " +
            "VALUES (" +
            "#{event.date}, " +
            "#{event.emailNotification}, " +
            "#{event.pushNotification}, " +
            "#{event.message.id}" +
            ")")
    @Options(useGeneratedKeys = true, keyProperty = "event.id", keyColumn = "id")
    void create(@Param("event") Event event);


    @Update("UPDATE event SET " +
            "date=#{event.date}, " +
            "email_notification=#{event.emailNotification}, " +
            "push_notification=#{event.pushNotification}" +
            "WHERE id=#{event.id}")
    void update(@Param("event") Event event);


    @Delete("DELETE FROM event WHERE id=#{id}")
    void delete(@Param("id") Long id);


    default void createWithMessage(Event event) {
        if (event.getMessage() != null) {
            SqlSession session = SessionService.getSession();
            session.getMapper(MessageMapper.class).create(event.getMessage());
            session.commit();
            session.close();
        }
        create(event);
    }

    default void deleteWithMessage(Event event) {
        if (event.getMessage() != null) {
            SqlSession session = SessionService.getSession();
            session.getMapper(MessageMapper.class).delete(event.getMessage().getId());
            session.commit();
            session.close();
        }
        delete(event.getId());
    }

}
