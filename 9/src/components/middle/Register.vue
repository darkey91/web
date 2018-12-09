<template>
    <div class="register form-box">
        <div class="header">Register</div>
        <div class="body">
            <form @submit.prevent="onRegister">
                <input type="hidden" name="action" value="register">
                <div class="field">
                    <div class="name">
                        <label for="login">Login</label>
                    </div>
                    <div class="value">
                        <input id="login" name="login" v-model="login"/>
                    </div>
                </div>
                <div class="field">
                    <div class="name">
                        <label for="name">Name</label>
                    </div>
                    <div class="value">
                        <input id="name" type="name" name="name" v-model="name"/>
                    </div>
                </div>
                <div class="button-field">
                    <input type="submit" value="Register">
                </div>
                <div class="error">{{error}}</div>
            </form>
        </div>
    </div>
</template>

<script>
    export default {
        name: "Register",

        data: function() {
            return {
                login: "",
                name: "",
                error: ""
            }
        },

        beforeCreate() {
            this.$root.$on("onRegisterValidationError", (error) => {
                this.error = error
            });
        },
        beforeMount() {
            this.login = this.name = this.error = "";
        }, methods: {
            onRegister: function() {
                this.$root.$emit("OnRegisterUser", this.login, this.name);
            }
        }
    }
</script>

<style scoped>

</style>
