<#import "common.ftl" as c>
<@c.page>

<div class="datatable myArticles">
    <div class="caption">My articles</div>
    <table>
        <thead>
        <tr>
            <th>#</th>
            <th>Title</th>
            <th>Hide/Show</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <#if myArticles??>
               <#list myArticles as myArticle>
                   <tr>
                       <td id="id">
                           ${myArticle.id}
                       </td>
                       <td>
                           ${myArticle.title}
                       </td>
                       <td>
                           <div class="row">
                               <div class="userId" style="display:none">${myArticle.userId}</div>
                               <div class="id" style="display:none">${myArticle.id}</div>
                               <button class="hideShowButton"><#if myArticle.isHidden() == 1>Show<#else>Hide</#if></button>
                           </div>

                       </td>
                   </tr>
               </#list>

            <#else>
                <td colspan="3" class="no-data">No data</td>
            </#if>
        </tr>
        </tbody>
    </table>
</div>

<script>

    $("div.row").click(function () {
        var id = $(this).find(".id").text();
        var userId = $(this).find(".userId").text();
        var value = $(this).find(".hideShowButton").text();

        var setHidden;
        if (value === 'Hide') {
            setHidden = true;
            $(this).find(".hideShowButton").text('Show');
        } else {
            $(this).find(".hideShowButton").text('Hide');
            setHidden = false;
        }

        $.post("", {action:"hideShow", setHidden: setHidden, articleId:id, userId:userId});
        return false;
    })

</script>

</@c.page>