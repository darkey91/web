<#-- @ftlvariable name="message" type="java.lang.String" -->

<#import "common.ftl" as c>

<@c.page>
<div class="articles">
    <div class="no-data">No data</div>
</div>

<template class="article-template">
     <article>
         <div class="title">{{#}}</div>
         <div class="information">by {{title}}, {{creationTime}}</div>
         <div class="body">{{text}}</div>

         <div class="footer">
             <div class="left">
                 <img src="img/voteup.png" title="Vote Up" alt="Vote Up"/>
                 <span class="positive-score">+173</span>
                 <img src="img/votedown.png" title="Vote Down" alt="Vote Down"/>
             </div>
             <div class="right">
                 <img src="img/date_16x16.png" title="Publish Time" alt="Publish Time"/>
                 2 days ago
                 <img src="img/comments_16x16.png" title="Comments" alt="Comments"/>
                 <a href="#">68</a>
             </div>
         </div>
     </article>
</template>

<script>
    $(function() {
        var $noData = $(".articles .no-data");
        $noData.text("Loading articles..");
        $.post("", {action:"find"}, function (articles) {
            var $articleTag = $("div.articles");
            if (articles.length > 0) {
                $articleTag.empty();
                articles.forEach(function (article) {
                    var $div = cloneTemplate($("template.article-template"));
                    $div.find(".title").text(article.key.title);
                    $div.find(".body").text(article.key.text);
                    $div.find(".information").text("by ".concat(article.value, ", ", article.key.creationTime));
                    $articleTag.append($div);
                });
            } else {
                    $noData.text("No data");
            }})
    })
</script>
</@c.page>
