<#import "/spring.ftl" as spring>
<#import "./common.ftlh" as c>

<#macro error field>
    <@spring.bind field/>
    <#list spring.status.errorMessages as error>
        <div class="error">${error}</div>
        <#break/>
    </#list>
</#macro>

<@c.page>
    <#if notice??>
            <div class="article">
                <article>
                    <div class="title">Notice #${notice.id}</div>
                    <div class="information">Author ${notice.getUser().getLogin()}, ${notice.creationTime}</div>
                    <div class="body">${notice.text}</div>
                    <div class="left">
                        <div class="footer">
                           <div class="tags">
                               <#list notice.tags as tag>#${tag.name} </#list>
                           </div>
                        </div>
                    </div>
                </article>
            </div>
            <div class="comments">
                <div class="title"><img src="/img/comments-48x48.png" alt="Comments">Comments</div>
                <#list notice.comments as com>
                <div class="comment">
                    <table class="comment-table">
                        <tbody>
                        <tr>
                            <td class="left">
                                <a href="#">${com.getUser().getLogin()}</a>
                            </td>
                            <td class="right">
                                <div class="info">
                                    ${com.creationTime}
                                </div>
                                <div class="comment-content">
                                    ${com.text}
                                </div>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                </#list>
            </div>
        <#if user??>
                <div class="comment form-box">
                    <div class="header">Add comment</div>
                    <div class="body">
                        <form action="" method="post">
                            <div class="field">
                                <div class="name">
                                    <label for="text">Text</label>
                                </div>
                                <div class="value">
                                    <input id="text" name="text">
                                </div>
                                <div class="error">${commentError!}</div>
                                 <@error 'comment.text'/>
                            </div>
                            <div class="button-field">
                                <input type="submit" value="Add">
                            </div>
                        </form>
                    </div>
                </div>
        </#if>
    <#else>
            Not found
    </#if>
</@c.page>
