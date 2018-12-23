<template>
    <div>
        <Post v-for="post in viewPosts" :users="users" :post="post" :key="post.id" />
    </div>
</template>

<script>
    import Post from "./Post";

    export default {
        name: "Index",
        props: ['users', 'posts'],
        components : {Post},
        computed: {
            viewPosts: function () {
                return Object.values(this.posts).reverse();
            }
        }
    }
</script>

<style scoped>

</style>
