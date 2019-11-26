package life.majiang.community.community.mapper;

import life.majiang.community.community.model.User;
import org.apache.ibatis.annotations.*;

//@Repository
@Mapper
public interface UserMapper {

   /* @Select("SELECT * FROM CITY WHERE state = #{state}")
    City findByState(@Param("state") String state);*//*数据库的字段写错了account_id,*/
    @Insert("insert into user (name,account_id,token,gmt_create,gmt_modified,bio ,avatar_url) values(#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{bio},#{avatarUrl})")
   void insert(User user);

    //来数据库中查user
    @Select("select * from user where token = #{token}")//这里传进来的是方法的形参（形参为类时直接放入，不为类是加注解）
    User findByToken(@Param("token") String token);

    @Select("select * from user where id = #{id}")
    User findById(@Param("id") Long id);//这里传的id是 question 对象 的发布者 creator

    @Select("select * from user where account_id = #{accountId}")
    User findByAccountId(@Param("accountId") String accountId);

    @Update("update user set name=#{name},token=#{token},gmt_modified=#{gmtModified},bio=#{bio},avatar_url=#{avatarUrl} where id=#{id}")
    void update(User User);
}
