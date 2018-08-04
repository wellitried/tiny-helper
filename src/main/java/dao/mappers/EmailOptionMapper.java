package dao.mappers;

import email.emailoption.EmailOption;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

public interface EmailOptionMapper {

    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "type", column = "type"),
            @Result(property = "fromAddress", column = "from_address"),
            @Result(property = "fromName", column = "from_name"),
            @Result(property = "host", column = "host"),
            @Result(property = "port", column = "port"),
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "password")
    })

    @Select("SELECT * FROM email_option WHERE type = #{type}")
    EmailOption selectByType(@Param("type") EmailOption.EmailOptionType type);

}
