package dao.mappers;

import email.emailoption.EmailOption;
import email.emailoption.EmailOptionType;
import org.apache.ibatis.annotations.*;

public interface EmailOptionMapper {

    @Select("SELECT * FROM email_option WHERE type = #{type}")
    @Results(id = "emailOption", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "type", column = "type"),
            @Result(property = "fromAddress", column = "from_address"),
            @Result(property = "fromName", column = "from_name"),
            @Result(property = "host", column = "host"),
            @Result(property = "port", column = "port"),
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "password")
    })
    EmailOption selectByType(@Param("type") EmailOptionType type);


    @Select("SELECT * FROM email_option WHERE id = #{id}")
    @ResultMap("emailOption")
    EmailOption selectById(@Param("id") Long id);


    @Insert("INSERT INTO email_option(" +
            "type, " +
            "from_address, " +
            "from_name, " +
            "host, " +
            "port, " +
            "username, " +
            "password" +
            ") " +
            "VALUES (" +
            "#{option.type}, " +
            "#{option.fromAddress}, " +
            "#{option.fromName}, " +
            "#{option.host}, " +
            "#{option.port}, " +
            "#{option.username}, " +
            "#{option.password}" +
            ")")
    @Options(useGeneratedKeys = true, keyProperty = "option.id", keyColumn = "id")
    void create(@Param("option") EmailOption option);


    @Update("UPDATE email_option SET " +
            "type=#{option.type}, " +
            "from_address=#{option.fromAddress}, " +
            "from_name=#{option.fromName}, " +
            "host=#{option.host}, " +
            "port=#{option.port}, " +
            "username=#{option.username}, " +
            "password=#{option.password} " +
            "WHERE id=#{option.id}")
    void update(@Param("option") EmailOption option);


    @Delete("DELETE FROM email_option WHERE id=#{id}")
    void delete(@Param("id") Long id);

}
