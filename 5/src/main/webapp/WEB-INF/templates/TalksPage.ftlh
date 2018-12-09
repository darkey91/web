<#import "common.ftl" as c>

<@c.page>
<div class="form-box">
    <form method="post" action="/talks" name="Send message">
        <input type="hidden" name="action" value="send">
        <div class="field">
            <div class="name">
                <label for="message">Message</label>
            </div>
            <div class="value">
                <textarea id="message" name="text" >${text!}</textarea>
            </div>
        </div>
        <div class="field">
            <div class="name">
                <label for="target">To user </label>
            </div>
            <div class="value">
                <input id="targetLogin" name="targetLogin" />
            </div>
            <div class="button-field">
                <input type="submit" name="action" value="send">
            </div>
        </div>
        <#if error??>
                    <div class="error">
                        ${error}
                    </div>
        </#if>



    </form>
</div>
  <div class="datatable">
      <div class="caption">Messages</div>
      <table>
          <thead>
          <tr>
              <th>#</th>
              <th>From</th>
              <th>To</th>
              <th>Text</th>
              <th>Date and time</th>
          </tr>
          </thead>
          <tbody>
        <#list messages as message>
        <tr>
            <td>
                ${message_index+ 1}
            </td>
            <td>
                ${userService.find(message.sourceUserId).login}
            </td>
            <td>
                ${userService.find(message.targetUserId).login}
            </td>
            <td>
                ${message.text}
            </td>
            <td>
                ${message.creationTime}
            </td>

        </tr>
        </#list>
      </table>
  </div>

</@c.page>
