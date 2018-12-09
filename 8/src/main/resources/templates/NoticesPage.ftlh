<#import "./common.ftlh" as c>

<@c.page>
<div class="datatable">
    <div class="caption">Notices</div>
    <table>
        <thead>
        <tr>
            <th>#</th>
            <th>Text</th>
            <th>Created At</th>
        </tr>
        </thead>
        <tbody>
    <#list notices as notice>
    <tr>
        <td><a href="/notice/${notice.id}">${notice.id}</a> </td>
        <td>${notice.text}</td>
        <td>${notice.creationTime}</td>
    </tr>
    </#list>
        </tbody>
    </table>
</div>
</@c.page>
