package life.majiang.community.community.Service;

import life.majiang.community.community.dto.PaginationDTO;
import life.majiang.community.community.dto.QuestionDTO;
import life.majiang.community.community.exception.CustomizeErrorCode;
import life.majiang.community.community.exception.CustomizeException;
import life.majiang.community.community.mapper.QuestionExtMapper;
import life.majiang.community.community.mapper.QuestionMapper;
import life.majiang.community.community.mapper.UserMapper;
import life.majiang.community.community.model.Question;
import life.majiang.community.community.model.QuestionExample;
import life.majiang.community.community.model.User;
import org.apache.ibatis.session.RowBounds;
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
    private QuestionExtMapper questionExtMapper;

    @Autowired
    private UserMapper userMapper;

    public PaginationDTO list(Integer page, Integer size) {

        PaginationDTO paginationDTO = new PaginationDTO();//新建一个分页对象
        Integer totalCount = (int)questionMapper.countByExample(new QuestionExample());//查出所有问题数
        Integer totalPage;//总共有多少页数
        //如果总问题数tatalCoutn 取模 要显示的页数size 不为0 ，则总页数要加一
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }
        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }
        paginationDTO.setPagination(totalPage,page);//通过三个参数，计算 分页对象 一些分页逻辑
        //size*(page-1)通过公式，计算偏移量:如第二页，开始显示的元素是 3*（2-1）= 3;偏移量为3开始元素是4
        Integer offset = size * (page - 1);

        //List<Question> questions = questionMapper.list(offset,size);//questionMapper.list();
        List<Question> questions = questionMapper.selectByExampleWithBLOBsWithRowbounds(new QuestionExample(), new RowBounds(offset, size));

        //返回的是list，这里新建一个list
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questions) {
            //根据问题的发布者找到 user
            User user =  userMapper.selectByPrimaryKey(question.getCreator());
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

    public PaginationDTO list(Long userId, Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();//新建一个分页对象
        //Integer totalCount = questionMapper.countByUserId(userId);//根据用户id查出所有问题数
        QuestionExample example = new QuestionExample();
        example.createCriteria()
                .andCreatorEqualTo(userId);
        Integer totalCount = (int)questionMapper.countByExample(example);

        Integer totalPage;//总共有多少页数
        //如果总问题数tatalCoutn 取模 要显示的页数size 不为0 ，则总页数要加一
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }
        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }
        paginationDTO.setPagination(totalPage,page);//通过三个参数，计算 分页对象 一些分页逻辑


        //size*(page-1)通过公式，计算偏移量:如第二页，开始显示的元素是 3*（2-1）= 3;偏移量为3开始元素是4
        Integer offset = size * (page - 1);//【-5，page = 0】

        //List<Question> questions = questionMapper.listByUserId(userId,offset,size);//questionMapper.list();

        QuestionExample example1 = new QuestionExample();
        example1.createCriteria()
                .andCreatorEqualTo(userId);
        List<Question> questions = questionMapper.selectByExampleWithBLOBsWithRowbounds(example1, new RowBounds(offset, size));
        //返回的是list，这里新建一个list
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questions) {
            //根据问题的发布者找到 user
            User user =  userMapper.selectByPrimaryKey(question.getCreator());
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

    public QuestionDTO getById(Long id) {
        //通过questionMapper去调用getQeustionById(id)
        Question question = questionMapper.selectByPrimaryKey(id);

        //加一个判断，是否为空
        if (question == null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        //加上用户_根据question.creator
        User user =  userMapper.selectByPrimaryKey(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;

    }

    public void createOrUpdadte(Question question) {
        if (question.getId() == null){
            //创建
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            //设置默认值
            question.setViewCount(0);
            question.setCommentCount(0);
            question.setLikeCount(0);
            questionMapper.insert(question);
        }else {
            //更新
            question.setGmtModified(question.getGmtCreate());

            Question updateQuestion = new Question();
            updateQuestion.setGmtModified(System.currentTimeMillis());
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setDescription(question.getDescription());
            updateQuestion.setTag(question.getTag());

            QuestionExample example = new QuestionExample();
            example.createCriteria()
                    .andIdEqualTo(question.getId());
            int updated = questionMapper.updateByExampleSelective(updateQuestion, example);
            //用返回值去验证是否更新成功
            //进行封装
            if (updated != 1){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    //增加阅读数
    public void incView(Long id) {
        //原始版：没有考虑并发，【这里是用阅读数值覆盖数据库值，用数据库值累加会更好】
        //首先要拿到数据库里的question
        /*Question question = questionMapper.selectByPrimaryKey(id);
        Question updateQuestion = new Question();
        updateQuestion.setViewCount(question.getViewCount() + 1);
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria()
                .andIdEqualTo(id);
        questionMapper.updateByExampleSelective(updateQuestion, questionExample);
        */
        Question question = new Question();
        question.setId(id);
        question.setViewCount(1);//这里是每次累加一个
        questionExtMapper.incView(question);
    }
}
