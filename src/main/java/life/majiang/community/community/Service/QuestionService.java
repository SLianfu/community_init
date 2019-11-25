package life.majiang.community.community.Service;

import life.majiang.community.community.dto.PaginationDTO;
import life.majiang.community.community.dto.QuestionDTO;
import life.majiang.community.community.mapper.QuestionMapper;
import life.majiang.community.community.mapper.UserMapper;
import life.majiang.community.community.model.Question;
import life.majiang.community.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * //同时可以使用userMapper，QeustionMapper
 * 起到组装的作用
 */
@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    public PaginationDTO list(Integer page, Integer size) {

        PaginationDTO paginationDTO = new PaginationDTO();//新建一个分页对象
        Integer totalCount = questionMapper.count();//查出所有问题数
        paginationDTO.setPagination(totalCount,page,size);//通过三个参数，计算 分页对象 一些分页逻辑

        if (page < 1 ){
            page = 1;
        }
        if (page > paginationDTO.getTotalPage()){
            page = paginationDTO.getTotalPage();
        }
        //size*(page-1)通过公式，计算偏移量:如第二页，开始显示的元素是 3*（2-1）= 3;偏移量为3开始元素是4
        Integer offset = size * (page - 1);

        List<Question> questions = questionMapper.list(offset,size);//questionMapper.list();
        //返回的是list，这里新建一个list
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questions) {
            //根据问题的发布者找到 user
           User user =  userMapper.findById(question.getCreator());
           //要把question 转成 DTO
            QuestionDTO questionDTO = new QuestionDTO();
            //把question里的内容 放到questionDTO【1，古老方法】【2，用工具】
//            questionDTO.setId(question.getId());【1】
            BeanUtils.copyProperties(question,questionDTO);
            //直接将source对象的属性复制到target【通过反射】

            //设置dto里面的user
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);//把设置了user的questionDTO对象加入到list

        }
        //重新封装的paginationDTO，将查询出的问题条目集合questionDTOList放入分页对象
        paginationDTO.setQuestions(questionDTOList);
        return paginationDTO;
    }
}
