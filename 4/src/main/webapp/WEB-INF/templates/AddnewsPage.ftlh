<#import "common.ftl" as c>
<@c.page>
<form action="/addnews" method="post">
    <p>Enter your text</p>
    <input type="hidden" name="action" value="addnews">
    <p><textarea rows="20" cols="60" name="news"></textarea></p>
    <p><input type="submit" value="Add news" ></p>
    <#if error??>
            <div class="error">
                ${error}
            </div>
    </#if>
</form>
</@c.page>