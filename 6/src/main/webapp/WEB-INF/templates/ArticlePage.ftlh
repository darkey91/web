<#import "common.ftl" as c>
<@c.page>
<form class="addArticle" action="/article" method="post">
    <input type="hidden" name="action" value="create">

    <p> Title</p>
    <input name="title" />
    <p>Enter your text</p>
    <p><textarea rows="20" cols="60" name="text"></textarea></p>
    <p><input type="submit" value="Add article" ></p>
    <div class="error"></div>
</form>

<script>
    $(function() {
        $(".addArticle").submit(function () {
            var title = $(this).find("input[name='title']").val();
            var text = $(this).find("textarea[name='text']").val();
            var $error= $(this).find(".error").text("");
            debugger;
            $.post("", {action:"create", title:title, text:text}, function(response) {
                if (response["success"]) {
                    setMessage("You have successfully published your article!");
                    location.reload();
                    $("textarea.text").text("");
                    $("input.title").attr('value', "");
                } else {
                    $error.text(response["error"]);
                }
            });
            return false;
        });
    })
</script>
</@c.page>