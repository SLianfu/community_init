/**
 * 提交回复
 */
function post() {
    var qustiosnId = $("#question_id").val();
    var content = $("#comment_content").val();
    comment2target(qustiosnId,1,content);

}

/**
 * 封装的调用方法
 * @param targetId
 * @param type
 * @param content
 */
function comment2target(targetId,type,content) {
    if (!content){/*js比较特别，可以直接进行判断，content有值即为true*/
        alert("不能回复空内容");
        return;
    }

    $.ajax({
        type: "POST",
        /*要指定contentType*/
        contentType:'application/json',
        url: "/comment",
        /*没用JSON.stringify(）括起来时，传递的是一个json对象：方法：用于将 JavaScript 值转换为 JSON 字符串。*/
        data: JSON.stringify({
            "parentId":targetId,
            "content":content,
            "type":type
        }),
        success: function (response) {
            if (response.code == 200) {
                window.location.reload();/*页面直接刷新*/
                /* $("#comment_section").hide();关闭文本框*/
            }else {
                /*错误处理*/
                /*实现不刷新登陆页面，，，不知道该怎么说*/
                if(response.code == 2003){
                    /*没登录,confirm会把你在窗口操作的值（true/false）返回回来*/
                    var isAccepted = confirm(response.message);
                    if (isAccepted){
                        window.open("https://github.com/login/oauth/authorize?client_id=2e11ca1282f427624e3e&redirect_uri=http://localhost:8887/callback&scope=user&state=1");
                        window.localStorage.setItem("closable",true);/*closeable返回的是带双引号的true："true"*/
                    }
                }else {
                    alert(response.message);
                }

            }
            //console.log(response);
        },
        dataType: "json"
    });
}

function comment(e) {
    var commentId = e.getAttribute("data-id");/*拿到id*/
    var content = $("#input-"+commentId).val();
    comment2target(commentId,2,content);

}

/**
 * 展开二级评论：当点击折叠时；去获取新的请求
 *  获取请求之后拿到这些标签属性后，再点开这个方法
 *      这些属性不是刚刚赋值的这些属性，而是发送这个请求（localhost:8887/comment/131）
 *      获取的数据，并把数据回显到页面
 */
function collapseComments(e) {
    //console.log(e);
    var id = e.getAttribute("data-id");/*拿到id（选中一级评论的id）*/
    /*然后改变当前commnet的样式，即改变div的类（加上一个in 【class】）*/
    var comments = $("#comment-"+id);
    //获取二级评论的展开状态
    var collapse = e.getAttribute("data-collapse");//刚开始是没有的，为false
    if (collapse){
        //collapse,存在即为true，点击就去移除class【这里要改的几个样式】
        //折叠，同时要把状态移除掉
        comments.removeClass("in");
        e.removeAttribute("data-collapse");
        //移除其他样式
        e.classList.remove("active");
    }else {
        var subCommentContainer = $("#comment-"+id);//放到外面
        //这里要加一个判断的，如果已经加载就不用重复加载，没有加载时，只有一个评论框div,即只有一个元素
        if (subCommentContainer.children().length != 1){//不等于1就是展开状态了
            //直接展示
            //展开二级评论
            comments.addClass("in");/*comments是一个标签的id，在后面追加in*/
            /*增加一个参数来标记comments这个标签是否是打开状态*/
            //标记二级评论展开状态
            e.setAttribute("data-collapse","in");
            e.classList.add("active");

        } else {
            //先发送请求
            $.getJSON( "/comment/"+id, function(data ) {
                // console.log(data);
                //把获取的信息写到页面，data
                //这里应该时一级评论的id，也是二级评论的整体div的标签id

                $.each( data.data.reverse(), function( index,comment ) {//index,comment：下标和评论内容
                    // console.log(comment);
                   //左边元素
                    var mediaLeftElement = $("<div/>", {
                        "class": "media-left"
                    }).append($("<img/>", {
                        "class": "media-object-self media-object img-rounded",
                        "src": comment.user.avatarUrl
                    }));

                    //右边元素
                    var mediaBodyElement =$("<div/>",{
                        "class":"media-body"
                    }).append($("<h5/>", {
                        "class": "media-heading",
                        "html":comment.user.name,
                    })).append($("<div/>", {
                        "html":comment.content
                    })).append($("<div/>", {
                        "class":"menu"
                    }).append($("<span/>", {
                        "class":"pull-right",
                        "html":moment(comment.gmtCreate).format('YYYY-MM-DD')
                    })));

                    //整体：二级评论元素
                    var mediaElement = $("<div/>",{
                        "class":"media"
                    }).append(mediaLeftElement).append(mediaBodyElement);

                    var commentElement = $("<div/>",{
                        "class":"col-lg-12 col-md-12 col-sm-12 col-xs-12 comments",
                        // html:comment.content，这个要注释掉
                    }).append(mediaElement);
                   //把每条二级评论的content插入到(每次都插到头部)，加一个reverse()
                    subCommentContainer.prepend(commentElement);
                });

                //展开二级评论
                comments.addClass("in");/*comments是一个标签的id，在后面追加in*/
                /*增加一个参数来标记comments这个标签是否是打开状态*/
                //标记二级评论展开状态
                e.setAttribute("data-collapse","in");
                e.classList.add("active");
            });
        }
    }
    //console.log(id);
}

function showSelectTag() {
    $("#select-tag").show();
}

function selectTag(e) {
    var value = e.getAttribute("data-tag");
    var previous = $("#tag").val();
    if (previous.indexOf(value) == -1) {
        if (previous) {
            $("#tag").val(previous + ',' + value);
        } else {
            $("#tag").val(value);
        }
    }
}