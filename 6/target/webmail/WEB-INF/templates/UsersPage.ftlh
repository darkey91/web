<#-- @ftlvariable name="users" type="ru.itmo.webmail.model.domain.User[]" -->
<#import "common.ftl" as c>

<@c.page>
<div class="datatable users">
    <div class="caption">Users</div>
    <table>
        <thead>
        <tr>
            <th>#</th>
            <th>Login</th>
            <th>Rating</th>
            <#if user?? && user.admin()>
              <th>set/remove admin permissions</th>
            </#if>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td colspan="3" class="no-data">No data</td>
        </tr>
        </tbody>
    </table>
</div>

<template class="users-tr">
    <tr>
        <td class="id">{{#}}</td>
        <td class="login">{{login}}</td>
        <td class="rating"></td>
        <#if user.admin()>
            <td class="adminPermission">
                <div class="permission"></div>
                <button style="margin-top:1em;" class="change"></button>
            </td>
        </#if>
    </tr>
</template>
<script>
    $(function () {
        var $noData = $(".users .no-data");
        $noData.text("Loading...");
        $.post("", {action: "find"}, function (users) {
            var $tbody = $(".users tbody");
            if (users.length > 0) {
                var logins = [];
                $tbody.empty();

                var ok = true;

                users.forEach(function (user) {
                    var $tr = cloneTemplate($("template.users-tr"));
                    $tr.find(".id").text(user.id);
                    $tr.find(".login").text(user.login);
                    $tr.find("tr").attr("id", "user_" + user.login.toLowerCase());
                     <#if user.admin()>
                        if (user.isAdmin) {
                            $tr.find(".permission").text("admin");
                            $tr.find(".change").text("disable");
                        } else {
                            $tr.find(".permission").text("user");
                            $tr.find(".change").text("enable");
                        }
                     </#if>

                    if (ok === true) {
                        $tr.find(".adminPermission").click(function () {
                            var userId = user.id;
                            var value = 0;
                            if ($(this).find(".change").text() === "disable") {
                                $(this).find(".permission").text("user");
                                $(this).find(".change").text("enable");
                            } else {
                                value = 1;
                                $(this).find(".permission").text("admin");
                                $(this).find(".change").text("disable");
                            }
                            $.post("", {action: "changePermission", userId: userId, value: value} , function(response) {
                                    ok = response;
                                }
                            );
                            if (!ok) location.reload();
                            return false;
                        });
                    }
                    logins.push(user.login);
                    $tbody.append($tr);


                });
                /*
                $.ajax({
                    url: "http://codeforces.com/api/user.info",
                    jsonp: "jsonp",
                    dataType: "jsonp",
                    data: {
                        handles: logins.join(";")
                    },
                    success: function (response) {
                        if (response["status"] === "OK") {
                            response["result"].forEach(function (user) {
                                $("#user_" + user.handle.toLowerCase()).find(".rating").text(user["rating"]);
                            })
                        }
                    }
                })*/
            } else {
                $noData.text("No data");
            }
        })
    })
</script>
</@c.page>
