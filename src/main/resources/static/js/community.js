
function post() {
    var qustiosnId = $("#question_id").val();
    var content = $("#comment_content").val();

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
            "parentId":qustiosnId,
            "content":content,
            "type":1
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
            console.log(response);
        },
        dataType: "json"
    });

    console.log(content);
    console.log(qustiosnId);
}