<#-- @ftlvariable name="message" type="java.lang.String" -->

<#import "common.ftl" as c>

<@c.page>
    <#if message??>
        <p>${message}</p>
    </#if>

    <#if confirmationUrl??>
        <a href="${confirmationUrl}">Click here to confirm your email!</a>
    </#if>

    <h1>Welcome to the index page</h1>
</@c.page>
