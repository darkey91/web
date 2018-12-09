<#import "common.ftlh" as c>

<@c.page>
    <div class='userInfo form-box'>
        <#if userInfo??>
            <div class="header">User #${userInfo.id}</div>
            <div class="field">
                <div class="name">${userInfo.login}</div>
            </div>
            <div class="field">
                <div class="name">${userInfo.creationTime}</div>
            </div>
            <div class="field">
                <div class="name">Status: <#if user.disabled>disabled<#else>enabled</#if></div>
            </div>
        <#else>
            <div>No such user</div>
        </#if>
    </div>
</@c.page>
