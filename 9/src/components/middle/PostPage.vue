<template>
    <div>
        <Post :users="users" :post="post" />
        <div class="comments">
            <div class="title"><img src="../../assets/img/comments-48x48.png" alt="Comments">Comments</div>
            <div class="comment" v-for="comment in comments" :key="comment.id" :users="users">
                <table class="comment-table">
                    <tbody>
                    <tr>
                        <td class="left">
                            <a href="#">{{users[comment.userId].login}}</a>
                        </td>
                        <td class="right">
                            <div class="info">

                            </div>
                            <div class="comment-content">
                                {{comment.text}}
                            </div>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
    </div>

    </div>
</template>

<script>
    import Post from "./Post";

    export default {
        name: "PostPage",
        props: ['users', 'post', 'comments'],
        components : {Post},
    }
</script>

<style scoped>

</style>
