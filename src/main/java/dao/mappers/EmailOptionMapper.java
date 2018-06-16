package dao.mappers;

import email.emailoption.EmailOption;
import email.emailoption.EmailOptionType;
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
            @Result(property = "password", column = "password"),
    })

    @Select("SELECT * from email_option WHERE type = #{type}")
    EmailOption selectOptionsByType(EmailOptionType type);

}
