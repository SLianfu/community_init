package life.majiang.community.community.mapper;

import life.majiang.community.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

//@Repository
@Mapper
public interface UserMapper {

   /* @Select("SELECT * FROM CITY WHERE state = #{state}")
    City findByState(@Param("state") String state);*//*数据库的字段写错了account_id,*/
    @Insert("insert into user (name,account_id,token,gmt_create,gmt_modified) values(#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified})")
   void insert(User user);

    //来数据库中查user
    @Select("select * from user where token = #{token}")//这里传进来的是方法的形参（形参为类时直接放入，不为类是加注解）
    User findByToken(@Param("token") String token);
}
