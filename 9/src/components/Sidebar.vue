<template>
    <aside>
        <SidebarPost v-for="post in viewPosts" :post="post" :user="users[post.userId]" :key="post.id"/>
    </aside>
</template>

<script>
    import SidebarPost from './SidebarPost';
    export default {
        name: "Sidebar",
        props: ["users", "posts"],
        computed: {
            viewPosts: function () {
                return Object.values(this.posts).sort((a, b) => b.id - a.id).slice(0, 2);
            }
        },
        components: {
            SidebarPost
        }
    }
</script>

<style scoped>

</style>
