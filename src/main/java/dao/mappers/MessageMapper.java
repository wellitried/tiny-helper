package dao.mappers;

import message.Message;
import org.apache.ibatis.annotations.*;

public interface MessageMapper {

    @Select("SELECT * FROM message WHERE id = #{id}")
    @Results(id = "message", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "subject", column = "subject"),
            @Result(property = "text", column = "text"),
            @Result(property = "recipient", column = "recipient"),
            @Result(property = "sent", column = "sent")
    })
    Message selectById(@Param("id") Long id);

    @Insert("INSERT INTO message(" +
            "subject, " +
            "text, " +
            "recipient, " +
            "sent" +
            ") " +
            "VALUES (" +
            "#{message.subject}, " +
            "#{message.text}, " +
            "#{message.recipient}, " +
            "#{message.sent}" +
            ")")
    @Options(useGeneratedKeys = true, keyProperty = "message.id", keyColumn = "id")
    void create(@Param("message") Message message);


    @Update("UPDATE message SET " +
            "subject=#{message.subject}, " +
            "text=#{message.text}, " +
            "recipient=#{message.recipient}, " +
            "sent=#{message.sent}" +
            "WHERE id=#{message.id}")
    void update(@Param("message") Message message);


    @Delete("DELETE FROM message WHERE id=#{id}")
    void delete(@Param("id") Long id);
}
