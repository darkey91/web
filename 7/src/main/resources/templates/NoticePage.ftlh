<#import "common.ftlh" as c>
<#import "/spring.ftl" as spring>

<#macro error field>
    <@spring.bind field/>
    <#list spring.status.errorMessages as error>
        <div class="error">${error}</div>
        <#break/>
    </#list>
</#macro>

<@c.page>
    <form method="post" action="/save">
        <textarea id='content' name='content' class="content"></textarea>
        <button class="Add">Add</button>
        <@error 'notice.content'/>
    </form>
</@c.page>