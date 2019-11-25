package life.majiang.community.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
//bean里面还加了别的方法，这个是比较奇怪的
@Data
public class PaginationDTO {
    private List<QuestionDTO> questions;
    private boolean showPrevious;//显示上一页
    private boolean showFirstPage;//显示第一页
    private boolean showNext;//显示下一页
    private boolean showEndPage;//显示最后一页
    private Integer page;//当前页数
    private List<Integer> pages = new ArrayList<>();//分页栏要显示的是那几页 的集合
    private  Integer totalPage;//总共有多少页数

    //设置分页逻辑
    public void setPagination(Integer totalCount, Integer page, Integer size) {
        //如果总问题数tatalCoutn 取模 要显示的页数size 不为0 ，则总页数要加一
        if (totalCount % size == 0){
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }

        //如果
        if (page < 1){
            page = 1;
        }
        if (page > totalPage){
            page = totalPage;
        }
        this.page = page;//将形参传递给PaginationDTO，目前处于第几分页
        //【哦哦，这里出问题了，没有传参】
        this.totalPage = totalPage;

        pages.add(page);//要显示 的分页
        for (int i = 1; i <= 3; i++){
            //index-要插入指定元素的索引
            //element –要插入的元素
            if (page - i > 0){  //【假设page=5】:2 3 4 [5] 6 7 8 【page=1的话，只插入右边的3个】
                // i=1时，page-1=4
                pages.add(0,page-i);//4插入左边，3插入左边，2插入左边
            }

            if (page + i <= totalPage){
                pages.add(page + i);//6插入右边，7插入右边，8插入右边
            }
        }

        //是否展示上一页
        if (page == 1){
            showPrevious = false;
        } else {
            showPrevious = true;
        }

        //是否展示下一页
        if (page == totalPage){
            showNext = false;
        } else {
            showNext = true;
        }

        //是否展示第一页
        if (pages.contains(1)){
            showFirstPage = false;
        }else {
            showFirstPage = true;
        }

        //是否展示最后页
        if (pages.contains(totalPage)){
            showEndPage = false;
        } else {
            showEndPage = true;
        }


    }
}
